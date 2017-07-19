package com.aaron.service.impl;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.aaron.aspect.SysContent;
import com.aaron.constant.Constants;
import com.aaron.constant.FileConstants;
import com.aaron.dao.SysLogDao;
import com.aaron.entity.sys.SysLog;
import com.aaron.service.SysLogService;
import com.aaron.util.excel.examples1.ExportExcel;
import com.aaron.util.excel.examples1.Student;

/**
 * 
 * 
 * @author Aaron
 * @date 2017年6月27日
 * @version 1.0
 * @package_name com.aaron.service.impl
 */
@Service
public class SysLogServiceImpl implements SysLogService {

	private Logger logger = LoggerFactory.getLogger(SysLogServiceImpl.class);

	@Resource
	SysLogDao sysLogDao;

	@Override
	public int insertLog(SysLog sysLog) {
		HttpServletRequest request = SysContent.getRequest();
		sysLog.setType(Constants.TYPE_ACCESS);
		sysLog.setRemoteAddr(request.getRemoteAddr());
		sysLog.setUserAgent(request.getHeader("user-agent"));
		sysLog.setRequestUri(request.getRequestURI());
		sysLog.setMethod(request.getMethod() + "[方法名：" + sysLog.getMethod()
				+ "]");
		sysLog.setException(null);
		sysLog.setIsDelete(Constants.DELETE_NO);
		// 判断是否是管理员
		sysLog.setCreateBy(request.getRemoteAddr() == FileConstants.LOCAL_IP ? "Aaron"
				: request.getRemoteAddr());
		sysLog.setUpdateBy(request.getRemoteAddr() == FileConstants.LOCAL_IP ? "Aaron"
				: request.getRemoteAddr());
		logger.info("###" + sysLog);
		return sysLogDao.insert(sysLog);
	}

	@Override
	public int removeLog(SysLog sysLog) {
		sysLog.setIsDelete(Constants.DELETE_YES);
		return sysLogDao.updateByPrimaryKey(sysLog);
	}

	@Override
	public int changeLog(SysLog sysLog) {
		return sysLogDao.updateByPrimaryKey(sysLog);
	}

	@Override
	public SysLog findLogById(int id) {
		return sysLogDao.selectByPrimaryKey(id);
	}

	@Override
	public List<SysLog> findAll() {
		return sysLogDao.findAll();
	}

	@Override
	public List<SysLog> list(Map<String, Object> map) {
		return sysLogDao.list(map);
	}

	@Override
	public Long getTotal(Map<String, Object> map) {
		return sysLogDao.getTotal(map);
	}

	@Override
	public void exprot(List<SysLog> sysLogs, OutputStream out) {
		ExportExcel<SysLog> ex = new ExportExcel<SysLog>();
        String[] headers = { "编号", "日志类型", "操作IP地址", "用户代理", "请求URI", "操作方式", "操作提交的数据", "异常信息"
        		, "删除标识", "创建者", "创建时间", "修改者", "修改时间"};
        //OutputStream out = null;
        try {
        	File file = new File("E://temp//日志信息_"+System.currentTimeMillis()+".xls");
        	if(file.exists()){
        		file.createNewFile();
        	}
        	//out = new FileOutputStream(file);
			ex.exportExcel(headers, sysLogs, out);
			logger.info("####日志信息导出成功");
		} catch (IOException e) {
			e.printStackTrace();
		}finally{
			try {
				out.flush();
				out.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

}
