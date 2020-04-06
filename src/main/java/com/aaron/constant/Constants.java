package com.aaron.constant;

/**
 * 常量集合
 * 
 * @author Aaron
 * @date 2017年6月27日
 * @version 1.0
 * @package_name com.aaron.constant
 */
public class Constants {
	/**
	 * 删除标识('0:未删除 1：已删除')
	 */
	public static final String DELETE_NO = "0";

	/**
	 * 删除标识('0:未删除 1：已删除')
	 */
	public static final String DELETE_YES = "1";

	/**
	 * 日志类型(1：接入日志；2：错误日志)
	 */
	public static final String SYOLOG_TYPE_ACCESS = "1";

	/**
	 * 日志类型(1：接入日志；2：错误日志)
	 */
	public static final String SYOLOG_TYPE_EXCEPTION = "2";

	/**
	 * 导出日志信息excel 文件路径
	 */
	public static final String SYS_LOG_PATH = "E://temp//日志信息_" + System.currentTimeMillis() + ".xls";

	/**
	 * 用户登录密码md5 加密字符串
	 */
	public static final String PASSOWORD_MD5 = "hbaaron";
}
