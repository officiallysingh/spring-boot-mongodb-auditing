package com.ksoot.product.adapter.controller;

import com.ksoot.common.APIResponse;
import com.ksoot.common.GeneralMessageResolver;
import com.ksoot.common.PaginatedResource;
import com.ksoot.common.PaginatedResourceAssembler;
import com.ksoot.mongodb.AuditEvent;
import com.ksoot.problem.core.Problems;
import com.ksoot.product.domain.mapper.ProductMappers;
import com.ksoot.product.domain.model.Product;
import com.ksoot.product.domain.model.dto.ProductCreationRQ;
import com.ksoot.product.domain.model.dto.ProductUpdationRQ;
import com.ksoot.product.domain.model.dto.ProductVM;
import com.ksoot.product.domain.service.AuditHistoryService;
import com.ksoot.product.domain.service.ProductService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.OffsetDateTime;
import java.util.List;
import java.util.function.Function;

import static com.ksoot.common.ApiConstants.INTERNAL_SERVER_ERROR_EXAMPLE_RESPONSE;
import static com.ksoot.common.CommonConstants.DEFAULT_PAGE_SIZE;
import static com.ksoot.common.CommonErrorKeys.EMPTY_UPDATE_REQUEST;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RestController
@RequestMapping("/v1/audit-history")
@Tag(name = "Audit History", description = "fetching APIs")
@RequiredArgsConstructor
class AuditHistoryController {

    private final AuditHistoryService auditHistoryService;

    @GetMapping
    @Operation(
            operationId = "get-audit-history",
            summary = "Gets a page of Audit History")
    @ApiResponses(
            value = {
                    @ApiResponse(
                            responseCode = "200",
                            description =
                                    "Audit History page returned successfully. Returns an empty page if no records found"),
                    @ApiResponse(
                            responseCode = "500",
                            description = "Internal Server error",
                            content = @Content(examples = @ExampleObject(INTERNAL_SERVER_ERROR_EXAMPLE_RESPONSE)))
            })
    public PaginatedResource<AuditEvent> getProducts(
            @Parameter(description = "Source MongoDB Collection name")
            @RequestParam(required = false)
            final String collectionName,
            @Parameter(description = "Audit Event type")
            @RequestParam(required = false)
            final AuditEvent.Type type,
            @Parameter(description = "Audit Username")
            @RequestParam(required = false)
            final String actor,
            @Parameter(description = "From Datetime, Inclusive")
            @RequestParam(required = false)
            final OffsetDateTime fromDateTime,
            @Parameter(description = "Till Datetime, Inclusive")
            @RequestParam(required = false)
            final OffsetDateTime tillDateTime,
            @ParameterObject @PageableDefault(size = DEFAULT_PAGE_SIZE) final Pageable pageRequest) {
        final Page<AuditEvent> feePage = this.auditHistoryService.getAuditHistory(collectionName, type,
                actor, fromDateTime, tillDateTime, pageRequest);
        return PaginatedResourceAssembler.assemble(feePage);
    }
}
