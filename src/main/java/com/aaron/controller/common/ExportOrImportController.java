package com.aaron.controller.common;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.aaron.entity.sys.SysLog;
import com.aaron.service.SysLogService;
import com.aaron.util.FileOperationTool;
import com.aaron.util.ResponseUtil;

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
	 * 日志导出
	 * 
	 * @param ids
	 * @param response
	 * @throws Exception
	 */
	@RequestMapping("/syslog/export")
	public String exportSysLog(@RequestParam(value = "ids") String ids,
			HttpServletResponse response) throws Exception {
		String[] idsStr = ids.split(",");
		SysLog sysLog = new SysLog();
		for (int i = 0; i < idsStr.length; i++) {
			// sysLog = sysLogService.findLogById(Integer.valueOf(idsStr[i]));
			// sysLogService.removeLog(sysLog);
		}
		JSONObject result = new JSONObject();
		result.put("success", true);
		ResponseUtil.write(response, result);
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
