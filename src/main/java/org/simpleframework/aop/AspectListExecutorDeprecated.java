package org.simpleframework.aop;

import lombok.Getter;
import net.sf.cglib.proxy.MethodInterceptor;
import net.sf.cglib.proxy.MethodProxy;
import org.simpleframework.aop.aspect.AspectInfo;
import org.simpleframework.util.ValidationUtil;

import java.lang.reflect.Method;
import java.util.Collections;
import java.util.List;

public class AspectListExecutorDeprecated implements MethodInterceptor {

  private final Class<?> targetClass;

  @Getter private final List<AspectInfo> sortedAspectInfoList;

  public AspectListExecutorDeprecated(Class<?> targetClass, List<AspectInfo> sortedAspectInfoList) {
    this.targetClass = targetClass;
    Collections.sort(sortedAspectInfoList, (a, b) -> a.getOrderIndex() - b.getOrderIndex());
    this.sortedAspectInfoList = sortedAspectInfoList;
  }

  @Override
  public Object intercept(Object object, Method method, Object[] args, MethodProxy methodProxy)
      throws Throwable {
    Object returnValue = null;
    if (ValidationUtil.isEmpty(sortedAspectInfoList)) {
      return returnValue;
    }
    try {
      invokeBeforeAdvice(method, args);
      returnValue = methodProxy.invokeSuper(object, args);
      invokeAfterReturningAdvice(method, args, returnValue);
    } catch (Exception e) {
      invokeAfterThrowingAdvice(method, args, e);
    }
    return returnValue;
  }

  private void invokeBeforeAdvice(Method method, Object[] args) throws Throwable {
    for (AspectInfo aspectInfo : sortedAspectInfoList) {
      aspectInfo.getAspectObject().before(targetClass, method, args);
    }
  }

  private Object invokeAfterReturningAdvice(Method method, Object[] args, Object returnValue)
      throws Throwable {
    Object result = null;
    for (int i = sortedAspectInfoList.size() - 1; i >= 0; i--) {
      result =
          sortedAspectInfoList
              .get(i)
              .getAspectObject()
              .afterReturning(targetClass, method, args, returnValue);
    }
    return result;
  }

  private void invokeAfterThrowingAdvice(Method method, Object[] args, Exception e)
      throws Throwable {
    for (int i = sortedAspectInfoList.size() - 1; i >= 0; i--) {
      sortedAspectInfoList.get(i).getAspectObject().afterThrowing(targetClass, method, e);
    }
  }
}
