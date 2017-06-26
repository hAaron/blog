package org.aaron.test;

import com.aaron.entity.Blog;
import com.aaron.util.PropertiesLoader;

/**
 * 
 * 
 * @author Aaron
 * @date 2017Äê6ÔÂ12ÈÕ
 * @version 1.0
 * @package_name org.aaron.test
 */
public class TestConfigProperties {
	
	public static void main(String[] args) {
		PropertiesLoader loader = new PropertiesLoader("/config.properties");
		System.out.println(loader.getProperty("fileuploadPath"));
		System.out.println(loader.getProperty("httpPath"));
	}

}
