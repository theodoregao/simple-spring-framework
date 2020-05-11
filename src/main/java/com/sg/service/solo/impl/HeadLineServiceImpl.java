package com.sg.service.solo.impl;

import com.sg.entity.bo.HeadLine;
import com.sg.entity.dto.Result;
import com.sg.service.solo.HeadLineService;
import lombok.extern.slf4j.Slf4j;
import org.simpleframework.core.annotation.Service;

import java.util.List;

@Slf4j
@Service
public class HeadLineServiceImpl implements HeadLineService {
  @Override
  public Result<Boolean> addHeadLine(HeadLine headLine) {
    log.info("addHeadLine() invoked");
    return null;
  }

  @Override
  public Result<Boolean> removeHeadLine(int headLineId) {
    return null;
  }

  @Override
  public Result<Boolean> modifyHeadLine(HeadLine headLine) {
    return null;
  }

  @Override
  public Result<HeadLine> queryHeadLineById(int headLineId) {
    return null;
  }

  @Override
  public Result<List<HeadLine>> queryHeadLine(
      HeadLine headLineCondition, int pageIndex, int pageSize) {
    return null;
  }
}
