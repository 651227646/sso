package com.byqh.sso.server.controller;

import com.byqh.sso.server.util.DataBaseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpSession;

@Controller
public class LogoutController {

    private final static Logger LOGGER = LoggerFactory.getLogger(LogoutController.class);

    @RequestMapping("logout")
    public String Logout(HttpSession session) {
        //销毁全局会话
        session.invalidate();
        //当session失效就需要清除子系统的登录状态，所以需要监听session的失效
        LOGGER.info("注销的sessionid为{}",session.getId());
        LOGGER.info("跳转到登出界面");
        LOGGER.info("T_TOKEN={}", DataBaseUtil.T_TOKEN);
        return "/logout/Logout.html";
    }

}
