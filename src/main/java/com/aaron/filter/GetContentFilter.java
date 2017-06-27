package com.aaron.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.aaron.aspect.SysContent;

/**
 * 通过过滤器，获取请求对象
 * 
 * @author Aaron
 * @date 2017年6月27日
 * @version 1.0
 * @package_name com.aaron.filter
 */
public class GetContentFilter implements Filter{

	@Override
	public void init(FilterConfig filterConfig) throws ServletException {
		// TODO Auto-generated method stub
	}

	@Override
	public void doFilter(ServletRequest request, ServletResponse response,
			FilterChain chain) throws IOException, ServletException {
		//将request 和reponse 放入到threadLocal中
		SysContent.setRequest((HttpServletRequest) request);
		SysContent.setResponse((HttpServletResponse) response);
		chain.doFilter(request, response);
	}

	@Override
	public void destroy() {
		// TODO Auto-generated method stub
	}

}
