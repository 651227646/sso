package filter;

import config.SsoClientConfigProperties;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.context.support.WebApplicationContextUtils;
import util.HttpUtil;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * @author yqi
 * @description client过滤器
 */
public class SsoClientFilter implements Filter {

    private final static Logger LOGGER = LoggerFactory.getLogger(SsoClientFilter.class);

    @Autowired
    SsoClientConfigProperties clientConfigProperties;

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        LOGGER.info("初始化单点登录客户端过滤器");
        clientConfigProperties = WebApplicationContextUtils.getWebApplicationContext(filterConfig.getServletContext())
                .getBean("ssoClientConfigProperties", SsoClientConfigProperties.class);
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        LOGGER.info("进入单点登录客户端过滤器");
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;
        HttpSession session = request.getSession();
        Boolean isLogin = (Boolean) session.getAttribute("isLogin");
        LOGGER.info("sessionid={}", session.getId());
        //TODO 判断是否有局部会话
        if (isLogin != null && isLogin) {
            LOGGER.info("有局部会话");
            //有局部会话
            filterChain.doFilter(request, response);
            return;
        }
        //TODO 判断地址栏中是否有携带token参数,有token表示请求是认证中心携带来的
        String token = request.getParameter("token");
        LOGGER.info("token={}", token);
        if (token != null && !"".equals(token)) {
            //发送http请求到sso认证中心校验token
            String url = clientConfigProperties.getSsoServerUrlPrefix() + "/verify";
            Map<String, String> paramMap = new HashMap<>();
            paramMap.put("token", token);
            //用来登出开始
            paramMap.put("clientUrl", clientConfigProperties.getClientLogoutUrl());
            paramMap.put("jsessionid", session.getId());
            //用来登出结束
            try {
                String responseStr = HttpUtil.http("POST", url, paramMap);//此发送请求方法需要修改
                LOGGER.info("验证令牌获取的结果{}", responseStr);
                if ("true".equals(responseStr.trim())) {
                    LOGGER.info("验证令牌成功");
                    //todo 如果认证中心返回的字符串是一个true表示令牌有效
                    //创建局部会话
                    session.setAttribute("isLogin", true);
                    //放行此次的请求
                    filterChain.doFilter(request, response);
                    return;
                }
            } catch (Exception e) {
                LOGGER.info("发送验证令牌信息请求失败{}" + e);
                e.printStackTrace();
            }
        }
        //没有局部会话
        //没有登录就重定向到认证中心进行认证检查用户是否已经在其他子系统中登录过 http://www.sso.com:80/checkLogin?redirectUrl=当前访问url
        clientConfigProperties.redirectToSSOURL(request, response);
    }

    @Override
    public void destroy() {
        LOGGER.info("销毁单点登录客户端过滤器");
    }
}
