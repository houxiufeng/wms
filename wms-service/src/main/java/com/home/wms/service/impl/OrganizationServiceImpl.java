package com.home.wms.service.impl;

import com.home.wms.dto.QueryOrganizationParams;
import com.home.wms.entity.Organization;
import com.home.wms.service.OrganizationService;
import com.ktanx.common.model.PageList;
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
public class OrganizationServiceImpl implements OrganizationService {
	@Autowired
	private JdbcDao jdbcDao;
	private static Logger LOGGER = LoggerFactory.getLogger(OrganizationServiceImpl.class);
	@Override
	public List<Organization> findAll() {
		return jdbcDao.queryAll(Organization.class);
	}

	@Override
	public PageList<Organization> findPageOrganizations(QueryOrganizationParams params) {
		Organization organization = new Organization();
		if (StringUtils.isNotBlank(params.getName())) {
			organization.setName(params.getName().trim());
		}
		return jdbcDao.createSelect(Organization.class).andConditionEntity(organization).pageList(params.getiDisplayStart()/params.getiDisplayLength() + 1, params.getiDisplayLength());
	}


	@Override
	public void saveOrganization(Organization organization) {
		jdbcDao.insert(organization);
	}

	@Override
	public void updateOrganization(Organization organization) {
		jdbcDao.update(organization);
	}

	@Override
	public void deleteOrganization(Long id) {
		jdbcDao.delete(Organization.class, id);
	}

	@Override
	public Organization getById(Long id) {
		return jdbcDao.get(Organization.class, id);
	}
}
