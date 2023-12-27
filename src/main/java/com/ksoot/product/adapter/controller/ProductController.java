package com.ksoot.product.adapter.controller;

import static com.ksoot.common.CommonErrorKeys.EMPTY_UPDATE_REQUEST;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

import com.ksoot.common.APIResponse;
import com.ksoot.common.GeneralMessageResolver;
import com.ksoot.common.PaginatedResource;
import com.ksoot.common.PaginatedResourceAssembler;
import com.ksoot.problem.core.Problems;
import com.ksoot.product.domain.mapper.ProductMappers;
import com.ksoot.product.domain.model.Product;
import com.ksoot.product.domain.model.dto.ProductCreationRQ;
import com.ksoot.product.domain.model.dto.ProductUpdationRQ;
import com.ksoot.product.domain.model.dto.ProductVM;
import com.ksoot.product.domain.service.ProductService;
import io.swagger.v3.oas.annotations.Parameter;
import java.util.List;
import java.util.function.Function;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
class ProductController implements ProductApi {

  private static final Function<List<Product>, List<ProductVM>> PRODUCT_PAGE_TRANSFORMER =
      fee -> fee.stream().map(ProductMappers.INSTANCE::productViewModel).toList();

  private final ProductService productService;

  @Override
  public ResponseEntity<APIResponse<?>> createProduct(final ProductCreationRQ request) {
    final String id = this.productService.createProduct(request).getId();
    return ResponseEntity.created(
            linkTo(methodOn(ProductController.class).getProduct(id)).withSelfRel().toUri())
        .body(APIResponse.newInstance().addMessage(GeneralMessageResolver.RECORD_CREATED));
  }

  @Override
  public ResponseEntity<ProductVM> getProduct(final String id) {
    return ResponseEntity.ok(
        ProductMappers.INSTANCE.productViewModel(this.productService.getProductById(id)));
  }

  @Override
  public ResponseEntity<APIResponse<?>> updateProduct(
      final String id, final ProductUpdationRQ request) {
    if (request.isEmpty()) {
      throw Problems.newInstance(EMPTY_UPDATE_REQUEST).throwAble(HttpStatus.BAD_REQUEST);
    }
    final Product state = this.productService.updateProduct(id, request);
    return ResponseEntity.ok()
        .location(
            linkTo(methodOn(ProductController.class).getProduct(state.getId()))
                .withSelfRel()
                .toUri())
        .body(APIResponse.newInstance().addMessage(GeneralMessageResolver.RECORD_UPDATED));
  }

  @Override
  public PaginatedResource<ProductVM> getProducts(
      @Parameter(description = "Phrases in product's name, description or tag")
          @RequestParam(required = false)
          final List<String> phrases,
      final Pageable pageRequest) {
    final Page<Product> feePage = this.productService.getProducts(phrases, pageRequest);
    return PaginatedResourceAssembler.assemble(feePage, PRODUCT_PAGE_TRANSFORMER);
  }

  @Override
  public ResponseEntity<APIResponse<?>> deleteProduct(final String id) {
    this.productService.deleteProduct(id);
    return ResponseEntity.ok(
        APIResponse.newInstance().addMessage(GeneralMessageResolver.RECORD_DELETED));
  }
}
