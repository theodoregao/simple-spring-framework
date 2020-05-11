package org.simpleframework.aop;

import org.simpleframework.aop.annotation.AspectDeprecated;
import org.simpleframework.aop.annotation.Order;
import org.simpleframework.aop.aspect.AspectInfo;
import org.simpleframework.aop.aspect.DefaultAspect;
import org.simpleframework.core.BeanContainer;
import org.simpleframework.util.ValidationUtil;

import java.lang.annotation.Annotation;
import java.util.*;

public class AspectWeaverDeprecated {
  private final BeanContainer beanContainer;

  public AspectWeaverDeprecated() {
    beanContainer = BeanContainer.getInstance();
  }

  public void doAop() {
    Set<Class<?>> aspectSet = beanContainer.getClassesByAnnotation(AspectDeprecated.class);
    Map<Class<? extends Annotation>, List<AspectInfo>> categorizedMap = new HashMap<>();
    if (ValidationUtil.isEmpty(aspectSet)) {
      return;
    }
    for (Class<?> aspectClass : aspectSet) {
      if (verifyAspect(aspectClass)) {
        categorizeAspect(categorizedMap, aspectClass);
      } else {
        throw new RuntimeException("Invalid aspect class " + aspectClass.getName());
      }
    }
    for (Class<? extends Annotation> category : categorizedMap.keySet()) {
      weaveByCategory(category, categorizedMap.get(category));
    }
  }

  private boolean verifyAspect(Class<?> aspectClass) {
    return aspectClass.isAnnotationPresent(AspectDeprecated.class)
        && aspectClass.isAnnotationPresent(Order.class)
        && DefaultAspect.class.isAssignableFrom(aspectClass)
        && aspectClass.getAnnotation(AspectDeprecated.class).value() != AspectDeprecated.class;
  }

  private void categorizeAspect(
      Map<Class<? extends Annotation>, List<AspectInfo>> categorizedMap, Class<?> aspectClass) {
    Order orderTag = aspectClass.getAnnotation(Order.class);
    AspectDeprecated aspectDeprecatedTag = aspectClass.getAnnotation(AspectDeprecated.class);
    DefaultAspect aspect = (DefaultAspect) beanContainer.getBean(aspectClass);
    AspectInfo aspectInfo = new AspectInfo(orderTag.value(), aspect, null);
    if (!categorizedMap.containsKey(aspectDeprecatedTag.value())) {
      List<AspectInfo> aspectInfoList = new ArrayList<>();
      categorizedMap.put(aspectDeprecatedTag.value(), aspectInfoList);
    }
    categorizedMap.get(aspectDeprecatedTag.value()).add(aspectInfo);
  }

  private void weaveByCategory(
      Class<? extends Annotation> category, List<AspectInfo> aspectInfoList) {
    Set<Class<?>> classes = beanContainer.getClassesByAnnotation(category);
    if (ValidationUtil.isEmpty(classes)) {
      return;
    }
    for (Class<?> targetClass : classes) {
      AspectListExecutorDeprecated aspectListExecutorDeprecated =
          new AspectListExecutorDeprecated(targetClass, aspectInfoList);
      Object proxyBean = ProxyCreator.createProxy(targetClass, aspectListExecutorDeprecated);
      beanContainer.addBean(targetClass, proxyBean);
    }
  }
}
