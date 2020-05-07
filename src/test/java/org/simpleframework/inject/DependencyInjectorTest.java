package org.simpleframework.inject;

import com.sg.controller.frontend.MainPageController;
import com.sg.service.combine.impl.HeadLineShopCategoryCombineServiceImpl;
import com.sg.service.combine.impl.HeadLineShopCategoryCombineServiceImpl2;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.simpleframework.core.BeanContainer;

public class DependencyInjectorTest {

  @DisplayName("Test doIoc method")
  @Test
  public void doIocTest() {
    BeanContainer beanContainer = BeanContainer.getInstance();
    beanContainer.loadBeans("com.sg");
    Assertions.assertTrue(beanContainer.isLoaded());
    MainPageController mainPageController =
        (MainPageController) beanContainer.getBean(MainPageController.class);
    Assertions.assertTrue(mainPageController instanceof MainPageController);
    Assertions.assertNull(mainPageController.getHeadLineShopCategoryCombineService());
    new DependencyInjector().doIoc();
    Assertions.assertNotNull(mainPageController.getHeadLineShopCategoryCombineService());
    Assertions.assertTrue(
        mainPageController.getHeadLineShopCategoryCombineService()
            instanceof HeadLineShopCategoryCombineServiceImpl);
    Assertions.assertFalse(
        mainPageController.getHeadLineShopCategoryCombineService()
            instanceof HeadLineShopCategoryCombineServiceImpl2);
  }
}
