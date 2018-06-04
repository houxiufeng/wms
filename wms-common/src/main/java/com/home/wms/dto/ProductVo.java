package com.home.wms.dto;

import com.google.common.collect.Lists;
import com.home.wms.entity.Product;
import org.apache.commons.lang3.StringUtils;

import java.util.List;

/**
 * Created by fitz on 2018/3/10.
 */
public class ProductVo extends Product{
	private List<String> imgUrlList;
	private List<String> fileUrlList;
	private String sparePartListStr;
	private String checkListStr;

	public List<String> getImgUrlList() {
		if (StringUtils.isNotBlank(this.getImgUrl())) {
			imgUrlList = Lists.newArrayList(this.getImgUrl().split(","));
		}
		return imgUrlList;
	}

	public List<String> getFileUrlList() {
		if (StringUtils.isNotBlank(this.getFileUrl())) {
			fileUrlList = Lists.newArrayList(this.getFileUrl().split(","));
		}
		return fileUrlList;
	}

	public String getSparePartListStr() {
		return sparePartListStr;
	}

	public void setSparePartListStr(String sparePartListStr) {
		this.sparePartListStr = sparePartListStr;
	}

	public String getCheckListStr() {
		return checkListStr;
	}

	public void setCheckListStr(String checkListStr) {
		this.checkListStr = checkListStr;
	}
}
