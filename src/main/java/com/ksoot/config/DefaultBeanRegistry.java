package com.ksoot.config;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;

public class DefaultBeanRegistry implements BeanRegistry {

  private static ApplicationContext applicationContext;

  public static ApplicationContext getApplicationContext() {
    return applicationContext;
  }

  @Override
  @Autowired
  public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
    DefaultBeanRegistry.applicationContext = applicationContext;
  }

  public static <T> T getBean(Class<T> requiredType) {
    return applicationContext.getBean(requiredType);
  }

  public static <T> T getBean(String name, Class<T> requiredType) {
    return applicationContext.getBean(name, requiredType);
  }
}
