package com.sg.service.combine;

import com.sg.entity.dto.MainPageInfoDto;
import com.sg.entity.dto.Result;

public interface HeadLineShopCategoryCombineService {
  Result<MainPageInfoDto> getMainPageInfo();
}
