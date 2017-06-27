package com.aaron.service.impl;

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
		sysLog.setType(SysLog.TYPE_ACCESS);
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

}
