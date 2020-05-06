package com.imooc.controller.superadmin;

import com.imooc.entity.bo.ShopCategory;
import com.imooc.entity.dto.Result;
import com.imooc.service.solo.ShopCategoryService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

public class ShopCategoryOperationOperationController {

  private ShopCategoryService shopCategoryService;

  public Result<Boolean> addShopCategory(HttpServletRequest req, HttpServletResponse resp) {
    // TODO: implement extract the parameters from req
    return shopCategoryService.addShopCategory(new ShopCategory());
  }

  public Result<Boolean> removeShopCategory(HttpServletRequest req, HttpServletResponse resp) {
    // TODO: implement extract the parameters from req
    return shopCategoryService.removeShopCategory(1);
  }

  public Result<Boolean> modifyShopCategory(HttpServletRequest req, HttpServletResponse resp) {
    // TODO: implement extract the parameters from req
    return shopCategoryService.modifyShopCategory(new ShopCategory());
  }

  public Result<ShopCategory> queryShopCategoryById(
      HttpServletRequest req, HttpServletResponse resp) {
    // TODO: implement extract the parameters from req
    return shopCategoryService.queryShopCategoryById(1);
  }

  public Result<List<ShopCategory>> queryShopCategory(
      HttpServletRequest req, HttpServletResponse resp) {
    // TODO: implement extract the parameters from req
    return shopCategoryService.queryShopCategory(null, 1, 100);
  }
}
