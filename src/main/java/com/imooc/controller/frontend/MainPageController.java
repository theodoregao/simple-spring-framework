package com.imooc.controller.frontend;

import com.imooc.entity.dto.MainPageInfoDto;
import com.imooc.entity.dto.Result;
import com.imooc.service.combine.HeadLineShopCategoryCombineService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class MainPageController {

  private HeadLineShopCategoryCombineService headLineShopCategoryCombineService;

  public Result<MainPageInfoDto> getMainPageInfo(HttpServletRequest req, HttpServletResponse resp) {
    return headLineShopCategoryCombineService.getMainPageInfo();
  }
}
