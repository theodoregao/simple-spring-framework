package com.sg.aspect;

import lombok.extern.slf4j.Slf4j;
import org.simpleframework.aop.annotation.Aspect;
import org.simpleframework.aop.annotation.Order;
import org.simpleframework.aop.aspect.DefaultAspect;

import java.lang.reflect.Method;

@Slf4j
// @AspectDeprecated(value = Controller.class)
@Aspect(pointcut = "within(com.sg.controller.superadmin.*)")
@Order(10)
public class ControllerInfoRecordAspect extends DefaultAspect {

  @Override
  public void before(Class<?> targetClass, Method method, Object[] args) throws Throwable {
    log.info(
        "Start class {} -> method {} with params [{}] ",
        targetClass.getName(),
        method.getName(),
        args);
  }

  @Override
  public Object afterReturning(
      Class<?> targetClass, Method method, Object[] args, Object returnValue) throws Throwable {
    log.info(
        "End class {} -> method {} with params [{}], return value {}.",
        targetClass.getName(),
        method.getName(),
        args,
        returnValue);
    return returnValue;
  }

  @Override
  public void afterThrowing(Class<?> targetClass, Method method, Throwable throwable)
      throws Throwable {
    log.info(
        "Exception class {} -> method {} with exception {}",
        targetClass.getName(),
        method.getName(),
        throwable);
  }
}
