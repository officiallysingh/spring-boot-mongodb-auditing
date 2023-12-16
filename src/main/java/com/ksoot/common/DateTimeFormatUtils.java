package com.ksoot.common;

import lombok.experimental.UtilityClass;

import java.time.*;
import java.time.format.DateTimeFormatter;
import java.util.Objects;

@UtilityClass
public class DateTimeFormatUtils {

  // To use in mappers
  public static LocalDateTime toLocalDateTime(final LocalDate localDate) {
    return Objects.nonNull(localDate) ? localDate.atStartOfDay() : null;
  }

  // To use in mappers
  public static LocalDate toLocalDate(final LocalDateTime localDateTime) {
    return Objects.nonNull(localDateTime) ? localDateTime.toLocalDate() : null;
  }

  public static String formatDate(final LocalDate date) {
    return date.format(DateTimeFormatter.ISO_DATE);
  }

  public static String formatDate(final LocalDate date, final String pattern) {
    return date.format(DateTimeFormatter.ofPattern(pattern));
  }

  public static String formatDate(final LocalDate date, final DateTimeFormatter formatter) {
    return date.format(formatter);
  }

  public static String formatDateTime(final LocalDateTime datetime) {
    return datetime.format(DateTimeFormatter.ISO_DATE_TIME);
  }

  public static String formatDateTime(final LocalDateTime datetime, final String pattern) {
    return datetime.format(DateTimeFormatter.ofPattern(pattern));
  }

  public static String formatDateTime(
      final LocalDateTime datetime, final DateTimeFormatter formatter) {
    return datetime.format(formatter);
  }

  public static String formatZonedDateTime(final ZonedDateTime zonedDateTime) {
    return zonedDateTime.format(DateTimeFormatter.ISO_ZONED_DATE_TIME);
  }

  public static String formatZonedDateTime(
      final ZonedDateTime zonedDateTime, final String pattern) {
    return zonedDateTime.format(DateTimeFormatter.ofPattern(pattern));
  }

  public static String formatZonedDateTime(
      final ZonedDateTime zonedDateTime, final DateTimeFormatter formatter) {
    return zonedDateTime.format(formatter);
  }

  public static String formatOffsetDateTime(final OffsetDateTime offsetDateTime) {
    return offsetDateTime.format(DateTimeFormatter.ISO_OFFSET_DATE_TIME);
  }

  public static String formatOffsetDateTime(
      final OffsetDateTime offsetDateTime, final String pattern) {
    return offsetDateTime.format(DateTimeFormatter.ofPattern(pattern));
  }

  public static String formatOffsetDateTime(
      final OffsetDateTime offsetDateTime, final DateTimeFormatter formatter) {
    return offsetDateTime.format(formatter);
  }

  public static String convertAndFormatOffsetDateTime(
      final OffsetDateTime sourceOffsetDateTime, final ZoneOffset targetZoneId) {
    return sourceOffsetDateTime.getOffset() == targetZoneId
        ? formatOffsetDateTime(sourceOffsetDateTime)
        : formatOffsetDateTime(sourceOffsetDateTime.withOffsetSameInstant(targetZoneId));
  }

  public static String convertAndFormatZonedDateTime(
      final ZonedDateTime sourceZoneDatetime, final ZoneId targetZoneId) {
    return sourceZoneDatetime.getZone() == targetZoneId
        ? formatZonedDateTime(sourceZoneDatetime)
        : formatZonedDateTime(sourceZoneDatetime.withZoneSameInstant(targetZoneId));
  }

  public static String formatTime(final LocalTime time, final DateTimeFormatter formatter) {
    return time.format(formatter);
  }

  public static String formatTime(final LocalTime time) {
    return time.format(DateTimeFormatter.ISO_DATE_TIME);
  }
}
