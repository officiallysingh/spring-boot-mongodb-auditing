package com.ksoot.product.adapter.repository;

import com.ksoot.mongodb.AuditEvent;
import com.ksoot.mongodb.AuditMetaData;
import com.ksoot.problem.core.Problems;
import com.ksoot.product.util.AppErrors;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;

import java.time.OffsetDateTime;
import java.util.List;
import java.util.Objects;

@RequiredArgsConstructor
@Repository
public class AuditHistoryRepository {

    private final MongoOperations mongoOperations;

    private final AuditMetaData auditMetaData;

    public Page<AuditEvent> getAuditHistory(final String collectionName,
                                            final AuditEvent.Type type,
                                            final String actor,
                                            final OffsetDateTime fromDateTime,
                                            final OffsetDateTime tillDateTime,
                                            final Pageable pageRequest) {
        if(!this.auditMetaData.isPresent(collectionName)) {
            throw Problems.newInstance(AppErrors.AUDIT_COLLECTION_NOT_FOUND).detailArgs(collectionName).throwAble();
        }

        String auditCollectionName = this.auditMetaData.getAuditCollection(collectionName).get();
        final Query query = new Query();
//        if (StringUtils.isNotBlank(collectionName)) {
//            query.addCriteria(Criteria.where("collectionName").is(collectionName));
//        }
        if (Objects.nonNull(type)) {
            query.addCriteria(Criteria.where("type").is(type));
        }
        if (StringUtils.isNotBlank(actor)) {
            query.addCriteria(Criteria.where("actor").is(actor));
        }
        if (Objects.nonNull(fromDateTime) && Objects.nonNull(tillDateTime)) {
            query.addCriteria(
                    Criteria.where("datetime")
                            .gte(fromDateTime)
                            .andOperator(Criteria.where("datetime").lte(tillDateTime)));
        } else if (Objects.nonNull(fromDateTime)) {
            query.addCriteria(Criteria.where("datetime").gte(fromDateTime));
        } else if (Objects.nonNull(tillDateTime)) {
            query.addCriteria(Criteria.where("datetime").lte(tillDateTime));
        }
        final long totalRecords = this.mongoOperations.count(query, auditCollectionName);
        if (totalRecords == 0) {
            return Page.empty();
        } else {
            final Pageable pageable =
                    totalRecords <= pageRequest.getPageSize()
                            ? PageRequest.of(0, pageRequest.getPageSize(), pageRequest.getSort())
                            : pageRequest;
            final List<AuditEvent> feeMovementRecords =
                    this.mongoOperations.find(query.with(pageable), AuditEvent.class, auditCollectionName);
            return new PageImpl<>(feeMovementRecords, pageable, totalRecords);
        }
    }
}
