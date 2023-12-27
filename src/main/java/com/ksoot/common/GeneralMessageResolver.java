package com.ksoot.common;

/**
 * @author Rajveer Singh
 */
public enum GeneralMessageResolver implements MessageResolver {

  // @formatter:off
  RECORD_CREATED("record.created.successfully", "Record created successfully"),
  RECORD_UPDATED("record.updated.successfully", "Record updated successfully"),
  RECORD_DELETED("record.deleted.successfully", "Record deleted successfully"),
  NO_RECORDS_FOUND("no.records.found", "No records found matching selection criteria"),
  NO_RECORD_FOUND("no.record.found", "No such record exists");
  // @formatter:on

  private final String messageCode;

  private final String defaultMessage;

  GeneralMessageResolver(final String messageCode, final String defaultMessage) {
    this.messageCode = messageCode;
    this.defaultMessage = defaultMessage;
  }

  @Override
  public String messageCode() {
    return this.messageCode;
  }

  @Override
  public String defaultMessage() {
    return this.defaultMessage;
  }
}
