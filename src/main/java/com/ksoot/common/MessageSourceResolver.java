package com.ksoot.common;

import lombok.Builder;
import org.springframework.context.MessageSourceResolvable;
import org.springframework.context.support.AbstractMessageSource;
import org.springframework.lang.Nullable;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;

import java.io.Serializable;

/**
 * @author Rajveer Singh
 */
@Builder
@SuppressWarnings("serial")
public class MessageSourceResolver implements MessageSourceResolvable, Serializable {

  private final String[] codes;

  @Nullable private String defaultMessage;

  @Nullable private Object[] arguments;

  private MessageSourceResolver(
      final String[] codes, final @Nullable String defaultMessage, @Nullable Object[] arguments) {
    this.codes = codes;
    this.defaultMessage = defaultMessage;
    this.arguments = arguments;
  }

//  public static MessageSourceResolver of(final String code) {
//    return new MessageSourceResolver(new String[] {code}, null, null);
//  }

  public static MessageSourceResolver of(final String defaultMessage) {
    return new MessageSourceResolver(null, defaultMessage, null);
  }

  public static MessageSourceResolver of(final String[] codes) {
    return new MessageSourceResolver(codes, null, null);
  }

  public static MessageSourceResolver of(final String code, final Object[] arguments) {
    return new MessageSourceResolver(new String[] {code}, null, arguments);
  }

  public static MessageSourceResolver of(final String[] codes, final Object[] arguments) {
    return new MessageSourceResolver(codes, null, arguments);
  }

  public static MessageSourceResolver of(final String[] codes, final String defaultMessage) {
    return new MessageSourceResolver(codes, defaultMessage, null);
  }

  public static MessageSourceResolver of(final String code, final String defaultMessage) {
    return new MessageSourceResolver(new String[] {code}, defaultMessage, null);
  }

  public static MessageSourceResolver of(
      final String[] codes, final String defaultMessage, final Object[] arguments) {
    return new MessageSourceResolver(codes, defaultMessage, arguments);
  }

  public static MessageSourceResolver of(
      final String code, final String defaultMessage, final Object... arguments) {
    return new MessageSourceResolver(new String[] {code}, defaultMessage, arguments);
  }

  public static MessageSourceResolver of(final MessageResolver resolver) {
    return of(resolver.messageCode(), resolver.defaultMessage());
  }

  public static MessageSourceResolver of(final MessageSourceResolvable resolvable) {
    return of(resolvable.getCodes(), resolvable.getDefaultMessage(), resolvable.getArguments());
  }

  /** Return the default code of this resolvable, that is, the last one in the codes array. */
  @Nullable
  public String getCode() {
    return (this.codes != null && this.codes.length > 0 ? this.codes[this.codes.length - 1] : null);
  }

  @Override
  @Nullable
  public String[] getCodes() {
    return this.codes;
  }

  @Override
  @Nullable
  public Object[] getArguments() {
    return this.arguments;
  }

  @Override
  @Nullable
  public String getDefaultMessage() {
    return this.defaultMessage;
  }

  /**
   * Indicate whether the specified default message needs to be rendered for substituting
   * placeholders and/or {@link java.text.MessageFormat} escaping.
   *
   * @return {@code true} if the default message may contain argument placeholders; {@code false} if
   *     it definitely does not contain placeholders or custom escaping and can therefore be simply
   *     exposed as-is
   * @since 5.1.7
   * @see #getDefaultMessage()
   * @see #getArguments()
   * @see AbstractMessageSource#renderDefaultMessage
   */
  public boolean shouldRenderDefaultMessage() {
    return true;
  }

  /**
   * Build a default String representation for this MessageResolvable: including codes, arguments,
   * and default message.
   */
  protected final String resolvableToString() {
    StringBuilder result = new StringBuilder(64);
    result.append("codes [").append(StringUtils.arrayToDelimitedString(this.codes, ","));
    result.append("]; arguments [").append(StringUtils.arrayToDelimitedString(this.arguments, ","));
    result.append("]; default message [").append(this.defaultMessage).append(']');
    return result.toString();
  }

  /**
   * The default implementation exposes the attributes of this MessageResolvable.
   *
   * <p>To be overridden in more specific subclasses, potentially including the resolvable content
   * through {@code resolvableToString()}.
   *
   * @see #resolvableToString()
   */
  @Override
  public String toString() {
    return getClass().getName() + ": " + resolvableToString();
  }

  @Override
  public boolean equals(@Nullable Object other) {
    if (this == other) {
      return true;
    }
    if (!(other instanceof MessageSourceResolvable)) {
      return false;
    }
    MessageSourceResolvable otherResolvable = (MessageSourceResolvable) other;
    return (ObjectUtils.nullSafeEquals(getCodes(), otherResolvable.getCodes())
        && ObjectUtils.nullSafeEquals(getArguments(), otherResolvable.getArguments())
        && ObjectUtils.nullSafeEquals(getDefaultMessage(), otherResolvable.getDefaultMessage()));
  }

  @Override
  public int hashCode() {
    int hashCode = ObjectUtils.nullSafeHashCode(getCodes());
    hashCode = 29 * hashCode + ObjectUtils.nullSafeHashCode(getArguments());
    hashCode = 29 * hashCode + ObjectUtils.nullSafeHashCode(getDefaultMessage());
    return hashCode;
  }
}
