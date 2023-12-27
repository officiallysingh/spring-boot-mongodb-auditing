package com.ksoot.product.adapter.repository;

import com.ksoot.product.domain.model.Product;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface ProductRepository extends ProductDao, MongoRepository<Product, String> {}
