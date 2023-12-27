package com.ksoot.config;

import com.google.common.collect.Sets;
import com.google.common.collect.Sets.SetView;
import com.ksoot.common.ClassUtils;
import com.ksoot.common.CommonConstants;
import com.ksoot.common.DateTimeUtils;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Arrays;
import java.util.Set;
import java.util.stream.Collectors;
import lombok.extern.log4j.Log4j2;
import org.apache.commons.lang3.StringUtils;
import org.springframework.boot.WebApplicationType;
import org.springframework.core.env.Environment;

@Log4j2
public class AppInitializer {

  static void initialize(final Environment env, final WebApplicationType webApplicationType) {
    log.info("System time zone: " + DateTimeUtils.ZONE_DISPALY_NAME);
    validateProfiles(env);
    logApplicationStartup(webApplicationType, env);
  }

  private static void validateProfiles(final Environment env) {
    Set<String> activeProfiles = Sets.newHashSet(env.getActiveProfiles());
    Set<String> defaultProfiles = Sets.newHashSet(env.getDefaultProfiles());
    Set<String> exclusiveProfiles = SpringProfiles.exclusiveProfiles();
    SetView<String> intersection =
        Sets.intersection(
            exclusiveProfiles, activeProfiles.isEmpty() ? defaultProfiles : activeProfiles);

    if (intersection.isEmpty()) {
      log.error(
          "You have misconfigured your application! It should run "
              + "with exactly one of active profiles: "
              + exclusiveProfiles
              + ", depending on environment.");
    } else if (intersection.size() > 1) {
      log.error(
          "You have misconfigured your application! It should not run with active profiles "
              + intersection
              + " togeather. Exactly one of profiles: "
              + exclusiveProfiles
              + " must be active, depending on environment.");
    }

    // new MutuallyExclusiveConfigurationPropertiesException(exclusiveProfiles,
    // intersection)
  }

  private static void logApplicationStartup(
      final WebApplicationType webApplicationType, Environment env) {
    String protocol = "http";
    if (env.getProperty("server.ssl.key-store") != null) {
      protocol = "https";
    }
    String serverPort =
        env.getProperty("server.port") == null ? "" + 8080 : env.getProperty("server.port");
    String contextPath =
        webApplicationType == WebApplicationType.REACTIVE
            ? env.getProperty("spring.webflux.base-path")
            : env.getProperty("server.servlet.context-path");

    if (StringUtils.isBlank(contextPath)) {
      contextPath = "";
    } else {
      contextPath =
          contextPath.startsWith(CommonConstants.SLASH)
              ? contextPath
              : CommonConstants.SLASH + contextPath;
    }
    String hostAddress = "localhost";
    try {
      hostAddress = InetAddress.getLocalHost().getHostAddress();
    } catch (UnknownHostException e) {
      log.warn("The host name could not be determined, using 'localhost' as fallback");
    }
    String activeProfile = env.getProperty("spring.profiles.active");
    String profiles =
        StringUtils.isEmpty(activeProfile)
            ? Arrays.stream(env.getDefaultProfiles()).collect(Collectors.joining(", "))
            : activeProfile;

    String applicationInfo =
        String.format(
            "Application '%1$s' of type: '%2$s' is running! %n\t",
            env.getProperty("spring.application.name"), webApplicationType);
    String localUrl =
        String.format(
            "Access Local: \t\t\t\t%1$s://localhost:%2$s%3$s/swagger-ui.html%n\t",
            protocol, serverPort, contextPath);
    String externalUrl =
        String.format(
            "Access External: \t\t\t%1$s://%2$s:%3$s%4$s/swagger-ui.html%n\t",
            protocol, hostAddress, serverPort, contextPath);
    String activeProfiles = String.format("Profiles: \t\t\t\t\t%1$s%n\t", profiles);

    boolean isProblemEnabled = env.getProperty("problem.enabled", Boolean.class, true);
    boolean problemHandling =
        isProblemEnabled && ClassUtils.isPresent("com.ksoot.problem.core.Problem");

    boolean security =
        env.getProperty("hammer.security.enabled", Boolean.class, false)
            && ClassUtils.isPresent(
                "org.springframework.security.config.annotation.web.configuration.WebSecurityConfiguration");

    String securityFeature = security ? "security" : "";
    String problemFeature = problemHandling ? "problem-handling" : "";

    String enabledConfigs =
        Arrays.asList(securityFeature, problemFeature).stream()
            .filter(StringUtils::isNotBlank)
            .collect(Collectors.joining(", "));

    String enabledConfigurations = String.format("Enabled Configurations: \t%1$s", enabledConfigs);

    String logInfo =
        applicationInfo + localUrl + externalUrl + activeProfiles + enabledConfigurations;
    log.info(
        "\n-------------------------------------------------------------------------------------------\n\t"
            + logInfo
            + "\n-------------------------------------------------------------------------------------------");
  }
}
