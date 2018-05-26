package com.home.wms.service;

import com.home.wms.dto.QueryEngineerParams;
import com.home.wms.dto.EngineerVo;
import com.home.wms.entity.Engineer;
import com.ktanx.common.model.PageList;

/**
 * Created by fitz on 2018/3/11.
 */
public interface EngineerService {
	PageList<EngineerVo> findPageEngineer(QueryEngineerParams params);
	void saveEngineer(Engineer engineer);
	void updateEngineer(Engineer engineer);
	void deleteEngineer(Long id);
	Engineer getEngineerById(Long id);
	Engineer getEngineerByUserId(Long userId);
}
