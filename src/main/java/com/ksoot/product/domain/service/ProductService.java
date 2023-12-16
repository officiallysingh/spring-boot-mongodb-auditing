package com.ksoot.product.domain.service;

import com.ksoot.product.domain.model.Product;
import com.ksoot.product.domain.model.dto.ProductCreationRQ;
import com.ksoot.product.domain.model.dto.ProductUpdationRQ;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface ProductService {

    Product createProduct(final ProductCreationRQ request);

    Product getProductById(final String id);

    Page<Product> getProducts(final List<String> phrases,
                              final Pageable pageRequest);

    Product updateProduct(final String id, final ProductUpdationRQ request);

    void deleteProduct(final String id);
}
