package com.aaron.controller.common;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;

import org.apache.commons.collections.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.aaron.constant.Constants;
import com.aaron.entity.sys.SysLog;
import com.aaron.service.SysLogService;
import com.aaron.util.DateUtil;
import com.aaron.util.FileOperationTool;
import com.aaron.util.ResponseUtil;
import com.aaron.util.StringUtil;
import com.aaron.util.excel.ExcelUtils;
import com.aaron.util.excel.examples2.ExcelUtil;

/**
 * 文件导入导出
 * 
 * @author Aaron
 * @date 2017年7月18日
 * @version 1.0
 * @package_name com.aaron.controller.common
 */
@Controller
@RequestMapping("/ei")
public class ExportOrImportController {
	public static Logger logger = LoggerFactory
			.getLogger(ExportOrImportController.class);
	@Resource
	SysLogService sysLogService;

	@RequestMapping("/getExcelPage")
	public String getExcelPage(@RequestParam("parent") String title, Model model) {
		model.addAttribute("pageClass", title);
		return "foreground/excel/excelPage";
	}

	/**
	 * 日志导入
	 * 
	 * @param ids
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/syslog/import")
	public String importSysLog(
			@RequestParam(value = "filename") MultipartFile file,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		String fileName = request.getParameter("fileName");
		if (file == null)
			return null;
		long size = file.getSize();
		if (fileName == null || ("").equals(fileName) && size == 0)
			return null;
		logger.info("TODO..........");
		JSONObject result = new JSONObject();
		result.put("success", true);
		ResponseUtil.write(response, result);
		return null;
	}

	/**
	 * 日志导出(默认导出全部日志文件)
	 * 
	 * @param ids
	 * @param response
	 * @throws Exception
	 */
	@RequestMapping("/syslog/export")
	public String exportSysLog(HttpServletResponse response) throws Exception {

		List<SysLog> sysLogs = sysLogService.findAll();
		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
		// 定义excel列表头
		Map<String, String> headNameMap = new LinkedHashMap<String, String>();
		headNameMap.put("id", "编号");
		headNameMap.put("type", "日志类型");
		headNameMap.put("remoteAddr", "操作IP地址");
		headNameMap.put("userAgent", "用户代理");
		headNameMap.put("requestUri", "请求URI");
		headNameMap.put("method", "操作方式");
		headNameMap.put("params", "操作提交的数据");
		headNameMap.put("exception", "异常信息");
		headNameMap.put("isDelete", "删除标识");
		headNameMap.put("createBy", "创建者");
		headNameMap.put("createDate", "创建时间");
		headNameMap.put("updateBy", "修改者");
		headNameMap.put("updateDate", "修改时间");

		if (CollectionUtils.isNotEmpty(sysLogs)) {
			// 转换枚举常量
			String type = "接入日志";// 日志类型（1：接入日志；2：错误日志）
			String isDelete = "未删除";// 删除标识('0:未删除 1：已删除')
			String createDate = "";
			String updateDate = "";
			for (SysLog sysLog : sysLogs) {
				if (StringUtil.isNotEmpty(sysLog.getType())
						&& Constants.SYOLOG_TYPE_EXCEPTION.equals(sysLog
								.getType())) {
					type = "错误日志";
				}
				if (StringUtil.isNotEmpty(sysLog.getIsDelete())
						&& Constants.DELETE_YES.equals(sysLog.getIsDelete())) {
					isDelete = "已删除";
				}
				if (sysLog.getCreateDate() != null) {
					createDate = DateUtil.dfDateTime.format(sysLog
							.getCreateDate());
				}
				if (sysLog.getUpdateDate() != null) {
					updateDate = DateUtil.dfDateTime.format(sysLog
							.getUpdateDate());
				}
				Map<String, Object> map = new LinkedHashMap<String, Object>();
				map.put("id", sysLog.getId());
				map.put("type", type);
				map.put("remoteAddr", sysLog.getRemoteAddr());
				map.put("userAgent", sysLog.getUserAgent());
				map.put("requestUri", sysLog.getRequestUri());
				map.put("method", sysLog.getMethod());
				map.put("params", sysLog.getParams());
				map.put("exception", sysLog.getException());
				map.put("isDelete", isDelete);
				map.put("createBy", sysLog.getCreateBy());
				map.put("createDate", createDate);
				map.put("updateBy", sysLog.getUpdateBy());
				map.put("updateDate", updateDate);
				list.add(map);
			}
		}
		ExcelUtils.exportXlsx(response, "日志数据", headNameMap, list);
		return null;
	}

	@RequestMapping("/getSysLogTemplate")
	public void getSysLogTemplateFile(HttpServletRequest request,
			HttpServletResponse response) {
		String formatFilePath = request.getSession().getServletContext()
				.getRealPath("/template/sysLogInf.xlsx");
		try {
			FileInputStream fis = new FileInputStream(formatFilePath);
			FileOperationTool
					.download(
							request,
							response,
							"application/vnd.openxmlformats-officedocument.spreadsheetml.sheet",
							"sysLogInf.xlsx", fis);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
