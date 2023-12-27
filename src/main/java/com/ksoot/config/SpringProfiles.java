package com.ksoot.config;

import java.util.*;
import org.springframework.boot.SpringApplication;
import org.springframework.core.env.Environment;

public class SpringProfiles {

  public static final String LOCAL = "local";

  public static final String DEVELOPMENT = "dev";

  public static final String STAGE = "stage";

  public static final String PRODUCTION = "prod";

  public static final String TEST = "test";

  private static final String FALLBACK_DEFAULT_PROFILE = "default";

  private static List<String> ACTIVE_PROFILES;

  private static Environment environment;

  public SpringProfiles(final Environment environment) {
    SpringProfiles.environment = environment;
  }

  public static void addDefaultProfile(final SpringApplication app) {
    Map<String, Object> defaultProfiles = new HashMap<>(1);
    app.setDefaultProperties(defaultProfiles);
  }

  public static void addAdditionalProfiles(
      final SpringApplication app, final String[] additionalProfiles) {
    app.setAdditionalProfiles(additionalProfiles);
  }

  public static List<String> getActiveProfiles() {
    if (ACTIVE_PROFILES == null) {
      if (environment.getActiveProfiles().length == 0) {
        ACTIVE_PROFILES = Arrays.asList(environment.getDefaultProfiles());
        return ACTIVE_PROFILES;
      } else {
        ACTIVE_PROFILES = Arrays.asList(environment.getActiveProfiles());
        return ACTIVE_PROFILES;
      }
    } else {
      return ACTIVE_PROFILES;
    }
  }

  public static boolean isLocal() {
    return getActiveProfiles().contains(LOCAL);
  }

  public static boolean isDev() {
    return getActiveProfiles().contains(DEVELOPMENT);
  }

  public static boolean isStage() {
    return getActiveProfiles().contains(STAGE);
  }

  public static boolean isProd() {
    return getActiveProfiles().contains(PRODUCTION);
  }

  public static boolean isTest() {
    return getActiveProfiles().contains(TEST);
  }

  public static boolean isEnabled(final String profile) {
    return getActiveProfiles().contains(profile);
  }

  public static Set<String> exclusiveProfiles() {
    return Set.of(FALLBACK_DEFAULT_PROFILE, LOCAL, DEVELOPMENT, STAGE, PRODUCTION);
  }
}
