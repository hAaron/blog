package com.aaron.util.excel.examples2;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

/**
 * 
 * 
 * @author Aaron
 * @date 2017年7月13日
 * @version 1.0
 * @package_name com.aaron.util
 */
public class MainTest {

	public static void main(String[] args) throws FileNotFoundException, ExcelException {
		List<String> list = new ArrayList<String>();
		list.add("ss");
		LinkedHashMap<String, String> fieldMap = new LinkedHashMap<String, String>();
		fieldMap.put("所属线路", "line");
		fieldMap.put("站名", "station_name");
		fieldMap.put("所属路局", "route");
		fieldMap.put("所属节点", "node");
		fieldMap.put("站址", "station_address");
		fieldMap.put("邮编", "zcode");
		fieldMap.put("建设时间", "time");
		fieldMap.put("里程", "licheng");
		fieldMap.put("等级", "level");
		fieldMap.put("客货运情况", "condition");
		String[] uniqueFields={};
		String pahtname = "E:"+File.separator+"temp"+File.separator+"test.xsl";
		//InputStream is = new FileInputStream(new File(pahtname));
		//List<beanEntity> excelToList = ExcelUtil.excelToList(is , "京沪线车站", beanEntity.class, fieldMap, uniqueFields);
		//System.out.println(excelToList+"@@@@");
		OutputStream os = new FileOutputStream(pahtname);
		ExcelUtil.listToExcel(list, fieldMap, "teset", os);;
	}
}
