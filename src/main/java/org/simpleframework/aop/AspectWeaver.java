package org.simpleframework.aop;

import org.simpleframework.aop.annotation.Aspect;
import org.simpleframework.aop.annotation.Order;
import org.simpleframework.aop.aspect.AspectInfo;
import org.simpleframework.aop.aspect.DefaultAspect;
import org.simpleframework.core.BeanContainer;
import org.simpleframework.util.ValidationUtil;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class AspectWeaver {
  private final BeanContainer beanContainer;

  public AspectWeaver() {
    beanContainer = BeanContainer.getInstance();
  }

  public void doAop() {
    Set<Class<?>> aspectSet = beanContainer.getClassesByAnnotation(Aspect.class);
    if (ValidationUtil.isEmpty(aspectSet)) {
      return;
    }
    List<AspectInfo> aspectInfoList = packAspectInfoList(aspectSet);
    Set<Class<?>> classSet = beanContainer.getClasses();
    for (Class<?> targetClass : classSet) {
      if (targetClass.isAnnotationPresent(Aspect.class)) {
        continue;
      }
      List<AspectInfo> roughMatchedAspectInfoList =
          collectRoughMatchedAspectListForSpecificClass(aspectInfoList, targetClass);
      wrapIfNecessary(roughMatchedAspectInfoList, targetClass);
    }
  }

  private List<AspectInfo> packAspectInfoList(Set<Class<?>> aspectSet) {
    List<AspectInfo> aspectInfoList = new ArrayList<>();
    for (Class<?> aspectClass : aspectSet) {
      if (verifyAspect(aspectClass)) {
        Order orderTag = aspectClass.getAnnotation(Order.class);
        Aspect aspectTag = aspectClass.getAnnotation(Aspect.class);
        DefaultAspect defaultAspect = (DefaultAspect) beanContainer.getBean(aspectClass);
        PointcutLocator pointcutLocator = new PointcutLocator(aspectTag.pointcut());
        AspectInfo aspectInfo = new AspectInfo(orderTag.value(), defaultAspect, pointcutLocator);
        aspectInfoList.add(aspectInfo);
      } else {
        throw new RuntimeException(
            "@Aspect and @Order must be added to the Aspect class, and Aspect class need to implement DefaultAspect");
      }
    }
    return aspectInfoList;
  }

  private List<AspectInfo> collectRoughMatchedAspectListForSpecificClass(
      List<AspectInfo> aspectInfoList, Class<?> targetClass) {
    List<AspectInfo> roughMatchedAspectList = new ArrayList<>();
    for (AspectInfo aspectInfo : aspectInfoList) {
      if (aspectInfo.getPointcutLocator().roughMatches(targetClass)) {
        roughMatchedAspectList.add(aspectInfo);
      }
    }
    return roughMatchedAspectList;
  }

  private void wrapIfNecessary(List<AspectInfo> roughMatchedAspectInfoList, Class<?> targetClass) {
    if (ValidationUtil.isEmpty(roughMatchedAspectInfoList)) {
      return;
    }
    AspectListExecutor aspectListExecutor =
        new AspectListExecutor(targetClass, roughMatchedAspectInfoList);
    Object proxyBean = ProxyCreator.createProxy(targetClass, aspectListExecutor);
    beanContainer.addBean(targetClass, proxyBean);
  }

  private boolean verifyAspect(Class<?> aspectClass) {
    return aspectClass.isAnnotationPresent(Aspect.class)
        && aspectClass.isAnnotationPresent(Order.class)
        && DefaultAspect.class.isAssignableFrom(aspectClass);
  }
}
