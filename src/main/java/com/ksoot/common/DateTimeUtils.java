package com.ksoot.common;

import java.time.*;
import java.time.format.DateTimeFormatter;
import java.time.format.TextStyle;
import java.time.temporal.ChronoUnit;
import java.time.temporal.TemporalAdjusters;
import java.util.Date;
import java.util.Locale;
import java.util.Objects;
import java.util.TimeZone;
import lombok.experimental.UtilityClass;

@UtilityClass
public class DateTimeUtils {

    public static final LocalTime LOCAL_TIME_MIN = LocalTime.MIN.truncatedTo(ChronoUnit.MINUTES);

    public static final LocalTime LOCAL_TIME_MAX = LocalTime.MAX.truncatedTo(ChronoUnit.MINUTES);

    public static final ZoneId ZONE_ID_UTC = ZoneId.of("UTC");

    public static final ZoneId ZONE_ID_IST = ZoneId.of("Asia/Kolkata");

    public static final ZoneOffset ZONE_OFFSET_UTC = ZoneOffset.UTC;

    public static final ZoneOffset ZONE_OFFSET_IST = ZoneOffset.ofHoursMinutes(5, 30);

    public static final TimeZone TIME_ZONE_UTC = TimeZone.getTimeZone(ZONE_ID_UTC);

    public static final TimeZone TIME_ZONE_IST = TimeZone.getTimeZone(ZONE_ID_IST);

    public static final ZoneId SYSTEM_ZONE_ID = ZoneId.systemDefault();

    public static final ZoneOffset SYSTEM_OFFSET_ID = SYSTEM_ZONE_ID.getRules().getOffset(java.time.Instant.now());

    public static final String ZONE_DISPALY_NAME =
            SYSTEM_ZONE_ID.getDisplayName(TextStyle.FULL, Locale.getDefault())
                    + "("
                    + SYSTEM_ZONE_ID.getDisplayName(TextStyle.SHORT, Locale.getDefault())
                    + ")";

    public static final String DEFAULT_DATE_TIME_FORMAT = "yyyy-MM-dd'T'HH:mm:ss'Z'";

    public static LocalDateTime fromISTtoUTC(final LocalDateTime localDateTimeIST) {
        // Convert LocalDateTime from IST to ZonedDateTime
        final ZonedDateTime zonedDateTimeIST = localDateTimeIST.atZone(ZONE_ID_IST);
        // Convert ZonedDateTime to UTC
        final ZonedDateTime zonedDateTimeUTC = zonedDateTimeIST.withZoneSameInstant(ZONE_ID_UTC);
        return zonedDateTimeUTC.toLocalDateTime();
    }

    public static LocalDateTime fromUTCtoIST(final LocalDateTime localDateTimeUTC) {
        // Convert LocalDateTime from UTC to ZonedDateTime
        final ZonedDateTime zonedDateTimeIST = localDateTimeUTC.atZone(ZONE_ID_UTC);
        // Convert ZonedDateTime to IST
        final ZonedDateTime zonedDateTimeUTC = zonedDateTimeIST.withZoneSameInstant(ZONE_ID_IST);
        return zonedDateTimeUTC.toLocalDateTime();
    }

    public static LocalDate nowLocalDateIST() {
        return isSystemTimeZoneIST()
                ? LocalDate.now()
                : fromUTCtoIST(LocalDateTime.now()).toLocalDate();
    }

    public static Month currentMonthIST() {
        return nowLocalDateIST().getMonth();
    }

    public static Month previousMonthIST() {
        return nowLocalDateIST().minusMonths(1).getMonth();
    }

    public static LocalDateTime monthStartLocalDateTime(final Month month) {
        return LocalDate.of(nowLocalDateIST().getYear(), month, 1).atStartOfDay();
    }

    public static LocalDateTime monthEndLocalDateTime(final Month month) {
        return LocalDate.of(nowLocalDateIST().getYear(), month, 1)
                .with(TemporalAdjusters.lastDayOfMonth())
                .plusDays(1)
                .atStartOfDay()
                .minusSeconds(1);
    }

    public static LocalDateTime trimTime(final LocalDateTime localDateTime) {
        return LocalDateTime.of(localDateTime.toLocalDate(), LocalTime.MIDNIGHT);
    }

    public static boolean isSystemTimeZoneUTC() {
        return SYSTEM_ZONE_ID.equals(ZONE_ID_UTC);
    }

    public static boolean isSystemTimeZoneIST() {
        return SYSTEM_ZONE_ID.equals(ZONE_ID_IST);
    }

