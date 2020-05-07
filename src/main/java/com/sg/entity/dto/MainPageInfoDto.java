package com.sg.entity.dto;

import com.sg.entity.bo.HeadLine;
import com.sg.entity.bo.ShopCategory;
import lombok.Data;

import java.util.List;

@Data
public class MainPageInfoDto {
  private List<HeadLine> headLineList;
  private List<ShopCategory> shopCategoryList;
}
