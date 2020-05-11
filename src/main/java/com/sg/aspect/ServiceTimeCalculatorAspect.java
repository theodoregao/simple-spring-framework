package com.sg.aspect;

import lombok.extern.slf4j.Slf4j;
import org.simpleframework.aop.annotation.Aspect;
import org.simpleframework.aop.annotation.Order;
import org.simpleframework.aop.aspect.DefaultAspect;

import java.lang.reflect.Method;

@Slf4j
@Aspect(pointcut = "within(org.simpleframework.core.annotation.Component)")
@Order(0)
public class ServiceTimeCalculatorAspect extends DefaultAspect {

  private long timestampCache;

  @Override
  public void before(Class<?> targetClass, Method method, Object[] args) throws Throwable {
    log.info(
        "Start with class {} -> method {} with params [{}] ",
        targetClass.getName(),
        method.getName(),
        args);
    timestampCache = System.currentTimeMillis();
  }

  @Override
  public Object afterReturning(
      Class<?> targetClass, Method method, Object[] args, Object returnValue) throws Throwable {
    long interval = System.currentTimeMillis() - timestampCache;
    log.info(
        "End class {} -> method {} with params [{}], return value {}. Total time consume {} ms",
        targetClass.getName(),
        method.getName(),
        args,
        returnValue,
        interval);
    return returnValue;
  }
}
