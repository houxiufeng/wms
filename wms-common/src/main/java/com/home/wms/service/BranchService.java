package com.home.wms.service;

import com.home.wms.dto.BranchVo;
import com.home.wms.dto.QueryBranchParams;
import com.home.wms.entity.Branch;
import com.ktanx.common.model.PageList;

import java.util.List;

/**
 * Created by fitz on 2018/3/6.
 */
public interface BranchService {
	PageList<BranchVo> findPageBranchs(QueryBranchParams params);
	void saveBranch(Branch branch);
	void updateBranch(Branch branch);
	void deleteBranch(Long id);
	Branch getBranchById(Long id);
	List<Branch> findByConditions(QueryBranchParams params);
	Branch getBranchByUserId(Long userId);
}
