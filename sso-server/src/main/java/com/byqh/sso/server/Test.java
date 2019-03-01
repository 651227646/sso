package com.byqh.sso.server;

import org.springframework.util.StreamUtils;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.Charset;

public class Test {

    public static void main(String[] args) {
        try {
//            URL url = new URL("https://way.jd.com/jisuapi/query4");
            URL url = new URL("http://op.juhe.cn/yi18/store/location");
            //开启链接
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            //设置请求方式
            connection.setRequestMethod("POST");
            //设置输出方式
            connection.setDoOutput(true);
            //设置参数
            StringBuilder params = new StringBuilder();
            params.append("shouji=").append("13456755448")
                        .append("&appkey=").append("bcb9ae698e64a30364618caa74f973be");
            //写出参数
            connection.getOutputStream().write(params.toString().getBytes("UTF-8"));
            //发起请求
            connection.connect();
            //接收对方响应的信息
            String resp = StreamUtils.copyToString(connection.getInputStream(), Charset.forName("UTF-8"));
            System.out.println(resp);

        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
