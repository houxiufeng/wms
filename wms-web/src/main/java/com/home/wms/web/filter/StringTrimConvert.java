package com.home.wms.web.filter;

import org.springframework.core.convert.converter.Converter;

/**
 * Created by fitz on 2018/3/29.
 */
public class StringTrimConvert implements Converter<String, String> {
	@Override
	public String convert(String s) {
		return s == null ? null : s.trim();
	}
}
