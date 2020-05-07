package com.sg.controller.superadmin;

import com.sg.entity.bo.ShopCategory;
import com.sg.entity.dto.Result;
import com.sg.service.solo.ShopCategoryService;
import org.simpleframework.core.annotation.Controller;
import org.simpleframework.inject.annotation.Autowired;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

@Controller
public class ShopCategoryOperationOperationController {

  @Autowired private ShopCategoryService shopCategoryService;

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
