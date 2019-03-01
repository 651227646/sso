package com.byqh.sso.server.util;

import org.springframework.util.StreamUtils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.Map;
import java.util.Map.Entry;

public class HttpUtil {
    /**
     * @param url
     * @param params
     * @return
     * @throws IOException
     * @Description:HTTP请求post
     */
    public static String http(String url, Map<String, String> params) throws IOException {
        return http("POST", url, params);
    }

    /**
     * @param method
     * @param url
     * @param params
     * @return
     * @throws IOException
     * @Title:函数
     * @Description:可以指定类型的http请求
     */
    public static String http(String method, String url, Map<String, String> params) throws IOException {
        try {
            URL u = null;
            HttpURLConnection con = null;
            // 构建请求参数
            StringBuffer sb = new StringBuffer();
            String requestStr = null;
            if (params != null) {
                for (Entry<String, String> e : params.entrySet()) {
                    sb.append(e.getKey());
                    sb.append("=");
                    sb.append(e.getValue());
                    sb.append("&");
                }
                requestStr = sb.substring(0, sb.length() - 1);
            } else {
                requestStr = sb.toString();
            }
            // 尝试发送请求
            u = new URL(url);
            con = (HttpURLConnection) u.openConnection();
            con.setRequestMethod(method);
            con.setDoOutput(true);
            con.setDoInput(true);
            con.setUseCaches(false);
            con.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
            OutputStreamWriter osw = new OutputStreamWriter(con.getOutputStream(), "UTF-8");
            osw.write(requestStr);
            osw.flush();
            osw.close();
            if (con != null) {
                con.disconnect();
            }
            // 读取返回内容
            StringBuffer buffer = new StringBuffer();
            BufferedReader br = new BufferedReader(new InputStreamReader(con.getInputStream(), "UTF-8"));
            String temp;
            while ((temp = br.readLine()) != null) {
                buffer.append(temp);
                buffer.append("\n");
            }
            return buffer.toString();
        } catch (IOException e) {
            e.printStackTrace();
            throw new IOException();
        }
    }

    public static String httpAddSessionId(String method, String url, String jsessionid) throws IOException {
        try {
            URL u = null;
            HttpURLConnection con = null;
            // 构建请求参数
            StringBuffer sb = new StringBuffer();
            String requestStr = null;
            // 尝试发送请求
            u = new URL(url);
            con = (HttpURLConnection) u.openConnection();
            con.setRequestMethod(method);
            con.setDoOutput(true);
            con.setDoInput(true);
            con.setUseCaches(false);
            con.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
            con.addRequestProperty("Cookie","JSESSIONID="+jsessionid);
            OutputStreamWriter osw = new OutputStreamWriter(con.getOutputStream(), "UTF-8");
            osw.write(requestStr);
            osw.flush();
            osw.close();
            if (con != null) {
                con.disconnect();
            }
            // 读取返回内容
            StringBuffer buffer = new StringBuffer();
            BufferedReader br = new BufferedReader(new InputStreamReader(con.getInputStream(), "UTF-8"));
            String temp;
            while ((temp = br.readLine()) != null) {
                buffer.append(temp);
                buffer.append("\n");
            }
            return buffer.toString();
        } catch (IOException e) {
            e.printStackTrace();
            throw new IOException();
        }
    }

    /**
     * 模拟浏览器的请求
     * @param httpURL 发送请求的地址
     * @param params  请求参数
     * @return
     * @throws Exception
     */
    public static String sendHttpRequest(String httpURL,Map<String,String> params) throws Exception{
        //建立URL连接对象
        URL url = new URL(httpURL);
        //创建连接
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        //设置请求的方式(需要是大写的)
        conn.setRequestMethod("POST");
        //设置需要输出
        conn.setDoOutput(true);
        //判断是否有参数.
        if(params!=null&&params.size()>0){
            StringBuilder sb = new StringBuilder();
            for(Entry<String,String> entry:params.entrySet()){
                sb.append("&").append(entry.getKey()).append("=").append(entry.getValue());
            }
            //sb.substring(1)去除最前面的&
            conn.getOutputStream().write(sb.substring(1).toString().getBytes("utf-8"));
        }
        //发送请求到服务器
        conn.connect();
        //获取远程响应的内容.
        String responseContent = StreamUtils.copyToString(conn.getInputStream(), Charset.forName("utf-8"));
        conn.disconnect();
        return responseContent;
    }

    /**
     * 模拟浏览器的请求
     * @param httpURL 发送请求的地址
     * @param jesssionId 会话Id
     * @return
     * @throws Exception
     */
    public static void sendHttpRequest(String httpURL,String jesssionId) throws Exception{
        //建立URL连接对象
        URL url = new URL(httpURL);
        //创建连接
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        //设置请求的方式(需要是大写的)
        conn.setRequestMethod("POST");
        //设置需要输出
        conn.setDoOutput(true);
        conn.addRequestProperty("Cookie","JSESSIONID="+jesssionId);
        //发送请求到服务器
        conn.connect();
        conn.getInputStream();
        conn.disconnect();
    }
}