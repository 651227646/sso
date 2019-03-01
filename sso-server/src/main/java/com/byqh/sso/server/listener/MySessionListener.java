package com.byqh.sso.server.listener;

import com.byqh.sso.server.controller.LogoutController;
import com.byqh.sso.server.entity.ClientInfoVo;
import com.byqh.sso.server.util.DataBaseUtil;
import com.byqh.sso.server.util.HttpUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;
import javax.servlet.http.HttpSession;
import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author yqi
 * @description 监听session失效
 */
@WebListener
public class MySessionListener implements HttpSessionListener {

    private final static Logger LOGGER = LoggerFactory.getLogger(MySessionListener.class);

    @Override
    public void sessionCreated(HttpSessionEvent se) {

    }

    /**
     * @param se
     * @description session 失效
     */
    @Override
    public void sessionDestroyed(HttpSessionEvent se) {
        LOGGER.info("注销全局会话");
        HttpSession session = se.getSession();
        String token = (String) session.getAttribute("token");
        //删除token记录的信息
        DataBaseUtil.T_TOKEN.remove(token);
        //获取出注册的子系统，依次调用子系统的登出方法
        List<ClientInfoVo> clientInfoVos = DataBaseUtil.T_CLIENT_INFO.remove(token);
        LOGGER.info("clientInfoVo.size={}", clientInfoVos.size());
        try {
            for (ClientInfoVo clientInfoVo : clientInfoVos) {
                LOGGER.info("clientInfoVo.getClientUrl()={}", clientInfoVo.getClientUrl());
                HttpUtil.sendHttpRequest(clientInfoVo.getClientUrl(), clientInfoVo.getJsessionid());
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
