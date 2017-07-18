package com.aaron.util.excel.examples;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;

public class Examples {

    public static void main(String[] agrs ) throws IOException, Exception{
    	
    	Excel2EntityConfig config = new Excel2EntityConfig();
		String[] columns = {"name", "password", "birthday"};
		config.setColumns(columns);
//		//设置日期的格式，和Excel里的日期格式一至
		config.setFormater(new SimpleDateFormat(
						"yyyy.MM.dd"));
		//设置从第行开始读，忽略前1行
		config.setCurrPosittion(2);
		//设置从第二列开始读取，忽略第一列的数序号列
		config.setColStartPosittion(0);
		ExcelReader<TestEntity> excel = new ExcelReader<TestEntity>();
		excel.setExcel2EntityConfig(config);
		
		File file = new File("e:\\temp\\testEntity.xls"); //把testEntity.xls文件复制到d:
    	InputStream input = new FileInputStream(file);  
    	//如果现现EXCEl编码问题引起的读取问题，请将InputStream换成 ByteArrayInputStream 可解决问题
    	//ByteArrayInputStream input = new ByteArrayInputStream(byte[])	
    	excel.InitExcelReader(input); 
		try {
			TestEntity entity = new TestEntity();
			excel.setEntity(entity);
			entity = excel.readLine();
			long start = System.currentTimeMillis();
			while (entity != null) {				
				System.out.print(entity.getName()+" ");
				System.out.print(entity.getPassword()+" ");
				System.out.println(new SimpleDateFormat("yyyy.MM.dd").format(entity.getBirthday()));
				///保存实体代码
				//entity = new TestEntity();
				//excel.setEntity(entity);
				entity = excel.readLine();
			}
			long end = System.currentTimeMillis();
			System.out.println("耗时："+(end-start));
		} catch (IOException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		} finally{
			input.close();
		}   	
    	
    }
}
