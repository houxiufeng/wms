package com.home.wms.service;

import com.home.wms.dto.QueryVendorParams;
import com.home.wms.dto.VendorVo;
import com.home.wms.entity.Vendor;
import com.ktanx.common.model.PageList;

/**
 * Created by fitz on 2018/3/11.
 */
public interface VendorService {
	PageList<VendorVo> findPageVendors(QueryVendorParams params);
	void saveVendor(Vendor vendor);
	void updateVendor(Vendor vendor);
	void deleteVendor(Long id);
	Vendor getVendorById(Long id);
}