    public static LocalDateTime toLocalDateTime(final LocalDate localDate) {
        return Objects.nonNull(localDate) ? localDate.atStartOfDay() : null;
    }

    public static LocalDate toLocalDate(final LocalDateTime localDateTime) {
        return Objects.nonNull(localDateTime) ? localDateTime.toLocalDate() : null;
    }

    public static ZonedDateTime nowZonedDateTimeUTC() {
        return ZonedDateTime.now(ZONE_ID_UTC);
    }

    public static ZonedDateTime nowZonedDateTime(final ZoneId zoneId) {
        return ZonedDateTime.now(zoneId);
    }

    public static LocalDateTime nowLocalDateTimeUTC() {
        return LocalDateTime.now(ZONE_ID_UTC);
    }

    public static LocalDateTime nowLocalDateTime(final ZoneId zoneId) {
        return LocalDateTime.now(zoneId);
    }

    public static ZonedDateTime convertZonedDateTime(
            final ZonedDateTime sourceZoneDatetime, final ZoneId targetZoneId) {
        return sourceZoneDatetime.getZone() == targetZoneId
                ? sourceZoneDatetime
                : sourceZoneDatetime.withZoneSameInstant(targetZoneId);
    }

    public static OffsetDateTime convertOffsetDateTime(
            final OffsetDateTime sourceOffsetDateTime, final ZoneOffset targetZoneId) {
        return sourceOffsetDateTime.getOffset() == targetZoneId
                ? sourceOffsetDateTime
                : sourceOffsetDateTime.withOffsetSameInstant(targetZoneId);
    }

    public static String convertAndFormatOffsetDateTime(
            final OffsetDateTime sourceOffsetDateTime, final ZoneOffset targetZoneId) {
        return sourceOffsetDateTime.getOffset() == targetZoneId
                ? DateTimeFormatUtils.formatOffsetDateTime(sourceOffsetDateTime)
                : DateTimeFormatUtils.formatOffsetDateTime(
                sourceOffsetDateTime.withOffsetSameInstant(targetZoneId));
    }

    public static String convertAndFormatZonedDateTime(
            final ZonedDateTime sourceZoneDatetime, final ZoneId targetZoneId) {
        return sourceZoneDatetime.getZone() == targetZoneId
                ? DateTimeFormatUtils.formatZonedDateTime(sourceZoneDatetime)
                : DateTimeFormatUtils.formatZonedDateTime(
                sourceZoneDatetime.withZoneSameInstant(targetZoneId));
    }

    public static OffsetDateTime nowOffsetDateTimeUTC() {
        return OffsetDateTime.now(ZONE_ID_UTC);
    }

    public static OffsetDateTime nowOffsetDateTime(final ZoneId zoneId) {
        return OffsetDateTime.now(zoneId);
    }

    public static OffsetDateTime offsetDateTimeUTC(final Date date) {
        return date.toInstant().atOffset(ZoneOffset.UTC);
    }

    public static OffsetDateTime offsetDateTime(final Date date, final ZoneOffset zoneOffset) {
        return date.toInstant().atOffset(zoneOffset);
    }

    // Truncate to minutes also
    public static LocalTime covertLocalTimeHHmm(
            final LocalTime time, final ZoneId sourceZoneId, final ZoneId targetZoneId) {
        if (sourceZoneId == targetZoneId) {
            return time;
        }
        final ZonedDateTime sourceTime = ZonedDateTime.of(LocalDate.now(), time, sourceZoneId);
        final ZonedDateTime converted = sourceTime.withZoneSameInstant(targetZoneId);
        return LocalTime.of(converted.getHour(), converted.getMinute());
    }

    public static boolean timeLe(final LocalTime candidate, final LocalTime reference) {
        return !candidate.isAfter(reference);
    }

    public static boolean timeGe(final LocalTime candidate, final LocalTime reference) {
        return !candidate.isBefore(reference);
    }

    public static boolean timeBetween(
            final LocalTime candidate, final LocalTime from, final LocalTime till) {
        return timeGe(candidate, from) && timeLe(candidate, till);
    }

    public static LocalTime nowLocalTimeHHmm() {
        return LocalTime.now().truncatedTo(ChronoUnit.MINUTES);
    }

    public static String nowOffsetDateTimeString() {
        return OffsetDateTime.now()
                .truncatedTo(ChronoUnit.SECONDS)
                .format(DateTimeFormatter.ISO_OFFSET_DATE_TIME);
    }
}
