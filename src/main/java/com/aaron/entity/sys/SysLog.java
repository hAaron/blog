package com.aaron.entity.sys;

import java.util.Date;
import java.util.Map;

/**
 * 日志表
 * 
 * @author Aaron
 * @date 2017年6月26日
 * @version 1.0
 * @package_name com.aaron.entity.sys
 */
public class SysLog {

	private static final long serialVersionUID = 1L;
	private int id;
	private String type; // 日志类型（1：接入日志；2：错误日志）
	private String remoteAddr; // 操作用户的IP地址
	private String requestUri; // 操作的URI
	private String method; // 操作的方式
	private String params; // 操作提交的数据
	private String userAgent; // 操作用户代理信息
	private String exception; // 异常信息

	private Date beginDate; // 开始日期
	private Date endDate; // 结束日期

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getRemoteAddr() {
		return remoteAddr;
	}

	public void setRemoteAddr(String remoteAddr) {
		this.remoteAddr = remoteAddr;
	}

	public String getRequestUri() {
		return requestUri;
	}

	public void setRequestUri(String requestUri) {
		this.requestUri = requestUri;
	}

	public String getMethod() {
		return method;
	}

	public void setMethod(String method) {
		this.method = method;
	}

	public String getParams() {
		return params;
	}

	public void setParams(String params) {
		this.params = params;
	}

	public String getUserAgent() {
		return userAgent;
	}

	public void setUserAgent(String userAgent) {
		this.userAgent = userAgent;
	}

	public String getException() {
		return exception;
	}

	public void setException(String exception) {
		this.exception = exception;
	}

	public Date getBeginDate() {
		return beginDate;
	}

	public void setBeginDate(Date beginDate) {
		this.beginDate = beginDate;
	}

	public Date getEndDate() {
		return endDate;
	}

	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	// 日志类型（1：接入日志；2：错误日志）
	public static final String TYPE_ACCESS = "1";
	public static final String TYPE_EXCEPTION = "2";

	/**
	 * 设置请求参数
	 * 
	 * @param paramMap
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public void setParams(Map paramMap) {
		if (paramMap == null) {
			return;
		}
		StringBuilder params = new StringBuilder();
		for (Map.Entry<String, String[]> param : ((Map<String, String[]>) paramMap)
				.entrySet()) {
			params.append(("".equals(params.toString()) ? "" : "&")
					+ param.getKey() + "=");
			String paramValue = (param.getValue() != null
					&& param.getValue().length > 0 ? param.getValue()[0] : "");
			// params.append(StringUtil.abbr(StringUtil.endsWithIgnoreCase(
			// param.getKey(), "password") ? "" : paramValue, 100));
			// TODO
			params.append(paramValue);
		}
		this.params = params.toString();
	}

	@Override
	public String toString() {
		return "日志参数SysLog [type=" + type + ", remoteAddr=" + remoteAddr
				+ ", requestUri=" + requestUri + ", method=" + method + "]";
	}
}
