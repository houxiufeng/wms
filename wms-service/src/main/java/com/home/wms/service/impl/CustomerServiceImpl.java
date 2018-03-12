package com.home.wms.service.impl;

import com.home.wms.dto.CurrentUserInfo;
import com.home.wms.dto.QueryBranchParams;
import com.home.wms.dto.QueryCustomerParams;
import com.home.wms.entity.Customer;
import com.home.wms.entity.Branch;
import com.home.wms.service.CustomerService;
import com.home.wms.utils.AppContextManager;
import com.ktanx.common.model.PageList;
import com.ktanx.jdbc.command.entity.Select;
import com.ktanx.jdbc.persist.JdbcDao;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by fitz on 2018/2/26.
 */
@Service
public class CustomerServiceImpl implements CustomerService {
	@Autowired
	private JdbcDao jdbcDao;
	private static Logger LOGGER = LoggerFactory.getLogger(CustomerServiceImpl.class);

	@Override
	public PageList<Customer> findPageCustomers(QueryCustomerParams params) {
		Select<Customer> s = jdbcDao.createSelect(Customer.class);
		if (params.getOrganizationId() != null) {
			s.and("organizationId",params.getOrganizationId());
		}
		if (StringUtils.isNotBlank(params.getName())) {
			s.and("name","like",new Object[]{"%"+params.getName().trim()+"%"});
		}
		return s.orderBy("id").desc().pageList(params.getiDisplayStart()/params.getiDisplayLength() + 1, params.getiDisplayLength());
	}


	@Override
	public void saveCustomer(Customer customer) {
		CurrentUserInfo currentUserInfo = AppContextManager.getCurrentUserInfo();
		customer.setCreatedBy(currentUserInfo.getId());
		customer.setOrganizationId(currentUserInfo.getOrganizationId());
		jdbcDao.insert(customer);
	}

	@Override
	public void updateCustomer(Customer Customer) {
		jdbcDao.update(Customer);
	}

	@Override
	public void deleteCustomer(Long id) {
		jdbcDao.delete(Customer.class, id);
	}

	@Override
	public Customer getCustomerById(Long id) {
		return jdbcDao.get(Customer.class, id);
	}

}
