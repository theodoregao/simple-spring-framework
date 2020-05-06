package com.imooc.service.combine;

import com.imooc.entity.dto.MainPageInfoDto;
import com.imooc.entity.dto.Result;

public interface HeadLineShopCategoryCombineService {
  Result<MainPageInfoDto> getMainPageInfo();
}
