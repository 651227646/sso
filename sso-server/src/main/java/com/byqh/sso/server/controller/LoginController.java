package com.byqh.sso.server.controller;

import com.byqh.sso.server.util.DataBaseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpSession;
import java.util.UUID;

@Controller
public class LoginController {

    private final static Logger LOGGER = LoggerFactory.getLogger(LoginController.class);

    @RequestMapping("/checkLogin")
    public String checkLogin(String redirectUrl, HttpSession session, Model model) {
        LOGGER.info("跳转的url为{}", redirectUrl);
        //todo 判断是否有全局的会话
        String token = (String) session.getAttribute("token");
        LOGGER.info("token为{}",token);
        LOGGER.info("sessionid={}",session.getId());
        if (StringUtils.isEmpty(token)) {
            LOGGER.info("没有全局会话");
            //没有全局会话,跳转到统一认证中心的登录界面
            model.addAttribute("redirectUrl", redirectUrl);
            return "/login/Login.html";
        } else {
            LOGGER.info("有全局会话");
            //todo 有全局会话
            //取出令牌信息，重定向到redirectUrl,带上token
            return "redirect:" + redirectUrl+"?token="+token;
        }
    }

    @RequestMapping("login")
    public String login(String userName, String passWord, String redirectUrl, HttpSession session, Model model) {
        LOGGER.info("验证用户密码：用户名为{}，密码为{},跳转URl为{}", userName, passWord, redirectUrl);
        if ("1".equals(passWord)){
            redirectUrl = "http://www.crm.com:8081/main";
        }else {
            redirectUrl = "http://www.wms.com:8089/main";
        }
        if ("zhangsan".equals(userName)) {
            LOGGER.info("验证成功");
            //账号密码匹配
            //todo 1.创建令牌信息token
            String token = UUID.randomUUID().toString();
            //todo 2.创建全局会话，将令牌信息放在会话当中
            session.setAttribute("token", token);
            //todo 3.需要将令牌信息放到数据库中，用来校验令牌是否是认证中心生成的
            DataBaseUtil.T_TOKEN.add(token);
            LOGGER.info("账号密码匹配后的sessionId={}", session.getId());
            //todo 4.重定向到redirectUrl并带上令牌信息
            return "redirect:" + redirectUrl+"?token="+token;
        }
        //用户名密码验证失败
//        model.addAttribute("redirectUrl", redirectUrl);
        return "/login/Login.html";
    }

}
