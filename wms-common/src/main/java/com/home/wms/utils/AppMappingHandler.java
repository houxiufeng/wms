package com.home.wms.utils;

import com.ktanx.jdbc.mapping.DefaultMappingHandler;

/**
 * Created by fitz on 2018/1/18.
 */
public class AppMappingHandler extends DefaultMappingHandler {

	@Override
	public String getPkFieldName(Class<?> aClass) {
		return "id";
	}
}
