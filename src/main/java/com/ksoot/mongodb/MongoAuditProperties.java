package com.ksoot.mongodb;

import jakarta.validation.Valid;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.List;

/**
 * @author Rajveer Singh
 */
@Getter
@Setter
@NoArgsConstructor
@ToString
@ConditionalOnProperty(prefix = "application.mongodb.auditing", name = "enabled", havingValue = "true")
@ConfigurationProperties(prefix = "application.mongodb")
@Valid
public class MongoAuditProperties {

  private List<String> entityBasePackages = List.of("com.ksoot.product");

  private Auditing auditing = new Auditing();

  @Getter
  @Setter
  @NoArgsConstructor
  @ToString
  @Valid
  class Auditing {
    private boolean enabled = false;
    private boolean withoutTransaction = false;
    private String prefix = "";
    private String suffix = "_aud";
  }
}
