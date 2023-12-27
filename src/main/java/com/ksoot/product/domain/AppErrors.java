package com.ksoot.product.domain;

import com.ksoot.problem.core.ErrorType;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public enum AppErrors implements ErrorType {
  AUDIT_COLLECTION_NOT_FOUND(
      "audit.collection.not.found",
      "Audit collection not found for Source collection: {0}",
      HttpStatus.BAD_REQUEST);

  private final String errorKey;

  private final String defaultDetail;

  private final HttpStatus status;

  AppErrors(final String errorKey, final String defaultDetail, final HttpStatus status) {
    this.errorKey = errorKey;
    this.defaultDetail = defaultDetail;
    this.status = status;
  }
}
