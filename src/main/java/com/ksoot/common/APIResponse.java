package com.ksoot.common;

import com.fasterxml.jackson.annotation.JsonAnyGetter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.common.collect.Sets;
import com.ksoot.common.MessageProvider;
import com.ksoot.common.MessageResolver;
import lombok.Getter;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.collections4.MapUtils;
import org.springframework.context.MessageSourceResolvable;
import org.springframework.util.Assert;

import java.util.*;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({"messages", "warnings", "errorCount", "errors"})
public class APIResponse<T> {

  private static final Set<String> RESERVED_PROPERTIES =
      new HashSet<>(Arrays.asList("messages", "warnings", "errorCount", "errors"));

  private final Set<String> messages;

  private final Set<String> warnings;

  @Getter
  @JsonInclude(JsonInclude.Include.NON_DEFAULT)
  private Integer errorCount;

  @Getter
  @JsonInclude(JsonInclude.Include.NON_EMPTY)
  private final List<T> errors;

  @JsonInclude(JsonInclude.Include.NON_EMPTY)
  private final Map<String, Object> values;

  @JsonAnyGetter
  @JsonInclude(JsonInclude.Include.NON_EMPTY)
  public Map<String, Object> getValues() {
    if (CollectionUtils.isNotEmpty(this.messages) || CollectionUtils.isNotEmpty(this.warnings)) {
      Map<String, Object> returnValues = Maps.newLinkedHashMap();
      if (CollectionUtils.isNotEmpty(this.messages)) {
        returnValues.put("messages", this.messages);
      }
      if (CollectionUtils.isNotEmpty(this.warnings)) {
        returnValues.put("warnings", this.warnings);
      }
      returnValues.putAll(this.values);
      return returnValues;
    } else {
      return Collections.unmodifiableMap(this.values);
    }
  }

  private APIResponse(final List<T> errors) {
    this.messages = Sets.newLinkedHashSet();
    this.warnings = Sets.newLinkedHashSet();
    this.errors = errors;
    this.errorCount = errors.size();
    this.values = Maps.newLinkedHashMap();
  }

  private APIResponse(final Map<String, Object> values) {
    this.messages = Sets.newLinkedHashSet();
    this.warnings = Sets.newLinkedHashSet();
    this.errors = Lists.newArrayList();
    this.values = values;
  }

  private APIResponse() {
    this.messages = Sets.newLinkedHashSet();
    this.warnings = Sets.newLinkedHashSet();
    this.errors = Lists.newArrayList();
    this.values = Maps.newLinkedHashMap();
  }

  public static <T> APIResponse<T> newInstance() {
    return new APIResponse<>();
  }

  public static <T> APIResponse<T> of(final T error) {
    Assert.notNull(error, "'error' must not be null");
    return new APIResponse<>(Arrays.asList(error));
  }

  public static <T> APIResponse<T> of(final List<T> errors) {
    Assert.notEmpty(errors, "'errors' must not be null or empty");
    Assert.noNullElements(errors, "'errors' must not be contain null elements");
    APIResponse<T> apiResponse = newInstance();
    return apiResponse.addErrors(errors);
  }

  public static <T> APIResponse<T> of(final String key, final Object value) {
    Assert.hasText(key, "'key' must not be null or empty");
    Assert.isTrue(!RESERVED_PROPERTIES.contains(key), "Property " + key + " is reserved");
    Map<String, Object> values = Maps.newLinkedHashMap();
    values.put(key, value);
    return new APIResponse<>(values);
  }

  public static <T> APIResponse<T> of(final Map<String, Object> values) {
    Assert.notEmpty(values, "'values' must not be null or empty");
    Map<String, Object> valuesInternal = Maps.newLinkedHashMap();
    values.forEach(
        (key, value) -> {
          Assert.isTrue(!RESERVED_PROPERTIES.contains(key), "Property " + key + " is reserved");
          valuesInternal.put(key, value);
        });
    return new APIResponse<>(valuesInternal);
  }

  public APIResponse<T> addValue(final String key, final Object value) {
    Assert.hasText(key, "'key' must not be null or empty");
    Assert.isTrue(!RESERVED_PROPERTIES.contains(key), "Property " + key + " is reserved");
    this.values.put(key, value);
    return this;
  }

  public APIResponse<T> addValues(final Map<String, Object> values) {
    if (MapUtils.isNotEmpty(values)) {
      values.entrySet().stream().forEach(entry -> this.addValue(entry.getKey(), entry.getValue()));
    }
    return this;
  }

  @SuppressWarnings("unchecked")
  public APIResponse<T> addErrors(final T... errors) {
    Assert.notEmpty(errors, "'errors' must not be null or empty");
    Assert.noNullElements(errors, "'errors' must not be not contain null elements");
    this.errors.addAll(Arrays.asList(errors));
    this.errorCount += errors.length;
    return this;
  }

  public APIResponse<T> addErrors(final List<T> errors) {
    Assert.notEmpty(errors, "'errors' must not be null or empty");
    Assert.noNullElements(errors, "'errors' must not be not contain null elements");
    this.errors.addAll(errors);
    this.errorCount += errors.size();
    return this;
  }

  public APIResponse<T> addMessage(final String... message) {
    Assert.notEmpty(message, "'message' must not be null or empty");
    Assert.noNullElements(message, "'message' must not be not contain null elements");
    this.messages.addAll(Arrays.asList(message));
    return this;
  }

  public APIResponse<T> addMessage(final MessageResolver... message) {
    Assert.notEmpty(message, "'message' must not be null or empty");
    Assert.noNullElements(message, "'message' must not be not contain null elements");
    this.messages.addAll(Arrays.stream(message).map(MessageProvider::getMessage).toList());
    return this;
  }

  public APIResponse<T> addMessage(final MessageSourceResolvable... message) {
    Assert.notEmpty(message, "'message' must not be null or empty");
    Assert.noNullElements(message, "'message' must not be not contain null elements");
    this.messages.addAll(Arrays.stream(message).map(MessageProvider::getMessage).toList());
    return this;
  }

  public APIResponse<T> addWarning(final String... message) {
    Assert.notEmpty(message, "'message' must not be null or empty");
    Assert.noNullElements(message, "'message' must not be not contain null elements");
    this.warnings.addAll(Arrays.asList(message));
    return this;
  }

  public APIResponse<T> addWarning(final MessageResolver... message) {
    Assert.notEmpty(message, "'message' must not be null or empty");
    Assert.noNullElements(message, "'message' must not be not contain null elements");
    this.warnings.addAll(Arrays.stream(message).map(MessageProvider::getMessage).toList());
    return this;
  }

  public APIResponse<T> addWarning(final MessageSourceResolvable... message) {
    Assert.notEmpty(message, "'message' must not be null or empty");
    Assert.noNullElements(message, "'message' must not be not contain null elements");
    this.warnings.addAll(Arrays.stream(message).map(MessageProvider::getMessage).toList());
    return this;
  }

  @JsonIgnore
  public boolean hasErrors() {
    return this.errorCount != null && this.errorCount.intValue() != 0;
  }

  @JsonIgnore
  public boolean hasData() {
    return MapUtils.isNotEmpty(this.values);
  }
}
