package org.simpleframework.util;

import java.util.Collection;
import java.util.Map;

public class ValidationUtil {

  public static boolean isEmpty(Collection<?> collection) {
    return collection == null || collection.isEmpty();
  }

  public static boolean isEmpty(String str) {
    return str == null || "".equals(str);
  }

  public static boolean isEmpty(Map<?, ?> map) {
    return map == null || map.isEmpty();
  }

  public static boolean isEmpty(Object[] objects) {
    return objects == null || objects.length == 0;
  }
}
