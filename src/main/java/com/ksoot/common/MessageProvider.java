package com.ksoot.common;

import org.springframework.context.MessageSource;
import org.springframework.context.MessageSourceResolvable;
import org.springframework.lang.NonNull;

import java.util.Locale;

/**
 * @author Rajveer Singh
 */
public class MessageProvider {

  private static MessageSource messageSource;

  public MessageProvider(@NonNull final MessageSource messageSource) {
    MessageProvider.messageSource = messageSource;
  }

  public static String getMessage(final String messageCode, final String defaultMessage) {
    return messageSource.getMessage(messageCode, null, defaultMessage, Locale.getDefault());
  }

  public static String getMessage(
      final String messageCode, final String defaultMessage, final Object... params) {
    return messageSource.getMessage(messageCode, params, defaultMessage, Locale.getDefault());
  }

  public static String getMessage(final MessageResolver messageResolver) {
    return messageSource.getMessage(
        messageResolver.messageCode(), null, messageResolver.defaultMessage(), Locale.getDefault());
  }

  public static String getMessage(final MessageResolver messageResolver, final Object... params) {
    return messageSource.getMessage(
        messageResolver.messageCode(),
        params,
        messageResolver.defaultMessage(),
        Locale.getDefault());
  }

  public static String getMessage(final MessageSourceResolvable resolvable) {
    return messageSource.getMessage(resolvable, Locale.getDefault());
  }
}
