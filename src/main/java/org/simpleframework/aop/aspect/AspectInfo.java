package org.simpleframework.aop.aspect;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class AspectInfo {
  private final int orderIndex;
  private final DefaultAspect aspectObject;
}
