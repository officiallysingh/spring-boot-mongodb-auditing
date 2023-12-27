package com.ksoot.product.domain.mapper;

import com.ksoot.product.domain.model.Product;
import com.ksoot.product.domain.model.dto.ProductVM;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface ProductMappers {

  ProductMappers INSTANCE = Mappers.getMapper(ProductMappers.class);

  ProductVM productViewModel(final Product product);
}
