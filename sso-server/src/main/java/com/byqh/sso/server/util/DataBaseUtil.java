package com.byqh.sso.server.util;

import com.byqh.sso.server.entity.ClientInfoVo;

import java.util.*;

/**
 * @author yqi
 * @description 模拟数据库据
 */
public class DataBaseUtil {

    public static Set<String> T_TOKEN = new HashSet<>();//存放token信息

    public static Map<String, List<ClientInfoVo>> T_CLIENT_INFO = new HashMap<>();//存放注册的客户端信息
}
