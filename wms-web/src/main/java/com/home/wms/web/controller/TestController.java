package com.home.wms.web.controller;

import com.alibaba.fastjson.JSONObject;
import com.home.wms.entity.User;
import org.apache.commons.lang3.StringUtils;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.util.CellReference;
import org.apache.poi.ss.usermodel.*;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

@Controller
@RequestMapping("/test")
public class TestController {

	@RequestMapping("/t1")
	@ResponseBody
	public User test() {
		User user = new User();
		user.setName("allen");
		return user;
	}
	
	@RequestMapping("/t12")
	@ResponseBody
	public JSONObject test2() {
		JSONObject json = new JSONObject();
		json.put("name", "clack");
		json.put("age", 22);
		return json;
	}
	
	@RequestMapping("/hello")
	@ResponseBody
	public JSONObject hello(String name) {
		JSONObject json = new JSONObject();
		return json;
	}

	@RequestMapping(value="/excel", method = RequestMethod.GET)
	public String excel() {
		return "excel";
	}

	@RequestMapping(value="/calc", method = RequestMethod.POST)
	@ResponseBody
	public JSONObject calc(Double x, Double y, HttpServletRequest request) {
		JSONObject json = new JSONObject();
		String projectPath = request.getSession().getServletContext().getRealPath("");
//        File projectAbsolutePath = new File(projectPath);
//        String webappsPath = projectAbsolutePath.getParent();
		String filePath = projectPath + File.separator + "files" + File.separator + "bobo.xls";
		System.out.println(filePath);
		json.put("data",calcExcel(x,y,filePath).toString());
		return json;
	}

	@ResponseBody
	@RequestMapping(value = "/uploadExcelFile", method = { RequestMethod.GET, RequestMethod.POST })
	public JSONObject uploadMailFile(@RequestParam(value = "file") MultipartFile[] recordFile, HttpServletRequest request) throws Exception {
		JSONObject crmResponse = new JSONObject();

		for(MultipartFile mpf : recordFile){
			// 文件名称
			String fileName = mpf.getOriginalFilename();
			if(StringUtils.isBlank(fileName)){
				continue;
			}

			// 文件路径
			String projectPath = request.getSession().getServletContext().getRealPath("");
			String filePath = projectPath + File.separator + "files" + File.separator;

			// 保存文件
			File file = new File(filePath, "bobo.xls");
			if (!file.exists()) {
				file.mkdirs();
			}
			try {
				mpf.transferTo(file);
				crmResponse.put("code",0);
			}catch (IllegalStateException | IOException e) {
				crmResponse.put("code",1);
				crmResponse.put("msg","上传失败！");
			}
		}

		return crmResponse;
	}

	public Object calcExcel(double x, double y, String filePath) {
		FileInputStream fis = null;
		Object result = null;
		try {
//            fis = new FileInputStream("/Users/fitz/test/bobo.xls");
			fis = new FileInputStream(filePath);
			Workbook wb = new HSSFWorkbook(fis); //or new XSSFWorkbook("c:/temp/test.xls")
			Sheet sheet = wb.getSheetAt(0);
			Row row1 = sheet.getRow(1);
			Cell cell0 = row1.getCell(0);
			Cell cell1 = row1.getCell(1);
			cell0.setCellValue(x);
			cell1.setCellValue(y);
			FormulaEvaluator evaluator = wb.getCreationHelper().createFormulaEvaluator();

			// suppose your formula is in C2
			CellReference cellReference = new CellReference("C2");
			Row row = sheet.getRow(cellReference.getRow());
			Cell cell = row.getCell(cellReference.getCol());

			CellValue cellValue = evaluator.evaluate(cell);
			switch (cellValue.getCellTypeEnum()) {
				case BOOLEAN:
					result = cellValue.getBooleanValue();
					break;
				case NUMERIC:
					result = cellValue.getNumberValue();
					break;
				case STRING:
					result = cellValue.getStringValue();
					break;
				case BLANK:
					break;
				case ERROR:
					break;
				case FORMULA:
					break;
			}
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (fis != null) {
				try {
					fis.close();
				} catch (IOException e) {
					System.out.println(e.getMessage());
					e.printStackTrace();
				}
			}
		}
		return result;
	}
}
