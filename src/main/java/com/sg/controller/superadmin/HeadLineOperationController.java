package com.sg.controller.superadmin;

import com.sg.entity.bo.HeadLine;
import com.sg.entity.dto.Result;
import com.sg.service.solo.HeadLineService;
import org.simpleframework.core.annotation.Controller;
import org.simpleframework.inject.annotation.Autowired;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

@Controller
public class HeadLineOperationController {

  @Autowired private HeadLineService headLineService;

  public Result<Boolean> addHeadLine(HttpServletRequest req, HttpServletResponse resp) {
    // TODO: implement extract the parameters from req
    return headLineService.addHeadLine(new HeadLine());
  }

  public Result<Boolean> removeHeadLine(HttpServletRequest req, HttpServletResponse resp) {
    // TODO: implement extract the parameters from req
    return headLineService.removeHeadLine(1);
  }

  public Result<Boolean> modifyHeadLine(HttpServletRequest req, HttpServletResponse resp) {
    // TODO: implement extract the parameters from req
    return headLineService.modifyHeadLine(new HeadLine());
  }

  public Result<HeadLine> queryHeadLineById(HttpServletRequest req, HttpServletResponse resp) {
    // TODO: implement extract the parameters from req
    return headLineService.queryHeadLineById(1);
  }

  public Result<List<HeadLine>> queryHeadLine(HttpServletRequest req, HttpServletResponse resp) {
    // TODO: implement extract the parameters from req
    return headLineService.queryHeadLine(null, 1, 100);
  }
}
