package com.ksoot.product.adapter.controller;

import static com.ksoot.common.ApiConstants.*;
import static com.ksoot.common.ApiStatus.*;
import static com.ksoot.common.CommonConstants.DEFAULT_PAGE_SIZE;

import com.ksoot.common.APIResponse;
import com.ksoot.common.Api;
import com.ksoot.common.PaginatedResource;
import com.ksoot.product.domain.model.dto.ProductCreationRQ;
import com.ksoot.product.domain.model.dto.ProductUpdationRQ;
import com.ksoot.product.domain.model.dto.ProductVM;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import java.util.List;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequestMapping("/v1/products")
@Tag(name = "Product", description = "management APIs")
public interface ProductApi extends Api {

    @Operation(
            operationId = "create-product",
            summary = "Creates a Product")
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = SC_201, description = "Product created successfully"),
                    @ApiResponse(
                            responseCode = SC_400,
                            description = "Bad request",
                            content = @Content(examples = @ExampleObject(BAD_REQUEST_EXAMPLE_RESPONSE)))
            })
    @PostMapping(
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<APIResponse<?>> createProduct(
            @Parameter(description = "Create Product request", required = true) @RequestBody @Valid final ProductCreationRQ request);

    @GetMapping(path = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(
            operationId = "get-product-by-id",
            summary = "Gets a Product by id")
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = SC_200, description = "Product returned successfully"),
                    @ApiResponse(
                            responseCode = SC_404,
                            description = "Requested Product not found",
                            content = @Content(examples = @ExampleObject(NOT_FOUND_EXAMPLE_RESPONSE)))
            })
    ResponseEntity<ProductVM> getProduct(
            @Parameter(
                    description = "Product Id",
                    required = true,
                    example = "6558c30160463a1fee00c7dc")
            @PathVariable(name = "id") final String id);

    @PatchMapping(
            path = "/{id}",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(
            operationId = "update-product",
            summary = "Updates a Product")
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "200", description = "Product updated successfully"),
                    @ApiResponse(
                            responseCode = SC_400,
                            description = "Bad request",
                            content = @Content(examples = @ExampleObject(BAD_REQUEST_EXAMPLE_RESPONSE))),
                    @ApiResponse(
                            responseCode = "404",
                            description = "Product not found",
                            content = @Content(examples = @ExampleObject(NOT_FOUND_EXAMPLE_RESPONSE)))
            })
    ResponseEntity<APIResponse<?>> updateProduct(
            @Parameter(
                    description = "Product Id",
                    required = true,
                    example = "6558c30160463a1fee00c7dc")
            @PathVariable(name = "id") final String id,
            @Parameter(description = "Update Product request", required = true) @RequestBody @Valid final ProductUpdationRQ request);

    @GetMapping
    @Operation(
            operationId = "get-products-page",
            summary = "Gets a page of Products")
    @ApiResponses(
            value = {
                    @ApiResponse(
                            responseCode = "200",
                            description =
                                    "Products page returned successfully. Returns an empty page if no records found"),
                    @ApiResponse(
                            responseCode = "500",
                            description = "Internal Server error",
                            content = @Content(examples = @ExampleObject(INTERNAL_SERVER_ERROR_EXAMPLE_RESPONSE)))
            })
    PaginatedResource<ProductVM> getProducts(
            @Parameter(description = "Phrases in product's name, description or tag")
            @RequestParam(required = false) final List<String> phrases,
            @ParameterObject @PageableDefault(size = DEFAULT_PAGE_SIZE) final Pageable pageRequest);

    @Operation(
            operationId = "delete-product",
            summary = "Deletes a Product")
    @ApiResponses(
            value = {
                    @ApiResponse(
                            responseCode = SC_200,
                            description = "Product deleted successfully",
                            content = @Content(examples = @ExampleObject(RECORD_DELETED_RESPONSE))),
                    @ApiResponse(
                            responseCode = SC_404,
                            description = "Requested Product not found",
                            content = @Content(examples = @ExampleObject(NOT_FOUND_EXAMPLE_RESPONSE)))
            })
    @DeleteMapping(path = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<APIResponse<?>> deleteProduct(
            @Parameter(
                    description = "Product Id",
                    required = true,
                    example = "6558c30160463a1fee00c7dc")
            @PathVariable(name = "id") final String id);
}
