package org.simpleframework.core;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.simpleframework.aop.annotation.Aspect;
import org.simpleframework.core.annotation.Component;
import org.simpleframework.core.annotation.Controller;
import org.simpleframework.core.annotation.Repository;
import org.simpleframework.core.annotation.Service;
import org.simpleframework.util.ClassUtil;
import org.simpleframework.util.ValidationUtil;

import java.lang.annotation.Annotation;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

@Slf4j
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class BeanContainer {

  private static final List<Class<? extends Annotation>> BEAN_ANNOTATIONS =
      Arrays.asList(
          Component.class, Controller.class, Service.class, Repository.class, Aspect.class);
  private final Map<Class<?>, Object> beanMap = new ConcurrentHashMap<>();

  private boolean loaded = false;

  public static BeanContainer getInstance() {
    return ContainerHolder.HOLDER.instance;
  }

  public boolean isLoaded() {
    return loaded;
  }

  public int size() {
    return beanMap.size();
  }

  public synchronized void loadBeans(String packageName) {
    if (isLoaded()) {
      log.warn("BeanContainer already loaded.");
      return;
    }
    final Set<Class<?>> classes = ClassUtil.extractPackageClass(packageName);
    if (ValidationUtil.isEmpty(classes)) {
      log.warn("No class get extracted from package {}", packageName);
      return;
    }
    for (Class<?> clazz : classes) {
      for (Class<? extends Annotation> annotation : BEAN_ANNOTATIONS) {
        if (clazz.isAnnotationPresent(annotation)) {
          beanMap.put(clazz, ClassUtil.newInstance(clazz));
        }
      }
    }
    loaded = true;
  }

  public Object addBean(Class<?> clazz, Object bean) {
    return beanMap.put(clazz, bean);
  }

  public Object removeBean(Class<?> clazz) {
    return beanMap.remove(clazz);
  }

  public Object getBean(Class<?> clazz) {
    return beanMap.get(clazz);
  }

  public Set<Class<?>> getClasses() {
    return beanMap.keySet();
  }

  public Set<Object> getBeans() {
    return new HashSet<>(beanMap.values());
  }

  public Set<Class<?>> getClassesByAnnotation(Class<? extends Annotation> annotation) {
    Set<Class<?>> classes = new HashSet<>();
    Set<Class<?>> allClasses = getClasses();
    if (ValidationUtil.isEmpty(allClasses)) {
      log.warn("Nothing in beanMap.");
      return classes;
    }
    for (Class<?> clazz : allClasses) {
      if (clazz.isAnnotationPresent(annotation)) {
        classes.add(clazz);
      }
    }
    return classes;
  }

  public Set<Class<?>> getClassesBySuper(Class<?> interfaceOrClass) {
    Set<Class<?>> classes = new HashSet<>();
    Set<Class<?>> allClasses = getClasses();
    if (ValidationUtil.isEmpty(allClasses)) {
      log.warn("Nothing in beanMap.");
      return classes;
    }
    for (Class<?> clazz : allClasses) {
      if (interfaceOrClass.isAssignableFrom(clazz) && !clazz.equals(interfaceOrClass)) {
        classes.add(clazz);
      }
    }
    return classes;
  }

  private enum ContainerHolder {
    HOLDER;
    private final BeanContainer instance;

    ContainerHolder() {
      instance = new BeanContainer();
    }
  }
}
