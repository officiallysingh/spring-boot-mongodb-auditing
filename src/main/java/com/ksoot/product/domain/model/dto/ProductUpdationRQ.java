package com.ksoot.product.domain.model.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import java.util.List;
import java.util.Map;
import lombok.Builder;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.collections4.MapUtils;
import org.apache.commons.lang3.StringUtils;

@Builder
@Valid
public record ProductUpdationRQ(
    String name,
    @Size(min = 2, max = 256) String description,
    List<@NotEmpty @Size(min = 2, max = 10) String> tags,
    Map<@NotEmpty @Size(min = 2, max = 50) String, @NotEmpty @Size(min = 2, max = 256) String>
        attributes) {

  @JsonIgnore
  public boolean isEmpty() {
    return StringUtils.isEmpty(this.name)
        && StringUtils.isEmpty(this.description)
        && CollectionUtils.isEmpty(this.tags)
        && MapUtils.isEmpty(this.attributes);
  }
}
