package org.simpleframework.util;

import lombok.extern.slf4j.Slf4j;

import java.io.File;
import java.lang.reflect.Constructor;
import java.net.URL;
import java.util.HashSet;
import java.util.Set;

@Slf4j
public class ClassUtil {

  private static final String FILE_PROTOCOL = "file";

  public static Set<Class<?>> extractPackageClass(String packageName) {

    final ClassLoader classLoader = getClassLoader();
    final URL url = classLoader.getResource(packageName.replace(".", "/"));

    if (url == null) {
      log.warn("Unable to retrieve anything from package: {}", packageName);
      return null;
    }

    if (!url.getProtocol().equalsIgnoreCase(FILE_PROTOCOL)) {
      return null;
    }

    final Set<Class<?>> classes = new HashSet<>();
    final File packageDirectory = new File(url.getPath());
    extractClassFile(classes, packageDirectory, packageName);

    return classes;
  }

  public static <T> T newInstance(Class<?> clazz) {
    try {
      Constructor constructor = clazz.getDeclaredConstructor();
      constructor.setAccessible(true);
      return (T) constructor.newInstance();
    } catch (Exception e) {
      log.error("Crate new instance of {} error", clazz);
      throw new RuntimeException();
    }
  }

  private static ClassLoader getClassLoader() {
    return Thread.currentThread().getContextClassLoader();
  }

  private static void extractClassFile(Set<Class<?>> classes, File fileSource, String packageName) {
    if (fileSource == null || !fileSource.isDirectory()) {
      return;
    }
    for (File file :
        fileSource.listFiles(it -> it.isDirectory() || it.getAbsolutePath().endsWith(".class"))) {
      if (file.isDirectory()) {
        extractClassFile(classes, file, packageName);
      } else {
        String classFullName = file.getAbsolutePath().replace(File.separator, ".");
        String className = classFullName.substring(classFullName.indexOf(packageName));
        className = className.substring(0, className.lastIndexOf("."));
        try {
          classes.add(Class.forName(className));
        } catch (ClassNotFoundException e) {
          log.warn("Extract class {} error", className);
        }
      }
    }
  }
}
