package com.aaron.util.excel.examples2;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 
 * 
 * @author Aaron
 * @date 2017Äê7ÔÂ18ÈÕ
 * @version 1.0
 * @package_name com.aaron.util.excel.examples2
 */
public class TestMain {
	public static void main(String[] args)  throws IOException{
		System.out.println(ExcelHandle.class.getResource("").getPath());
		String tempFilePath = ExcelHandle.class.getResource("/test.xlsx").getPath();//"e:/temp/test.xlsx";// ExcelHandle.class.getResource("test.xlsx").getPath();
		List<String> dataListCell = new ArrayList<String>();
		dataListCell.add("names");
		dataListCell.add("ages");
		dataListCell.add("sexs");
		dataListCell.add("deses");
		List<Map<String, Object>> dataList = new ArrayList<Map<String, Object>>();
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("names", "names");
		map.put("ages", 22);
		map.put("sexs", "ÄĞ");
		map.put("deses", "²âÊÔ");
		dataList.add(map);
		Map<String, Object> map1 = new HashMap<String, Object>();
		map1.put("names", "names1");
		map1.put("ages", 23);
		map1.put("sexs", "ÄĞ");
		map1.put("deses", "²âÊÔ1");
		dataList.add(map1);
		Map<String, Object> map2 = new HashMap<String, Object>();
		map2.put("names", "names2");
		map2.put("ages", 24);
		map2.put("sexs", "Å®");
		map2.put("deses", "²âÊÔ2");
		dataList.add(map2);
		Map<String, Object> map3 = new HashMap<String, Object>();
		map3.put("names", "names3");
		map3.put("ages", 25);
		map3.put("sexs", "ÄĞ");
		map3.put("deses", "²âÊÔ3");
		dataList.add(map3);

		ExcelHandle handle = new ExcelHandle();
		handle.writeListData(tempFilePath, dataListCell, dataList, 0);

		List<String> dataCell = new ArrayList<String>();
		dataCell.add("name");
		dataCell.add("age");
		dataCell.add("sex");
		dataCell.add("des");
		Map<String, Object> dataMap = new HashMap<String, Object>();
		dataMap.put("name", "name");
		dataMap.put("age", 11);
		dataMap.put("sex", "Å®");
		dataMap.put("des", "²âÊÔ");

		handle.writeData(tempFilePath, dataCell, dataMap, 0);
	}
}
