package com.home.wms.dto;

import com.home.wms.entity.Branch;
import com.home.wms.entity.BranchProduct;
import com.home.wms.entity.Product;

/**
 * Created by fitz on 2018/3/13.
 */
public class BranchProductInfo extends BranchProduct {
	private Branch branch;
	private Product product;

	public Branch getBranch() {
		return branch;
	}

	public void setBranch(Branch branch) {
		this.branch = branch;
	}

	public Product getProduct() {
		return product;
	}

	public void setProduct(Product product) {
		this.product = product;
	}
}
