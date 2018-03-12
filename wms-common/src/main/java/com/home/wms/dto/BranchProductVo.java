package com.home.wms.dto;

import com.home.wms.entity.BranchProduct;

/**
 * Created by fitz on 2018/3/11.
 */
public class BranchProductVo extends BranchProduct {
	private String productName;
	private String productModel;

	public String getProductName() {
		return productName;
	}

	public void setProductName(String productName) {
		this.productName = productName;
	}

	public String getProductModel() {
		return productModel;
	}

	public void setProductModel(String productModel) {
		this.productModel = productModel;
	}
}
