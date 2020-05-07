package com.sg.controller.frontend;

import com.sg.entity.dto.MainPageInfoDto;
import com.sg.entity.dto.Result;
import com.sg.service.combine.HeadLineShopCategoryCombineService;
import lombok.Getter;
import org.simpleframework.core.annotation.Controller;
import org.simpleframework.inject.annotation.Autowired;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Controller
@Getter
public class MainPageController {

  @Autowired(value = "HeadLineShopCategoryCombineServiceImpl")
  private HeadLineShopCategoryCombineService headLineShopCategoryCombineService;

  public Result<MainPageInfoDto> getMainPageInfo(HttpServletRequest req, HttpServletResponse resp) {
    return headLineShopCategoryCombineService.getMainPageInfo();
  }
}
