package com.ksoot.mongodb;

import com.ksoot.common.DateTimeUtils;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PastOrPresent;
import jakarta.validation.constraints.Positive;
import java.time.Instant;
import java.time.OffsetDateTime;
import java.util.Optional;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.bson.Document;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Immutable;
import org.springframework.data.annotation.PersistenceCreator;
import org.springframework.data.mongodb.core.mapping.Field;
import org.springframework.data.mongodb.core.mapping.event.AfterDeleteEvent;
import org.springframework.data.mongodb.core.mapping.event.AfterSaveEvent;

@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE, onConstructor = @__(@PersistenceCreator))
@Valid
@Immutable
public class AuditEvent {

  @Id
  @Field(name = "_id")
  private String id;

  @NotNull
  @PastOrPresent
  @Field(name = "datetime")
  private OffsetDateTime datetime;

  @NotEmpty
  @Field(name = "actor")
  private String actor;

  @NotNull
  @Positive
  @Field(name = "revision")
  private Long revision;

  @NotNull
  @Field(name = "type")
  private Type type;

  @NotEmpty
  @Field(name = "collectionName")
  private String collectionName;

  @NotNull
  @Field(name = "source")
  private Document source;

  public static AuditEvent of(
      final Type type,
      final Long timestamp,
      final Long revision,
      final String collectionName,
      final Document source,
      final String auditUserName) {
    return new AuditEvent(
        null,
        Instant.ofEpochMilli(timestamp).atOffset(DateTimeUtils.SYSTEM_OFFSET_ID),
        auditUserName,
        revision,
        type,
        collectionName,
        source);
  }

  public static AuditEvent ofSaveEvent(
      final AfterSaveEvent<?> event,
      final Long revision,
      final String auditUserName,
      final Optional<String> versionProperty) {
    Long version =
        versionProperty.isPresent() ? event.getDocument().getLong(versionProperty.get()) : null;
    Type type = version == null ? null : version == 0 ? Type.CREATED : Type.UPDATED;
    return of(
        type,
        event.getTimestamp(),
        revision,
        event.getCollectionName(),
        event.getDocument(),
        auditUserName);
  }

  public static AuditEvent ofDeleteEvent(
      final AfterDeleteEvent<?> event, final Long revision, final String auditUserName) {
    return of(
        Type.DELETED,
        event.getTimestamp(),
        revision,
        event.getCollectionName(),
        event.getDocument(),
        auditUserName);
  }

  public enum Type {
    CREATED,
    UPDATED,
    DELETED
  }
}
