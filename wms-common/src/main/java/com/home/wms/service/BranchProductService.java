package com.home.wms.service;

import com.home.wms.dto.BranchProductInfo;
import com.home.wms.dto.BranchProductVo;
import com.home.wms.dto.QueryBranchProductParams;
import com.home.wms.entity.BranchProduct;
import com.ktanx.common.model.PageList;

import java.util.List;

/**
 * Created by fitz on 2018/3/6.
 */
public interface BranchProductService {
	PageList<BranchProductVo> findPageBranchProducts(QueryBranchProductParams params);
	void saveBranchProduct(BranchProduct branchProduct);
	void updateBranchProduct(BranchProduct branchProduct);
	void deleteBranchProduct(Long id);
	BranchProduct getBranchProductById(Long id);
    BranchProductInfo getBranchProductInfoById(Long id);
}
