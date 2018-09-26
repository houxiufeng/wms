package com.home.wms.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.util.StrUtil;
import com.google.common.collect.Lists;
import com.home.wms.dto.BranchVo;
import com.home.wms.dto.CurrentUserInfo;
import com.home.wms.dto.QueryBranchParams;
import com.home.wms.entity.Branch;
import com.home.wms.entity.Customer;
import com.home.wms.entity.User;
import com.home.wms.service.BranchService;
import com.home.wms.utils.AppContextManager;
import com.ktanx.common.model.PageList;
import com.ktanx.jdbc.persist.JdbcDao;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by fitz on 2018/3/6.
 */
@Service
public class BranchServiceImpl implements BranchService{
	@Autowired
	private JdbcDao jdbcDao;
	private static Logger LOGGER = LoggerFactory.getLogger(BranchServiceImpl.class);

	@Override
	public PageList<BranchVo> findPageBranchs(QueryBranchParams params) {

		StringBuffer sql =  new StringBuffer("select a.*, (select b.name from customer b where b.id = a.customer_id) customer_name from branch a where 1");
		List<Object> paramList = Lists.newArrayList();
		if (StrUtil.isNotBlank(params.getName())) {
			sql.append(" and a.name like ?");
			paramList.add("%" + params.getName().trim() + "%");
		}
		if (params.getOrganizationId() != null) {
			sql.append(" and a.organization_id = ?");
			paramList.add(params.getOrganizationId());
		}
		sql.append(" order by a.id desc");


		return (PageList<BranchVo>)jdbcDao.createNativeExecutor().resultClass(BranchVo.class).command(sql.toString()).forceNative(true).parameters(paramList.toArray()).pageList(params.getiDisplayStart()/params.getiDisplayLength() + 1, params.getiDisplayLength());
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
	@Override
	public List<Branch> findByConditions(QueryBranchParams params) {
		Branch branch = new Branch();
		branch.setCustomerId(params.getCustomerId());
		branch.setOrganizationId(AppContextManager.getCurrentUserInfo().getOrganizationId());
		branch.setName(params.getName());
		return jdbcDao.queryList(branch);
	}
	@Override
	public Branch getBranchByUserId(Long userId) {
        Branch branch = new Branch();
        branch.setUserId(userId);
		return jdbcDao.querySingleResult(branch);
	}

	@Override
	public BranchVo getBranchVoById(Long id) {
		Branch branch = jdbcDao.get(Branch.class, id);
		if (branch != null) {
			BranchVo branchVo = new BranchVo();
			BeanUtil.copyProperties(branch, branchVo);
			User user = jdbcDao.get(User.class, branch.getUserId());
			if (user != null) {
				branchVo.setUserName(user.getName());
			}
			Customer customer = jdbcDao.get(Customer.class, branch.getCustomerId());
			if (customer != null) {
				branchVo.setCustomerName(customer.getName());
			}
			return branchVo;
		}
        return null;
	}
}
