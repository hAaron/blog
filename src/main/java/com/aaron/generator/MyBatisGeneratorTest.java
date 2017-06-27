package com.aaron.generator;


import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.mybatis.generator.api.MyBatisGenerator;
import org.mybatis.generator.config.Configuration;
import org.mybatis.generator.config.xml.ConfigurationParser;
import org.mybatis.generator.internal.DefaultShellCallback;  
  
/**
 * mybatis反向工具，可以使用此生成所需代码。 
 * @author Aaron
 * @date 2017年6月27日
 * @version 1.0
 * @package_name com.aaron.generator
 */

public class MyBatisGeneratorTest  {  
      
    private  String confFilePath = "generatorConfig.xml";  
  
    public  void generator() throws Exception {  
          
        List<String> warnings = new ArrayList<String>();  
          
        confFilePath = this.getClass().getResource(confFilePath).getFile();  
          
        File configFile = new File(confFilePath);  
          
        Configuration config = new ConfigurationParser(warnings).parseConfiguration(configFile);  
          
        DefaultShellCallback callback = new DefaultShellCallback(true);  
          
        MyBatisGenerator myBatisGenerator = new MyBatisGenerator(config, callback, warnings);  
          
        myBatisGenerator.generate(null);  
    }  
    
    public static void main(String[] args) throws Exception {
    	MyBatisGeneratorTest my = new MyBatisGeneratorTest();
    	my.generator();
    	System.out.println("----------结束----------");
	}
}
