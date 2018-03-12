package com.home.wms.service.impl;

import com.google.common.collect.Lists;
import com.home.wms.dto.BranchProductVo;
import com.home.wms.dto.CurrentUserInfo;
import com.home.wms.dto.QueryBranchProductParams;
import com.home.wms.entity.Branch;
import com.home.wms.entity.BranchProduct;
import com.home.wms.entity.Permission;
import com.home.wms.service.BranchProductService;
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
 * Created by fitz on 2018/3/7.
 */
@Service
public class BranchProductServiceImpl implements BranchProductService{

	@Autowired
	private JdbcDao jdbcDao;
	private static Logger LOGGER = LoggerFactory.getLogger(BranchProductServiceImpl.class);

//	@Override
//	public PageList<BranchProduct> findPageBranchProducts(QueryBranchProductParams params) {
//		Select<BranchProduct> s = jdbcDao.createSelect(BranchProduct.class);
//		if (params.getOrganizationId() != null) {
//			s.and("organizationId",params.getOrganizationId());
//		}
//		return s.orderBy("id").desc().pageList(params.getiDisplayStart()/params.getiDisplayLength() + 1, params.getiDisplayLength());
//	}

	@Override
	public PageList<BranchProductVo> findPageBranchProducts(QueryBranchProductParams params) {
		StringBuffer sql =  new StringBuffer("select a.*, b.name product_name, b.model product_model from branch_product a");
		sql.append(" left join product b on a.product_id = b.id where 1");
		List<Object> paramList = Lists.newArrayList();
		if (params.getOrganizationId() != null) {
			sql.append(" and a.organization_id = ?");
			paramList.add(params.getOrganizationId());
		}
		sql.append(" order by a.id desc");
		return (PageList<BranchProductVo>)jdbcDao.createNativeExecutor().resultClass(BranchProductVo.class)
				.command(sql.toString()).parameters(paramList.toArray())
				.pageList(params.getiDisplayStart()/params.getiDisplayLength() + 1, params.getiDisplayLength());
	}

	@Override
	public void saveBranchProduct(BranchProduct branchProduct) {
		CurrentUserInfo currentUserInfo = AppContextManager.getCurrentUserInfo();
		branchProduct.setOrganizationId(currentUserInfo.getOrganizationId());
		branchProduct.setCreatedBy(currentUserInfo.getId());
		branchProduct.setSn(branchProduct.getSn().trim());
		jdbcDao.insert(branchProduct);
	}

	@Override
	public void updateBranchProduct(BranchProduct branchProduct) {
		jdbcDao.update(branchProduct);
	}

	@Override
	public void deleteBranchProduct(Long id) {
        jdbcDao.delete(BranchProduct.class, id);
	}

	@Override
	public BranchProduct getBranchProductById(Long id) {
		return jdbcDao.get(BranchProduct.class, id);
	}
}
