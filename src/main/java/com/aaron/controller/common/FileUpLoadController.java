package com.aaron.controller.common;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.aaron.constant.FileConstants;
import com.aaron.util.FileUtils;
import com.aaron.util.PropertiesLoader;

/**
 * 图片上传到nginx
 * 
 * @author Aaron
 * @date 2017年6月13日
 * @version 1.0
 * @package_name com.aaron.controller
 */
@Controller
@RequestMapping("/ueditor/fileupload")
public class FileUpLoadController {

	public static Logger logger = LoggerFactory.getLogger(FileUpLoadController.class); 
	
	// 文件上传路径
//	@Resource(name = "fileuploadPath")
//	private String fileuploadPath;

	// 文件读取路径
//	@Resource(name = "httpPath")
//	private String httpPath;

	/**
	 * 文件上传Action
	 * 
	 * @param req
	 * @return UEDITOR 需要的json格式数据
	 */
	@RequestMapping(value = "upload", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public Map<String, Object> upload(HttpServletRequest req) {
		
		Map<String, Object> result = new HashMap<String, Object>();
		MultipartHttpServletRequest mReq = null;
		MultipartFile file = null;
		InputStream is = null;
		String fileName = "";
		// 原始文件名 UEDITOR创建页面元素时的alt和title属性
		String originalFileName = "";
		String filePath = "";

		try {
			mReq = (MultipartHttpServletRequest) req;
			// 从config.json中取得上传文件的ID
			file = mReq.getFile("upfile");
			// 取得文件的原始文件名称
			fileName = file.getOriginalFilename();

			originalFileName = fileName;

			if (!StringUtils.isEmpty(fileName)) {
				is = file.getInputStream();
				fileName = FileUtils.reName(fileName);
				filePath = FileUtils.saveFile(fileName, is, FileConstants.fileuploadPath);
			} else {
				throw new IOException("文件名为空!");
			}
			logger.info("文件上传的地址url{}"+(FileConstants.httpPath + filePath));
			result.put("state", "SUCCESS");// UEDITOR的规则:不为SUCCESS则显示state的内容
			result.put("url", FileConstants.httpPath + filePath);
			result.put("title", originalFileName);
			result.put("original", originalFileName);
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("####文件上传失败fileName{}"+fileName+"#####异常信息####"+e);
			result.put("state", "文件上传失败!");
			result.put("url", "");
			result.put("title", "");
			result.put("original", "");
		}

		return result;
	}
}
