package com.home.wms.service.impl;

import com.google.common.collect.Lists;
import com.home.wms.dto.QueryDictParams;
import com.home.wms.entity.Dict;
import com.home.wms.enums.DictType;
import com.home.wms.service.DictService;
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
 * Created by fitz on 2018/3/10.
 */
@Service
public class DictServiceImpl implements DictService{

	@Autowired
	private JdbcDao jdbcDao;
	private static Logger LOGGER = LoggerFactory.getLogger(DictServiceImpl.class);

	@Override
	public PageList<Dict> findPageDicts(QueryDictParams params) {
		Select<Dict> s = jdbcDao.createSelect(Dict.class);
		Dict entity = new Dict();
		entity.setName(params.getName());
		entity.setOrganizationId(params.getOrganizationId());
		entity.setType(params.getType());
		s.andConditionEntity(entity);
		return s.orderBy("id").desc().pageList(params.getiDisplayStart()/params.getiDisplayLength() + 1, params.getiDisplayLength());
	}

	@Override
	public void saveDict(Dict dict) {
		dict.setOrganizationId(AppContextManager.getCurrentUserInfo().getOrganizationId());
		dict.setName(dict.getName().trim());
        jdbcDao.insert(dict);
	}

	@Override
	public void deleteDict(Long id) {
        jdbcDao.delete(Dict.class, id);
	}

	@Override
	public List<Dict> findByType(Short type) {
		QueryDictParams dictParams = new QueryDictParams();
		dictParams.setOrganizationId(AppContextManager.getCurrentUserInfo().getOrganizationId());
		dictParams.setType(type);
		dictParams.setiDisplayStart(0);
		dictParams.setiDisplayLength(Integer.MAX_VALUE);
		return findPageDicts(dictParams);
	}

}
