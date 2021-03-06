package org.simpleframework.aop;

import lombok.Getter;
import net.sf.cglib.proxy.MethodInterceptor;
import net.sf.cglib.proxy.MethodProxy;
import org.simpleframework.aop.aspect.AspectInfo;
import org.simpleframework.util.ValidationUtil;

import java.lang.reflect.Method;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

public class AspectListExecutor implements MethodInterceptor {

  private final Class<?> targetClass;

  @Getter private final List<AspectInfo> sortedAspectInfoList;

  public AspectListExecutor(Class<?> targetClass, List<AspectInfo> sortedAspectInfoList) {
    this.targetClass = targetClass;
    Collections.sort(sortedAspectInfoList, (a, b) -> a.getOrderIndex() - b.getOrderIndex());
    this.sortedAspectInfoList = sortedAspectInfoList;
  }

  @Override
  public Object intercept(Object object, Method method, Object[] args, MethodProxy methodProxy)
      throws Throwable {
    Object returnValue = null;
    collectAccurateMatchedAspectList(method);
    if (ValidationUtil.isEmpty(sortedAspectInfoList)) {
      return methodProxy.invokeSuper(object, args);
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

  private void collectAccurateMatchedAspectList(Method method) {
    if (ValidationUtil.isEmpty(sortedAspectInfoList)) {
      return;
    }
    Iterator<AspectInfo> it = sortedAspectInfoList.iterator();
    while (it.hasNext()) {
      AspectInfo aspectInfo = it.next();
      if (!aspectInfo.getPointcutLocator().accurateMatches(method)) {
        it.remove();
      }
    }
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
