package com.ksoot.mongodb;

import com.ksoot.common.CommonConstants;
import com.ksoot.common.DateTimeUtils;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PastOrPresent;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.bson.Document;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Immutable;
import org.springframework.data.mongodb.core.mapping.event.AfterDeleteEvent;
import org.springframework.data.mongodb.core.mapping.event.AfterSaveEvent;

import java.time.Instant;
import java.time.OffsetDateTime;
import java.util.function.Supplier;

@Getter
@AllArgsConstructor(access = AccessLevel.PACKAGE)
@Valid
@Immutable
public class AuditEvent implements Identifiable<String> {

    @Id
    private String id;

    @NotNull
    @PastOrPresent
    private OffsetDateTime datetime;

    @NotEmpty
    private String actor;

    @NotNull
    @PositiveOrZero
    private Long revision;

    @NotNull
    private Type type;

    @NotEmpty
    private String collectionName;

    @NotNull
    private Document source;

    public static AuditEvent of(final Type type, final Long timestamp, final Long revision, final String collectionName,
                                final Document source, final Supplier<String> auditUserSupplier) {
        return new AuditEvent(null, Instant.ofEpochMilli(timestamp).atOffset(DateTimeUtils.SYSTEM_OFFSET_ID), auditUserSupplier.get(),
                revision, type, collectionName, source);
    }

    public static AuditEvent ofSaveEvent(final AfterSaveEvent<?> event, final Long revision, final Supplier<String> auditUserSupplier) {
        return of(revision == 1 ? Type.CREATED : Type.UPDATED, event.getTimestamp(), revision,
                event.getCollectionName(), event.getDocument(), auditUserSupplier);
    }

    public static AuditEvent ofDeleteEvent(final AfterDeleteEvent<?> event, final Long revision, final Supplier<String> auditUserSupplier) {
        return of(Type.DELETED, event.getTimestamp(), revision, event.getCollectionName(), event.getDocument(), auditUserSupplier);
    }

    public enum Type {
        CREATED, UPDATED, DELETED;
    }
}
