package com.byqh.sso.server.controller;

import com.byqh.sso.server.entity.ClientInfoVo;
import com.byqh.sso.server.util.DataBaseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.ArrayList;
import java.util.List;

@Controller
public class VerifyController {

    private final static Logger LOGGER = LoggerFactory.getLogger(VerifyController.class);

    @RequestMapping("verify")
    @ResponseBody
    public String verifyToken(String token, String clientUrl, String jsessionid) {
        LOGGER.info("开始验证token的真实性，token为{}", token);
        //todo 校验token的真实性
        if (DataBaseUtil.T_TOKEN.contains(token)) {
            //令牌有效，返回true
            //用于登出 start
            List<ClientInfoVo> clientInfoVoList = DataBaseUtil.T_CLIENT_INFO.get(token);
            if (clientInfoVoList == null) {
                clientInfoVoList = new ArrayList<>();
                DataBaseUtil.T_CLIENT_INFO.put(token, clientInfoVoList);
            }
            LOGGER.info("注册子系统token为{}clentUrl为{}", token, clientUrl);
            clientInfoVoList.add(new ClientInfoVo(clientUrl, jsessionid));
            //用于登出 end
            return "true";
        }
        return "false";
    }

}
