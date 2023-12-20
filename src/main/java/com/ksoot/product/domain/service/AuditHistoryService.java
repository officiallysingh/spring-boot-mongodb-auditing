package com.ksoot.product.domain.service;

import com.ksoot.mongodb.AuditEvent;
import com.ksoot.product.adapter.repository.AuditHistoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.OffsetDateTime;

@Service
@RequiredArgsConstructor
public class AuditHistoryService {

    private final AuditHistoryRepository auditHistoryRepository;

    public Page<AuditEvent> getAuditHistory(final String collectionName,
                                            final AuditEvent.Type type,
                                            final String actor,
                                            final OffsetDateTime fromDateTime,
                                            final OffsetDateTime tillDateTime,
                                            final Pageable pageRequest) {
        return this.auditHistoryRepository.getAuditHistory(collectionName, type, actor, fromDateTime, tillDateTime, pageRequest);
    }
}
