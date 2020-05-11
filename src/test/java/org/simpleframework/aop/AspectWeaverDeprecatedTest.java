package org.simpleframework.aop;

import com.sg.controller.superadmin.HeadLineOperationController;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.simpleframework.core.BeanContainer;
import org.simpleframework.inject.DependencyInjector;

public class AspectWeaverDeprecatedTest {

  @DisplayName("test doAop method")
  @Test
  public void doAopTest() {
    BeanContainer beanContainer = BeanContainer.getInstance();
    beanContainer.loadBeans("com.sg");
    new AspectWeaverDeprecated().doAop();
    new DependencyInjector().doIoc();

    HeadLineOperationController headLineOperationController =
        (HeadLineOperationController) beanContainer.getBean(HeadLineOperationController.class);
    headLineOperationController.addHeadLine(null, null);
  }
}
