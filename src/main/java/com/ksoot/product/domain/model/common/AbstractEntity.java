package com.ksoot.product.domain.model.common;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Version;

@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id")
public abstract class AbstractEntity implements Identifiable<String>, Versionable<Long> {

  @Id protected String id;

  @Version protected Long version;

  @Override
  public String getId() {
    return this.id;
  }

  @Override
  public Long getVersion() {
    return this.version;
  }
}
