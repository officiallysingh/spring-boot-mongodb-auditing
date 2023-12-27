package com.ksoot.product.domain.model.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import java.util.List;

public record ProductSummary(
    @Schema(description = "Internal record id", example = "6558c30160463a1fee00c7dc") String id,
    String name,
    String description,
    List<String> tags) {}
