package com.byqh.sso.server.entity;


/**
 * @author yqi
 * @description 客户端信息
 */
public class ClientInfoVo {

    private String clientUrl;

    private String jsessionid;

    public ClientInfoVo() {
    }

    public ClientInfoVo(String clientUrl, String jsessionid) {
        this.clientUrl = clientUrl;
        this.jsessionid = jsessionid;
    }

    public String getClientUrl() {
        return clientUrl;
    }

    public void setClientUrl(String clientUrl) {
        this.clientUrl = clientUrl;
    }

    public String getJsessionid() {
        return jsessionid;
    }

    public void setJsessionid(String jsessionid) {
        this.jsessionid = jsessionid;
    }
}
