package com.home.wms.dto;

import com.home.wms.entity.Dict;
import com.home.wms.enums.DictType;

/**
 * Created by fitz on 2018/3/10.
 */
public class DictVo extends Dict{
	private String typeName;

	public String getTypeName() {
		return DictType.toDesc(this.getType());
	}
}
