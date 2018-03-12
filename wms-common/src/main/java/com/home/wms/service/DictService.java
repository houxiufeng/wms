package com.home.wms.service;

import com.home.wms.dto.QueryDictParams;
import com.home.wms.entity.Dict;
import com.ktanx.common.model.PageList;

import java.util.List;

/**
 * Created by fitz on 2018/3/6.
 */
public interface DictService {
	PageList<Dict> findPageDicts(QueryDictParams params);
	void saveDict(Dict dict);
	void deleteDict(Long id);
}
