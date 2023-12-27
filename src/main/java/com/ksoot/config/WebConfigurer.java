package com.ksoot.config;

import jakarta.servlet.ServletContext;
import jakarta.servlet.ServletException;
import java.util.List;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.boot.WebApplicationType;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.boot.web.servlet.ServletContextInitializer;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.lang.Nullable;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * Configuration of web application with Servlet 3.0 APIs.
 *
 * @author Rajveer Singh
 */
@Slf4j
@Configuration
@AutoConfigureAfter(value = {GeneralAutoConfiguration.class})
public class WebConfigurer implements ServletContextInitializer, WebMvcConfigurer {

  private final Environment env;

  private final ActuatorEndpointProperties actuatorEndpointProperties;

  private final List<HandlerMethodArgumentResolver> customArgumentResolvers;

  private final List<HandlerInterceptor> customInterceptors;

  public WebConfigurer(
      final Environment env,
      @Nullable final ActuatorEndpointProperties actuatorEndpointProperties,
      @Nullable final List<HandlerMethodArgumentResolver> customArgumentResolvers,
      @Nullable final List<HandlerInterceptor> customInterceptors) {
    this.env = env;
    this.actuatorEndpointProperties = actuatorEndpointProperties;
    this.customArgumentResolvers = customArgumentResolvers;
    this.customInterceptors = customInterceptors;
  }

  @Override
  public void onStartup(final ServletContext servletContext) throws ServletException {
    if (this.env.getActiveProfiles().length != 0) {
      log.info(
          "Web application configuration, using profiles: {}",
          (Object[]) this.env.getActiveProfiles());
    }

    // App initialization and logging startup info
    AppInitializer.initialize(this.env, WebApplicationType.SERVLET);

    log.info("Web application fully configured");
  }

  @Override
  public void addArgumentResolvers(final List<HandlerMethodArgumentResolver> argumentResolvers) {
    if (!CollectionUtils.isEmpty(this.customArgumentResolvers)) {
      this.customArgumentResolvers.forEach(argumentResolvers::add);
    }
    // Add any custom method argument resolvers
  }

  @Override
  public void addInterceptors(final InterceptorRegistry registry) {
    if (CollectionUtils.isNotEmpty(this.customInterceptors)) {
      for (HandlerInterceptor interceptor : this.customInterceptors) {
        registry
            .addInterceptor(interceptor)
            .addPathPatterns("/**")
            .excludePathPatterns(ActuatorUtils.getPaths(this.actuatorEndpointProperties));
      }
    }
  }

  // @Override
  // public void configureContentNegotiation(ContentNegotiationConfigurer configurer) {
  // configurer.defaultContentType(MediaType.APPLICATION_JSON);
  // }
}
