package com.ksoot.mongodb;

import java.util.Map;
import java.util.Optional;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.util.ConcurrentReferenceHashMap;

@Component
public class AuditMetaData {

  private final Map<String, Metadata> metadata;

  AuditMetaData() {
    this.metadata = new ConcurrentReferenceHashMap<>();
  }

  void put(final String collectionName, final Metadata metadata) {
    this.metadata.put(collectionName, metadata);
  }

  public Optional<String> getAuditCollection(final String collectionName) {
    return this.metadata.containsKey(collectionName)
        ? Optional.of(this.metadata.get(collectionName).auditCollectionName)
        : Optional.empty();
  }

  public Optional<String> getVersionProperty(final String collectionName) {
    return this.metadata.containsKey(collectionName)
        ? Optional.ofNullable(this.metadata.get(collectionName).versionProperty)
        : Optional.empty();
  }

  public boolean isPresent(final String collectionName) {
    return this.metadata.containsKey(collectionName);
  }

  @AllArgsConstructor(staticName = "of", access = AccessLevel.PACKAGE)
  static class Metadata {

    private String auditCollectionName;

    private String versionProperty;
  }
}
