package org.simpleframework.aop.aspect;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.simpleframework.aop.PointcutLocator;

@AllArgsConstructor
@Getter
public class AspectInfo {
  private final int orderIndex;
  private final DefaultAspect aspectObject;
  private final PointcutLocator pointcutLocator;
}
