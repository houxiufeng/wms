package com.home.wms.web.controller;

import com.alibaba.fastjson.JSONObject;
import com.home.wms.utils.AppContextManager;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.IOException;

/**
 * Created by fitz on 2018/3/10.
 */
@Controller
@RequestMapping("/fileupload")
public class FileuploadController {

	private static final Logger LOG = LoggerFactory.getLogger(FileuploadController.class);

	@Value(value="${uploadRealPath}")
	private String uploadRealPath;//实际路径(根据实际情况修改tomcat server.xml配置)

	@Value(value="${uploadPath}")
	private String uploadPath;//映射路径

	@ResponseBody
	@RequestMapping(value = "/uploadProductImg", method = {RequestMethod.POST })
	public JSONObject uploadProductImg(@RequestParam(value = "productImg") MultipartFile[] productImg, HttpServletRequest request) throws Exception {
		JSONObject result = new JSONObject();

		for(MultipartFile mpf : productImg){
			// 文件名称
			String fileName = mpf.getOriginalFilename();
			if(StringUtils.isBlank(fileName)){
				continue;
			}
			// 文件路径
			String filePath = uploadRealPath + AppContextManager.getCurrentUserInfo().getOrganizationId() + File.separator + "productImgs" + File.separator;
			// 保存文件
			File file = new File(filePath, fileName);
			if (!file.exists()) {
				file.mkdirs();
			}
			try {
				mpf.transferTo(file);
				result.put("code",0);
				result.put("imgPath",uploadPath + AppContextManager.getCurrentUserInfo().getOrganizationId() + File.separator + "productImgs" + File.separator + fileName);
				result.put("imgName",fileName);
			}catch (IllegalStateException | IOException e) {
				result.put("code",1);
				result.put("msg","upload failed.");
			}
		}

		return result;
	}

	@ResponseBody
	@RequestMapping(value = "/uploadProductFile", method = {RequestMethod.POST })
	public JSONObject uploadProductFile(@RequestParam(value = "productFile") MultipartFile[] productFile, HttpServletRequest request) throws Exception {
		JSONObject result = new JSONObject();

		for(MultipartFile mpf : productFile){
			// 文件名称
			String fileName = mpf.getOriginalFilename();
			if(StringUtils.isBlank(fileName)){
				continue;
			}
			// 文件路径
			String filePath = uploadRealPath + AppContextManager.getCurrentUserInfo().getOrganizationId() + File.separator + "productFiles" + File.separator;
			// 保存文件
			File file = new File(filePath, fileName);
			if (!file.exists()) {
				file.mkdirs();
			}
			try {
				mpf.transferTo(file);
				result.put("code",0);
				result.put("filePath",uploadPath + AppContextManager.getCurrentUserInfo().getOrganizationId() + File.separator + "productFiles" + File.separator + fileName);
				result.put("fileName",fileName);
			}catch (IllegalStateException | IOException e) {
				result.put("code",1);
				result.put("msg","upload failed.");
			}
		}

		return result;
	}

	@ResponseBody
	@RequestMapping(value = "/uploadVendorAvator", method = {RequestMethod.POST })
	public JSONObject uploadVendorAvator(@RequestParam(value = "vendorAvator") MultipartFile[] vendorAvator, HttpServletRequest request) throws Exception {
		JSONObject result = new JSONObject();

		for(MultipartFile mpf : vendorAvator){
			// 文件名称
			String fileName = mpf.getOriginalFilename();
			if(StringUtils.isBlank(fileName)){
				continue;
			}
			// 文件路径
			String filePath = uploadRealPath + AppContextManager.getCurrentUserInfo().getOrganizationId() + File.separator + "vendorAvator" + File.separator;
			// 保存文件
			File file = new File(filePath, fileName);
			if (!file.exists()) {
				file.mkdirs();
			}
			try {
				mpf.transferTo(file);
				result.put("code",0);
				result.put("avatorPath",uploadPath + AppContextManager.getCurrentUserInfo().getOrganizationId() + File.separator + "vendorAvator" + File.separator + fileName);
			}catch (IllegalStateException | IOException e) {
				result.put("code",1);
				result.put("msg","upload failed.");
			}
		}

		return result;
	}
}
