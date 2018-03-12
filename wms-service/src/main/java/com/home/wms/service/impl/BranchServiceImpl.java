package com.home.wms.service.impl;

import com.home.wms.dto.CurrentUserInfo;
import com.home.wms.dto.QueryBranchParams;
import com.home.wms.entity.Branch;
import com.home.wms.service.BranchService;
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
 * Created by fitz on 2018/3/6.
 */
@Service
public class BranchServiceImpl implements BranchService{
	@Autowired
	private JdbcDao jdbcDao;
	private static Logger LOGGER = LoggerFactory.getLogger(BranchServiceImpl.class);

	@Override
	public PageList<Branch> findPageBranchs(QueryBranchParams params) {
		Select<Branch> s = jdbcDao.createSelect(Branch.class);
		if (params.getOrganizationId() != null) {
			s.and("organizationId",params.getOrganizationId());
		}
		if (StringUtils.isNotBlank(params.getName())) {
			s.and("name","like",new Object[]{"%"+params.getName().trim()+"%"});
		}
		return s.orderBy("id").desc().pageList(params.getiDisplayStart()/params.getiDisplayLength() + 1, params.getiDisplayLength());
	}

	@Override
	public void saveBranch(Branch branch) {
		CurrentUserInfo currentUserInfo = AppContextManager.getCurrentUserInfo();
		branch.setOrganizationId(currentUserInfo.getOrganizationId());
		branch.setCreatedBy(currentUserInfo.getId());
		jdbcDao.insert(branch);
	}

	@Override
	public void updateBranch(Branch branch) {
		jdbcDao.update(branch);
	}

	@Override
	public void deleteBranch(Long id) {
		jdbcDao.delete(Branch.class, id);
	}

	@Override
	public Branch getBranchById(Long id) {
		return jdbcDao.get(Branch.class, id);
	}
}
