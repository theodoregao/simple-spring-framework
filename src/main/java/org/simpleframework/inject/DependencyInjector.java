package org.simpleframework.inject;

import lombok.extern.slf4j.Slf4j;
import org.simpleframework.core.BeanContainer;
import org.simpleframework.inject.annotation.Autowired;
import org.simpleframework.util.ValidationUtil;

import java.lang.reflect.Field;
import java.util.Set;

@Slf4j
public class DependencyInjector {

  private final BeanContainer beanContainer;

  public DependencyInjector() {
    beanContainer = BeanContainer.getInstance();
  }

  public void doIoc() {
    for (Class<?> clazz : beanContainer.getClasses()) {
      for (Field field : clazz.getDeclaredFields()) {
        if (field.isAnnotationPresent(Autowired.class)) {
          Autowired autowired = field.getAnnotation(Autowired.class);
          String autowiredValue = autowired.value();
          Class<?> fieldClass = field.getType();
          try {
            field.setAccessible(true);
            Object targetClassObject = beanContainer.getBean(clazz);
            Object fieldInstance = getFieldInstance(fieldClass, autowiredValue);
            field.set(targetClassObject, fieldInstance);
          } catch (IllegalAccessException e) {
            log.warn("Set field {} error", fieldClass);
          }
        }
      }
    }
  }

  private Object getFieldInstance(Class<?> fieldClass, String autowiredValue) {
    Object bean = beanContainer.getBean(fieldClass);
    if (bean != null) {
      return bean;
    }
    final Set<Class<?>> implementedClasses = beanContainer.getClassesBySuper(fieldClass);
    if (implementedClasses.size() == 1) {
      return beanContainer.getBean(implementedClasses.iterator().next());
    }
    if (implementedClasses.size() > 1 && ValidationUtil.isEmpty(autowiredValue)) {
      throw new RuntimeException("Multiple implementation found for " + fieldClass);
    } else
      for (Class<?> clazz : implementedClasses) {
        if (clazz.getSimpleName().equals(autowiredValue)) {
          return beanContainer.getBean(clazz);
        }
      }
    return null;
  }
}
