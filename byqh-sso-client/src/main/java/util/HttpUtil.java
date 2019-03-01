package util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Map;
import java.util.Map.Entry;

/**
 * @author yqi
 * @description 模拟http请求
 */
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
}