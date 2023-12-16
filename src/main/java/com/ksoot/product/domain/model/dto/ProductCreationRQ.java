package com.ksoot.product.domain.model.dto;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.Builder;

import java.util.List;
import java.util.Map;

@Builder
@Valid
public record ProductCreationRQ(
        @NotEmpty
        @Size(min = 2, max = 256)
        String name,
        @Size(min = 2, max = 256) String description,
        @Size(max = 5)
        List<@NotEmpty @Size(min = 2, max = 10) String> tags,
        Map<@NotEmpty @Size(min = 2, max = 50) String, @NotEmpty @Size(min = 2, max = 256) String> attributes) {
}
