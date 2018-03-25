package com.home.wms.service;

/**
 * Created by fitz on 2018/2/26.
 */
import com.home.wms.dto.CustomerVo;
import com.home.wms.dto.QueryBranchParams;
import com.home.wms.dto.QueryCustomerParams;
import com.home.wms.entity.Customer;
import com.home.wms.entity.Branch;
import com.ktanx.common.model.PageList;

public interface CustomerService {
	PageList<CustomerVo> findPageCustomers(QueryCustomerParams params);
	void saveCustomer(Customer customer);
	void updateCustomer(Customer customer);
	void deleteCustomer(Long id);
	Customer getCustomerById(Long id);
	Customer getCustomerByUserId(Long userId);
}
