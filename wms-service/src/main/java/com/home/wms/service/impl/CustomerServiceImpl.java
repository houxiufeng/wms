package com.home.wms.service.impl;

import com.google.common.collect.Lists;
import com.home.wms.dto.CurrentUserInfo;
import com.home.wms.dto.CustomerVo;
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

import java.util.List;

/**
 * Created by fitz on 2018/2/26.
 */
@Service
public class CustomerServiceImpl implements CustomerService {
	@Autowired
	private JdbcDao jdbcDao;
	private static Logger LOGGER = LoggerFactory.getLogger(CustomerServiceImpl.class);

	@Override
	public PageList<CustomerVo> findPageCustomers(QueryCustomerParams params) {
		List<Object> paramList = Lists.newArrayList();
		StringBuffer sql =  new StringBuffer("select c.*,");
		sql.append("(select d.name from dict d where c.type = d.id) type_name,");
		sql.append("(select d.name from dict d where c.credit_status = d.id) credit_status_name ");
		sql.append("from customer c where 1 ");
		if (params.getOrganizationId() != null) {
			sql.append("and c.organization_id = ? ");
			paramList.add(params.getOrganizationId());
		}
		if (StringUtils.isNotBlank(params.getName())) {
			sql.append("and c.name like ? ");
			paramList.add("%"+params.getName().trim()+"%");
		}
		sql.append(" order by c.id desc");
		return (PageList<CustomerVo>)jdbcDao.createNativeExecutor().resultClass(CustomerVo.class)
				.command(sql.toString()).parameters(paramList.toArray())
				.pageList(params.getiDisplayStart()/params.getiDisplayLength() + 1, params.getiDisplayLength());
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
	@Override
	public Customer getCustomerByUserId(Long userId) {
	    Customer customer = new Customer();
	    customer.setUserId(userId);
		return jdbcDao.querySingleResult(customer);
	}

}
