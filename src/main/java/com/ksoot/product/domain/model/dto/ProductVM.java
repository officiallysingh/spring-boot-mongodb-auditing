package com.ksoot.product.domain.model.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import java.util.List;
import java.util.Map;
import lombok.Builder;

@Builder
public record ProductVM(
    @Schema(description = "Internal record id", example = "6558c30160463a1fee00c7dc") String id,
    String name,
    String description,
    List<String> tags,
    Map<String, String> attributes) {}
