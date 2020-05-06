package com.imooc.service.combine.impl;

import com.imooc.entity.bo.HeadLine;
import com.imooc.entity.bo.ShopCategory;
import com.imooc.entity.dto.MainPageInfoDto;
import com.imooc.entity.dto.Result;
import com.imooc.service.combine.HeadLineShopCategoryCombineService;
import com.imooc.service.solo.HeadLineService;
import com.imooc.service.solo.ShopCategoryService;

import java.util.List;

public class HeadLineShopCategoryCombineServiceImpl implements HeadLineShopCategoryCombineService {

  private HeadLineService headLineService;
  private ShopCategoryService shopCategoryService;

  @Override
  public Result<MainPageInfoDto> getMainPageInfo() {
    HeadLine headLineCondition = new HeadLine();
    headLineCondition.setEnableStatus(1);
    Result<List<HeadLine>> headLineResult = headLineService.queryHeadLine(headLineCondition, 1, 4);

    ShopCategory shopCategoryCondition = new ShopCategory();
    Result<List<ShopCategory>> shopCategoryResult =
        shopCategoryService.queryShopCategory(shopCategoryCondition, 1, 100);

    Result<MainPageInfoDto> result = mergeMainPageInfoResult(headLineResult, shopCategoryResult);
    return result;
  }

  private Result<MainPageInfoDto> mergeMainPageInfoResult(
      Result<List<HeadLine>> headLineResult, Result<List<ShopCategory>> shopCategoryResult) {
    return null;
  }
}
