package com.aaron.controller.common;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
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

import com.aaron.constant.Constants;
import com.aaron.entity.sys.SysLog;
import com.aaron.service.SysLogService;
import com.aaron.util.DateUtil;
import com.aaron.util.FileUtils;
import com.aaron.util.ResponseUtil;
import com.aaron.util.StringUtil;
import com.aaron.util.excel.ExcelUtils;

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
	/**
	 * 日志文件存放路径
	 */
	public static final String SYS_TEMP_FILE_PATH = "/template/sysLogInf.xlsx";

	@Resource
	SysLogService sysLogService;

	/**
	 * 日志导出(默认导出全部日志文件)
	 * 
	 * @param response
	 * @throws Exception
	 */
	@RequestMapping("/syslog/export")
	public String exportSysLog(HttpServletResponse response) throws Exception {

		// 方法1：浏览器下载文件
		List<SysLog> sysLogs = sysLogService.findAll();
		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
		// step1:定义excel列表头
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
			// step2:转换枚举常量
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
				//step3:构造数据集合 List<Map<String, Object>> Map<key,value> key为列头，value为对应的数值
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
		// step4:导出日志数据
		ExcelUtils.exportXlsx(response, "sysLogInf", headNameMap, list);

		// 方法2：自定义本地导出excel文件存放地址
		// sysLogService.exprot(sysLogs);
		return null;
	}

	/**
	 * 获取项目路径下的模板文件
	 * 
	 * @param request
	 * @param response
	 */
	@RequestMapping("/getSysLogTemplate")
	public void getSysLogTemplateFile(HttpServletRequest request,
			HttpServletResponse response) {
		logger.info("request.getSession().getServletContext().getRealPath()###"
				+ request.getSession().getServletContext().getRealPath(""));
		String formatFilePath = request.getSession().getServletContext()
				.getRealPath(SYS_TEMP_FILE_PATH);
		try {
			FileInputStream fis = new FileInputStream(formatFilePath);
			FileUtils
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

	@RequestMapping("/getExcelPage")
	public String getExcelPage(@RequestParam("parent") String title, Model model) {
		model.addAttribute("pageClass", title);
		return "foreground/excel/excelPage";
	}

	/**
	 * 日志导入(根据选择的模板定义数据，解析文件，将excel文件数据转换成实体类集合)
	 * 
	 * @param ids
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/syslog/import")
	public String importSysLog(
			@RequestParam(value = "fileName") String fileName,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		List<List<String>> list = ExcelUtils.readXlsx(
				FileUtils.uploadToStream(request, fileName), 1);
		List<SysLog> sysLogs = new ArrayList<SysLog>();
		long start = System.currentTimeMillis();
		for (List<String> result : list) {
			SysLog sysLog = new SysLog();
			for (int i = 0; i < result.size();) {
				sysLog.setType("接入日志".equals(result.get(1)) ? Constants.SYOLOG_TYPE_ACCESS
						: Constants.SYOLOG_TYPE_EXCEPTION);
				sysLog.setRemoteAddr(result.get(2));
				sysLog.setUserAgent(result.get(3));
				sysLog.setRequestUri(result.get(4));
				sysLog.setMethod(result.get(5));
				sysLog.setParams(result.get(6));
				sysLog.setException(result.get(7));
				break;
			}
			sysLogs.add(sysLog);
		}
		sysLogService.insertLogs(sysLogs);
		long end = System.currentTimeMillis();

		logger.info("日志信息导入成功，fileName{}" + fileName);
		logger.info("导入耗时：" + (end - start));
		JSONObject result = new JSONObject();
		result.put("success", "导入成功");
		ResponseUtil.write(response, result);
		return null;
	}

}
