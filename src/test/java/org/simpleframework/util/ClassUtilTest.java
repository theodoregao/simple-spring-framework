package org.simpleframework.util;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Set;

public class ClassUtilTest {

  @DisplayName("Test ClassUtil::extractPackageClass")
  @Test
  public void extractPackageClassTest() {
    Set<Class<?>> classes = ClassUtil.extractPackageClass("com.sg.entity");
    System.out.println(classes);
    Assertions.assertEquals(4, classes.size());
  }
}
