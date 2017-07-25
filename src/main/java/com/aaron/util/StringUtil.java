package com.aaron.util;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

/**
 * 字符串工具类
 * 
 * @author Aaron
 * @date 2017年7月25日
 * @version 1.0
 * @package_name com.aaron.util
 */
public class StringUtil {

	/**
	 * 判断源字符串是否为空
	 * 
	 * @param str
	 *            源字符串
	 * @return
	 */
	public static boolean isEmpty(String str) {
		if (str == null || "".equals(str.trim())) {
			return true;
		} else {
			return false;
		}
	}

	/**
	 * 判断源字符串是否不为空
	 * 
	 * @param str
	 *            源字符串
	 * @return
	 */
	public static boolean isNotEmpty(String str) {
		if ((str != null) && !"".equals(str.trim())) {
			return true;
		} else {
			return false;
		}
	}

	/**
	 * 格式化模糊查询
	 * 
	 * @param str
	 *            源字符串
	 * @return
	 */
	public static String formatLike(String str) {
		if (isNotEmpty(str)) {
			return "%" + str + "%";
		} else {
			return null;
		}
	}

	/**
	 * 过滤掉集合里的空格
	 * 
	 * @param list
	 *            源字符串集合
	 * @return
	 */
	public static List<String> filterWhite(List<String> list) {
		List<String> resultList = new ArrayList<String>();
		for (String l : list) {
			if (isNotEmpty(l)) {
				resultList.add(l);
			}
		}
		return resultList;
	}

	/**
	 * 判断源字符是否为空
	 * 
	 * @param cs
	 *            源字符
	 * @return
	 */
	public static boolean isBlank(final CharSequence cs) {
		int strLen;
		if (cs == null || (strLen = cs.length()) == 0) {
			return true;
		}
		for (int i = 0; i < strLen; i++) {
			if (Character.isWhitespace(cs.charAt(i)) == false) {
				return false;
			}
		}
		return true;
	}

	/**
	 * 判断源字符是否不为空
	 * 
	 * @param cs
	 *            源字符
	 * @return
	 */
	public static boolean isNotBlank(final CharSequence cs) {
		return !StringUtil.isBlank(cs);
	}

}
