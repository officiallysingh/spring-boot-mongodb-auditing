package com.ksoot.mongodb;

import jakarta.validation.Valid;
import java.util.List;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * @author Rajveer Singh
 */
@Getter
@Setter
@NoArgsConstructor
@ToString
@ConditionalOnProperty(
    prefix = "application.mongodb.auditing",
    name = "enabled",
    havingValue = "true")
@ConfigurationProperties(prefix = "application.mongodb")
@Valid
public class MongoAuditProperties {

  /**
   * Default: Main class package name. List of packages to scan for MongoDB entities.
   */
  private List<String> entityBasePackages;

  private Auditing auditing = new Auditing();

  @Getter
  @Setter
  @NoArgsConstructor
  @ToString
  @Valid
  class Auditing {

    /**
     * Default: true, Whether or not to enable MongoDB Auditing.
     */
    private boolean enabled = false;

    /**
     * Default: false, Whether or not to do Auditing without Transactions.
     */
    private boolean withoutTransaction = false;

    /**
     * Default: "", Audit collection name prefix.
     */
    private String prefix = "";

    /**
     * Default: "", Audit collection name suffix.
     */
    private String suffix = "_aud";
  }
}
