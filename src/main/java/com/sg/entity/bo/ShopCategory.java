package com.sg.entity.bo;

import java.util.Date;

public class ShopCategory {
  private Long shopCategoryId;
  private String shopCategoryName;
  private String shopCategoryDesc;
  private String shopCategoryImg;
  private Integer priority;
  private Date createTime;
  private Date lastEditTime;
  private ShopCategory parent;
}
