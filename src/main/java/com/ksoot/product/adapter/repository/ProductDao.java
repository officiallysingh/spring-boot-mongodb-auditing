package com.ksoot.product.adapter.repository;

import com.ksoot.product.domain.model.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface ProductDao {

    Page<Product> findPage(
            final List<String> phrases,
            final Pageable pageRequest);
}
