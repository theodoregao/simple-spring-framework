package org.simpleframework.aop.annotation;

import java.lang.annotation.*;

@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface AspectDeprecated {
  Class<? extends Annotation> value();
}
