package com.ksoot.product.adapter.repository;

import com.ksoot.product.domain.model.Product;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.TextCriteria;
import org.springframework.data.mongodb.core.query.TextQuery;
import org.springframework.stereotype.Repository;

@RequiredArgsConstructor
@Repository
public class ProductRepositoryImpl implements ProductDao {

  private final MongoOperations mongoOperations;

  @Override
  public Page<Product> findPage(final List<String> phrases, final Pageable pageRequest) {

    Query query = new Query();
    if (CollectionUtils.isNotEmpty(phrases)) {
      TextCriteria criteria = TextCriteria.forDefaultLanguage();
      criteria.matchingAny(phrases.toArray(new String[phrases.size()]));
      query = TextQuery.queryText(criteria).sortByScore();
    }

    final long totalRecords = this.mongoOperations.count(query, Product.class);
    if (totalRecords == 0) {
      return Page.empty();
    } else {
      final Pageable pageable =
          totalRecords <= pageRequest.getPageSize()
              ? PageRequest.of(0, pageRequest.getPageSize(), pageRequest.getSort())
              : pageRequest;
      final List<Product> products = this.mongoOperations.find(query.with(pageable), Product.class);
      return new PageImpl<>(products, pageable, totalRecords);
    }
  }
}
