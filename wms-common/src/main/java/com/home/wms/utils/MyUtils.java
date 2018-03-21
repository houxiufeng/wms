package com.home.wms.utils;

import cn.hutool.core.util.StrUtil;
import com.google.common.base.Function;
import com.google.common.base.Splitter;
import com.google.common.collect.Iterables;
import com.google.common.collect.Lists;
import com.google.common.primitives.Ints;
import com.google.common.primitives.Longs;

import java.util.List;

/**
 * Created by fitz on 2018/2/27.
 */
public class MyUtils {

	//把 "1,3,5" --> List<Long>(){1L,3L,5L}
	public static List<Long> strToLongList(String str) {
		return Lists.newArrayList(Iterables.transform(Splitter.on(',').split(str), new Function<String, Long>() {
			public Long apply(final String in) {
				return in == null ? null : Longs.tryParse(in);
			}
		}));
	}
	public static List<Integer> strToIntList(String str) {
		return Lists.newArrayList(Iterables.transform(Splitter.on(',').split(str), new Function<String, Integer>() {
			public Integer apply(final String in) {
				return in == null ? null : Ints.tryParse(in);
			}
		}));
	}

	public static void main(String[] args) {
		String s = "1,2,5,6";
		List<Integer> sss = strToIntList(s);
		System.out.println(sss.get(0));
	}
}
