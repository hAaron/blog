package com.aaron.service;

import java.io.OutputStream;
import java.util.List;
import java.util.Map;

import com.aaron.entity.sys.SysLog;

/**
 * 日志接口的方法定义需要注意，避免和aop定义的execution 表达式冲突，否则会陷入死循环
 * 
 * @author Aaron
 * @date 2017年6月27日
 * @version 1.0
 * @package_name com.aaron.service
 */
public interface SysLogService {

	int insertLog(SysLog sysLog);
	
	int removeLog(SysLog sysLog);
	
	int changeLog(SysLog sysLog);
	
	SysLog findLogById(int id);
	
	List<SysLog> findAll();

	List<SysLog> list(Map<String, Object> map);

	Long getTotal(Map<String, Object> map);
	
	public void exprot(List<SysLog> sysLogs);

	int insertLogs(List<SysLog> sysLogs);
}
