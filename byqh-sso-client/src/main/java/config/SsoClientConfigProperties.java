package config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.context.properties.ConfigurationProperties;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author yqi
 * @description sso-client 配置属性类
 */
@ConfigurationProperties(prefix = "byqh.sso")
public class SsoClientConfigProperties {

    private final static Logger LOGGER = LoggerFactory.getLogger(SsoClientConfigProperties.class);

    private String ssoServerUrlPrefix;//统一认证中心地址

    private String clientHostUrl;//当前客户端地址

    private String clientLogoutUrl;//当前客户端登出地址

    private String urlPatterns;//过滤路径

    public String getSsoServerUrlPrefix() {
        return ssoServerUrlPrefix;
    }

    public void setSsoServerUrlPrefix(String ssoServerUrlPrefix) {
        this.ssoServerUrlPrefix = ssoServerUrlPrefix;
    }

    public String getClientHostUrl() {
        return clientHostUrl;
    }

    public void setClientHostUrl(String clientHostUrl) {
        this.clientHostUrl = clientHostUrl;
    }

    public String getClientLogoutUrl() {
        return clientLogoutUrl;
    }

    public void setClientLogoutUrl(String clientLogoutUrl) {
        this.clientLogoutUrl = clientLogoutUrl;
    }

    public String getUrlPatterns() {
        return urlPatterns;
    }

    public void setUrlPatterns(String urlPatterns) {
        this.urlPatterns = urlPatterns;
    }

    /**
     * @param request
     * @param response
     * @throws IOException
     * @description 跳转到sso检查是否登录
     */
    public void redirectToSSOURL(HttpServletRequest request, HttpServletResponse response) throws IOException {
        LOGGER.info("SERVER_URL_PREFIX={},CLIENT_HOST_URL={}", ssoServerUrlPrefix, clientHostUrl);
        String redirectUrl = getRedirectUrl(request);
        StringBuffer url = new StringBuffer();
        url.append(ssoServerUrlPrefix)
                .append("/checkLogin?redirectUrl=")
                .append(redirectUrl);
        response.sendRedirect(url.toString().trim());//不加trim后面会有空格
    }

    /**
     * @param request
     * @return
     * @description 获取请求url
     */
    private String getRedirectUrl(HttpServletRequest request) {
        return clientHostUrl + request.getServletPath();
    }
}
