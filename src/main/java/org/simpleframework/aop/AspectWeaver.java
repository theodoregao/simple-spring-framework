package org.simpleframework.aop;

import org.simpleframework.aop.annotation.Aspect;
import org.simpleframework.aop.annotation.Order;
import org.simpleframework.aop.aspect.AspectInfo;
import org.simpleframework.aop.aspect.DefaultAspect;
import org.simpleframework.core.BeanContainer;
import org.simpleframework.util.ValidationUtil;

import java.lang.annotation.Annotation;
import java.util.*;

public class AspectWeaver {
  private final BeanContainer beanContainer;

  public AspectWeaver() {
    beanContainer = BeanContainer.getInstance();
  }

  public void doAop() {
    Set<Class<?>> aspectSet = beanContainer.getClassesByAnnotation(Aspect.class);
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
    return aspectClass.isAnnotationPresent(Aspect.class)
        && aspectClass.isAnnotationPresent(Order.class)
        && DefaultAspect.class.isAssignableFrom(aspectClass)
        && aspectClass.getAnnotation(Aspect.class).value() != Aspect.class;
  }

  private void categorizeAspect(
      Map<Class<? extends Annotation>, List<AspectInfo>> categorizedMap, Class<?> aspectClass) {
    Order orderTag = aspectClass.getAnnotation(Order.class);
    Aspect aspectTag = aspectClass.getAnnotation(Aspect.class);
    DefaultAspect aspect = (DefaultAspect) beanContainer.getBean(aspectClass);
    AspectInfo aspectInfo = new AspectInfo(orderTag.value(), aspect);
    if (!categorizedMap.containsKey(aspectTag.value())) {
      List<AspectInfo> aspectInfoList = new ArrayList<>();
      categorizedMap.put(aspectTag.value(), aspectInfoList);
    }
    categorizedMap.get(aspectTag.value()).add(aspectInfo);
  }

  private void weaveByCategory(
      Class<? extends Annotation> category, List<AspectInfo> aspectInfoList) {
    Set<Class<?>> classes = beanContainer.getClassesByAnnotation(category);
    if (ValidationUtil.isEmpty(classes)) {
      return;
    }
    for (Class<?> targetClass : classes) {
      AspectListExecutor aspectListExecutor = new AspectListExecutor(targetClass, aspectInfoList);
      Object proxyBean = ProxyCreator.createProxy(targetClass, aspectListExecutor);
      beanContainer.addBean(targetClass, proxyBean);
    }
  }
}
