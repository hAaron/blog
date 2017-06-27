package com.aaron.aspect;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * 获取request reponse对象。 通过ThreadLocal方式将session保存，Spring AOP中获得Session对象。
 * 
 * @author Aaron
 * @date 2017年6月27日
 * @version 1.0
 * @package_name com.aaron.aspect
 */
public class SysContent {
	private static ThreadLocal<HttpServletRequest> requestLocal = new ThreadLocal<HttpServletRequest>();
	private static ThreadLocal<HttpServletResponse> responseLocal = new ThreadLocal<HttpServletResponse>();

	public static HttpServletRequest getRequest() {
		return (HttpServletRequest) requestLocal.get();
	}

	public static void setRequest(HttpServletRequest request) {
		requestLocal.set(request);
	}

	public static HttpServletResponse getResponse() {
		return (HttpServletResponse) responseLocal.get();
	}

	public static void setResponse(HttpServletResponse response) {
		responseLocal.set(response);
	}

	public static HttpSession getSession() {
		return (HttpSession) ((HttpServletRequest) requestLocal.get())
				.getSession();
	}
}
