package com.ksoot.mongodb;

import org.springframework.stereotype.Component;
import org.springframework.util.ConcurrentReferenceHashMap;

import java.util.Map;
import java.util.Optional;

@Component
public class AuditMetaData {

    private Map<String, String> auditCollections;

    AuditMetaData() {
        this.auditCollections = new ConcurrentReferenceHashMap<>();
    }

    void put(final String collectionName, final String auditCollectionName) {
        this.auditCollections.put(collectionName, auditCollectionName);
    }

    public Optional<String> getAuditCollection(final String collectionName) {
        return Optional.ofNullable(this.auditCollections.getOrDefault(collectionName, null));
    }

    public boolean isPresent(final String collectionName) {
        return this.auditCollections.containsKey(collectionName);
    }
}
