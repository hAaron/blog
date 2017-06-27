package com.aaron.intercepter;

import java.text.SimpleDateFormat;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.time.DateUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.NamedThreadLocal;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import com.aaron.entity.sys.SysLog;
import com.aaron.util.DateUtil;
/**
 * 日志管理  spring拦截器
 * 
 * @author Aaron
 * @date 2017年6月26日
 * @version 1.0
 * @package_name com.aaron
 */
public class LogIntercepter implements HandlerInterceptor {

	private Logger logger = LoggerFactory.getLogger(LogIntercepter.class);

	private static final ThreadLocal<Long> startTimeThreadLocal = new NamedThreadLocal<Long>(
			"ThreadLocal StartTime");

	@Override
	public boolean preHandle(HttpServletRequest arg0, HttpServletResponse arg1,
			Object arg2) throws Exception {
		long beginTime = System.currentTimeMillis();// 1、开始时间
		startTimeThreadLocal.set(beginTime);
		logger.info("开始计时: {}  URI: {}",
				new SimpleDateFormat("hh:mm:ss.SSS").format(beginTime),
				arg0.getRequestURI());
		return true;
	}

	@Override
	public void postHandle(HttpServletRequest arg0, HttpServletResponse arg1,
			Object arg2, ModelAndView arg3) throws Exception {
		if (arg3 != null) {
			logger.info("ViewName: " + arg3.getViewName());
		}
	}

	@Override
	public void afterCompletion(HttpServletRequest request,
			HttpServletResponse response, Object handler, Exception ex)
			throws Exception {
		String requestURL = request.getRequestURL().toString();
		SysLog log = new SysLog();
		log.setType(ex == null ? SysLog.TYPE_ACCESS : SysLog.TYPE_EXCEPTION);
		log.setRemoteAddr(request.getRemoteAddr());
		log.setUserAgent(request.getHeader("user-agent"));
		log.setRequestUri(request.getRequestURI());
		//log.setParams(request.getParameterMap());
		log.setMethod(request.getMethod());
		
		
		// 保存日志
		//SysLog.saveLog(log);

		// 打印JVM信息。
		if (logger.isDebugEnabled()) {
			long beginTime = startTimeThreadLocal.get();// 得到线程绑定的局部变量（开始时间）
			long endTime = System.currentTimeMillis(); // 2、结束时间
			logger.debug(
					"计时结束：{}  耗时：{}  URI: {}  最大内存: {}m  已分配内存: {}m  已分配内存中的剩余空间: {}m  最大可用内存: {}m",
					new SimpleDateFormat("hh:mm:ss.SSS").format(endTime),
					DateUtil.formatDateTime(endTime - beginTime), request
							.getRequestURI(),
					Runtime.getRuntime().maxMemory() / 1024 / 1024, Runtime
							.getRuntime().totalMemory() / 1024 / 1024, Runtime
							.getRuntime().freeMemory() / 1024 / 1024, (Runtime
							.getRuntime().maxMemory()
							- Runtime.getRuntime().totalMemory() + Runtime
							.getRuntime().freeMemory()) / 1024 / 1024);
			// 删除线程变量中的数据，防止内存泄漏
			startTimeThreadLocal.remove();
		}

	}

}
