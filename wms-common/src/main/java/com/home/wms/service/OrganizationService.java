package com.home.wms.service;

/**
 * Created by fitz on 2018/2/26.
 */
import com.home.wms.dto.QueryOrganizationParams;
import com.home.wms.entity.Organization;
import com.ktanx.common.model.PageList;

import java.util.List;

public interface OrganizationService {
	List<Organization> findAll();
	PageList<Organization> findPageOrganizations(QueryOrganizationParams params);
	void saveOrganization(Organization organization);
	void updateOrganization(Organization organization);
	void deleteOrganization(Long id);
	Organization getById(Long id);
}
