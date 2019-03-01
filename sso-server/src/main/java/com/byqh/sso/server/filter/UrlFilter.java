package com.byqh.sso.server.filter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

//登陆状态验证控制过滤器
public class UrlFilter implements Filter {
	private Logger logger = LoggerFactory.getLogger(this.getClass());
	private String contextPath;

	@Override
	public void doFilter(ServletRequest sRequest, ServletResponse sResponse, FilterChain filterChain)
			throws IOException, ServletException {
		HttpServletRequest request = (HttpServletRequest) sRequest;
		HttpServletResponse response = (HttpServletResponse) sResponse;
		HttpSession session = request.getSession(true);// 若存在会话则返回该会话，否则新建一个会话。
		String path = request.getContextPath();
		String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + path
				+ "/";
		request.setAttribute("basePath", basePath);
		String url = request.getServletPath();
		if (url.equals(""))
			url += "/";
		String loginName = (String) session.getAttribute("loginName");
		/** 无需验证的 */
		String[] strs = { "/css/", "/js/", "themes", ".css", ".jpg", ".png" }; // 路径中包含这些字符串的,可以不用用检查
		// 特殊用途的路径可以直接访问
		if (strs != null && strs.length > 0) {
			for (String str : strs) {
				if (url.indexOf(str) >= 0) {
					filterChain.doFilter(request, response);
					return;
				}
			}
		}
		Enumeration<?> enu = request.getParameterNames();
		Map<String, String> parameterMap = new HashMap<String, String>();
		while (enu.hasMoreElements()) {
			String paraName = (String) enu.nextElement();
			parameterMap.put(paraName, request.getParameter(paraName));
		}
		logger.info("【url日志】 UrlFilter:【" + basePath.substring(0,basePath.lastIndexOf("/"))+url + "】  loginName=" + loginName + " parameterMap="
				+ parameterMap);
		filterChain.doFilter(request, response);
	}

	@Override
	public void destroy() {
		System.out.println(contextPath + " UrlFilter：销毁");

	}

	@Override
	public void init(FilterConfig filterConfig) throws ServletException {
		contextPath = filterConfig.getServletContext().getContextPath();
		System.out.println(contextPath + " UrlFilter：创建");
	}

}