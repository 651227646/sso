package com.byqh.ssoclient2.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpSession;

/**
 * @author yqi
 * @description 登出controller
 */
@Controller
public class LogoutController {

    private final static Logger LOGGER = LoggerFactory.getLogger(LogoutController.class);

    @RequestMapping("logout")
    @ResponseBody
    public String logout(HttpSession session) {
        LOGGER.info("消除局部会话sessionId={}",session.getId());
        session.invalidate();
        return null;
    }

}
