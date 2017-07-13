package com.aaron.util;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
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
		LinkedHashMap fieldMap = new LinkedHashMap<>();
		fieldMap.put("ss", "ss");
		String sheetName = "test";
		OutputStream out ;
		File file = new File( "aE:"+File.separator+"temp"+File.separator+"test.txt");
		out = new FileOutputStream(file,true); //是否追加
		ExcelUtil.listToExcel(list, fieldMap, sheetName, out );
	}
}
