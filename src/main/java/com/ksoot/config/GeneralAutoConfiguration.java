package com.ksoot.config;

import com.ksoot.common.MessageProvider;
import com.ksoot.common.PaginatedResourceAssembler;
import org.springframework.boot.autoconfigure.AutoConfigureOrder;
import org.springframework.boot.autoconfigure.task.TaskExecutionProperties;
import org.springframework.boot.autoconfigure.task.TaskSchedulingProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.ApplicationContext;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.core.env.Environment;
import org.springframework.data.web.HateoasPageableHandlerMethodArgumentResolver;
import org.springframework.lang.Nullable;

@Configuration
@EnableConfigurationProperties(
        value = {TaskExecutionProperties.class, TaskSchedulingProperties.class})
@AutoConfigureOrder(Ordered.HIGHEST_PRECEDENCE)
public class GeneralAutoConfiguration {

    //  @Bean
    //  ApplicationEventMulticaster applicationEventMulticaster(
    //      @Qualifier("applicationTaskExecutor") final Executor taskExecutor) {
    //    SimpleApplicationEventMulticaster eventMulticaster = new
    // SimpleApplicationEventMulticaster();
    //    eventMulticaster.setTaskExecutor(taskExecutor);
    //    return eventMulticaster;
    //  }

    @Bean
    MessageProvider messageProvider(final MessageSource messageSource) {
        return new MessageProvider(messageSource);
    }

    @Bean
    SpringProfiles springProfiles(final Environment environment) {
        return new SpringProfiles(environment);
    }

    @Bean
    PaginatedResourceAssembler paginatedResourceAssembler(
            @Nullable final HateoasPageableHandlerMethodArgumentResolver resolver) {
        return new PaginatedResourceAssembler(resolver);
    }

    @Bean
    BeanRegistry defaultBeanRegistry(final ApplicationContext applicationContext) {
        BeanRegistry beanRegistry = new DefaultBeanRegistry();
        beanRegistry.setApplicationContext(applicationContext);
        return beanRegistry;
    }

    // @ConditionalOnMissingBean(value = Validator.class)
    // @Bean
    // Validator validator() {
    // return Validation.buildDefaultValidatorFactory().getValidator();
    // }
}
