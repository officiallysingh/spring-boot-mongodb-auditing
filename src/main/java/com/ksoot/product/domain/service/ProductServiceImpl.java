package com.ksoot.product.domain.service;

import com.ksoot.problem.core.Problems;
import com.ksoot.product.adapter.repository.ProductRepository;
import com.ksoot.product.domain.model.Product;
import com.ksoot.product.domain.model.dto.ProductCreationRQ;
import com.ksoot.product.domain.model.dto.ProductUpdationRQ;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;

    @Transactional
    @Override
    public Product createProduct(final ProductCreationRQ request) {
        final Product product =
                Product.builder()
                        .name(request.name())
                        .description(request.description())
                        .tags(request.tags())
                        .attributes(request.attributes())
                        .build();
        return this.productRepository.save(product);
    }

    @Transactional(readOnly = true)
    @Override
    public Product getProductById(final String id) {
        return this.productRepository.findById(id).orElseThrow(Problems::notFound);
    }

    @Transactional(readOnly = true)
    @Override
    public Page<Product> getProducts(final List<String> phrases,
                                     final Pageable pageRequest) {
        return this.productRepository.findPage(phrases, pageRequest);
    }

    @Transactional
    @Override
    public Product updateProduct(final String id, final ProductUpdationRQ request) {
        final Product product = this.productRepository.findById(id).orElseThrow(Problems::notFound);

        Optional.ofNullable(request.name()).ifPresent(product::setName);
        Optional.ofNullable(request.description()).ifPresent(product::setDescription);
        Optional.ofNullable(request.tags()).ifPresent(product::setTags);
        Optional.ofNullable(request.attributes()).ifPresent(product::setAttributes);

        return this.productRepository.save(product);
    }

    @Transactional
    @Override
    public void deleteProduct(final String id) {
        if (!this.productRepository.existsById(id)) {
            throw Problems.notFound();
        }
        this.productRepository.deleteById(id);
    }
}
