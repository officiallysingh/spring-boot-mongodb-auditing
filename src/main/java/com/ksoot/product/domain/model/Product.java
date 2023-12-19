package com.ksoot.product.domain.model;

import com.ksoot.mongodb.AbstractEntity;
import com.ksoot.mongodb.Auditable;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.*;
import org.springframework.data.annotation.TypeAlias;
import org.springframework.data.mongodb.core.index.TextIndexed;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.TextScore;

import java.util.List;
import java.util.Map;

@Getter
@Setter
@Builder
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@AllArgsConstructor(access = AccessLevel.PACKAGE)
//@AllArgsConstructor(staticName = "of")
@Document(collection = "products")
@TypeAlias("product")
@Auditable
public class Product extends AbstractEntity {

    @NotEmpty
    @Size(min = 2, max = 256)
    @TextIndexed(weight = 3)
    private String name;

    @Size(min = 2, max = 256)
    @TextIndexed(weight = 1)
    private String description;

    @TextIndexed(weight = 2)
    @Size(max = 5)
    private List<@NotEmpty @Size(min = 2, max = 10) String> tags;

    private Map<@NotEmpty @Size(min = 2, max = 50) String, @NotEmpty @Size(min = 2, max = 256) String> attributes;

    @TextScore
    @Getter(AccessLevel.PACKAGE)
    @Setter(AccessLevel.PACKAGE)
    private Float score;
}
