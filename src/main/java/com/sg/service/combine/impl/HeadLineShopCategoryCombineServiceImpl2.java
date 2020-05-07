package com.sg.service.combine.impl;

import com.sg.entity.bo.HeadLine;
import com.sg.entity.bo.ShopCategory;
import com.sg.entity.dto.MainPageInfoDto;
import com.sg.entity.dto.Result;
import com.sg.service.combine.HeadLineShopCategoryCombineService;
import com.sg.service.solo.HeadLineService;
import com.sg.service.solo.ShopCategoryService;
import org.simpleframework.core.annotation.Service;
import org.simpleframework.inject.annotation.Autowired;

import java.util.List;

@Service
public class HeadLineShopCategoryCombineServiceImpl2 implements HeadLineShopCategoryCombineService {

  @Autowired private HeadLineService headLineService;
  @Autowired private ShopCategoryService shopCategoryService;

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
