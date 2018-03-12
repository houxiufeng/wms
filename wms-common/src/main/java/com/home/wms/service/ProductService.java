package com.home.wms.service;

import com.home.wms.dto.ProductVo;
import com.home.wms.dto.QueryBranchParams;
import com.home.wms.dto.QueryProductParams;
import com.home.wms.entity.Branch;
import com.home.wms.entity.Product;
import com.ktanx.common.model.PageList;

import java.util.List;

/**
 * Created by fitz on 2018/3/6.
 */
public interface ProductService {
	PageList<Product> findPageProducts(QueryProductParams params);
	void save(Product product);
	void update(Product product);
	void delete(Long id);
	Product getById(Long id);
	ProductVo getVoById(Long id);
	List<Product> findByConditions(Product product);
}
