package com.imooc;

import com.imooc.entity.bo.HeadLine;
import lombok.extern.slf4j.Slf4j;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Slf4j
@WebServlet("/hello")
public class HelloServlet extends HttpServlet {

  //  Logger log = LoggerFactory.getLogger(HelloServlet.class);  // lombok can help define with
  // annotation @Slf4j

  @Override
  public void init() throws ServletException {
    log.debug("init()");
  }

  @Override
  public void destroy() {
    log.debug("destroy()");
  }

  @Override
  protected void service(HttpServletRequest req, HttpServletResponse resp)
      throws ServletException, IOException {
    log.debug("service()");
    doGet(req, resp);
  }

  @Override
  protected void doGet(HttpServletRequest req, HttpServletResponse resp)
      throws ServletException, IOException {
    String name = "My Simple Framework";
    log.debug("name is {}", name);
    req.setAttribute("name", name);
    req.getRequestDispatcher("/WEB-INF/jsp/hello.jsp").forward(req, resp);
    HeadLine headLine = new HeadLine();
    headLine.setLineId(1L);
    headLine.getLineId();
  }
}
