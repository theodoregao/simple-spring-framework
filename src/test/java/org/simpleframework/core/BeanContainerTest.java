package org.simpleframework.core;

import com.sg.controller.DispatcherServlet;
import com.sg.controller.frontend.MainPageController;
import com.sg.service.solo.HeadLineService;
import com.sg.service.solo.impl.HeadLineServiceImpl;
import org.junit.jupiter.api.*;
import org.simpleframework.core.annotation.Controller;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class BeanContainerTest {

  private static BeanContainer beanContainer;

  @BeforeAll
  static void init() {
    beanContainer = BeanContainer.getInstance();
  }

  @DisplayName("Test loadBeans method")
  @Order(1)
  @Test
  public void loadBeansTest() {
    Assertions.assertEquals(false, beanContainer.isLoaded());
    beanContainer.loadBeans("com.sg");
    Assertions.assertEquals(true, beanContainer.isLoaded());
    Assertions.assertEquals(6, beanContainer.size());
  }

  @DisplayName("Test getBean method")
  @Order(2)
  @Test
  public void getBeanTest() {
    MainPageController controller =
        (MainPageController) beanContainer.getBean(MainPageController.class);
    Assertions.assertEquals(true, controller instanceof MainPageController);

    DispatcherServlet dispatcherServlet =
        (DispatcherServlet) beanContainer.getBean(DispatcherServlet.class);
    Assertions.assertNull(dispatcherServlet);
  }

  @DisplayName("Test getClassesByAnnotation method")
  @Order(3)
  @Test
  public void getClassesByAnnotationTest() {
    Assertions.assertEquals(true, beanContainer.isLoaded());
    Assertions.assertEquals(3, beanContainer.getClassesByAnnotation(Controller.class).size());
  }

  @DisplayName("Test getClassesBySuper method")
  @Order(4)
  @Test
  public void getClassesBySuperTest() {
    Assertions.assertEquals(true, beanContainer.isLoaded());
    Assertions.assertTrue(
        beanContainer.getClassesBySuper(HeadLineService.class).contains(HeadLineServiceImpl.class));
  }
}
