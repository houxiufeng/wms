package com.home.wms.utils;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.crypto.SecureUtil;
import cn.hutool.extra.mail.MailAccount;
import cn.hutool.extra.mail.MailUtil;
import com.google.common.base.Function;
import com.google.common.base.Splitter;
import com.google.common.collect.Iterables;
import com.google.common.collect.Lists;
import com.google.common.primitives.Ints;
import com.google.common.primitives.Longs;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Calendar;
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

	public static String generateToken(String email, String password, Long organizationId) {
		String s = email + password;
		if (organizationId != null) {
			s += organizationId;
		}
		return SecureUtil.md5(s);
	}

	public static void sendMail(String to, String subject, String content, boolean isHtml, File... files) {
		try {
			MailAccount account = new MailAccount();
			account.setHost("mail.mtsm.com.my");
			account.setPort(587);
			account.setAuth(true);
			account.setFrom("kftang@mtsm.com.my");
			account.setUser("kftang@mtsm.com.my");
			account.setPass("Mtsm@123456");
			MailUtil.send(account, CollUtil.newArrayList(to), subject, content, isHtml, files);
		} catch (Exception e) {
			System.out.println("Email error:"+e.getMessage());
			e.printStackTrace();
		}
	}

	public static String getLastDayOfMonth(int year, int month) {
		Calendar cal = Calendar.getInstance();
		cal.set(Calendar.YEAR, year);
		cal.set(Calendar.MONTH, month-1);
		cal.set(Calendar.DAY_OF_MONTH,cal.getActualMaximum(Calendar.DATE));
		return new SimpleDateFormat( "yyyy-MM-dd ").format(cal.getTime());
	}
	public static String getFirstDayOfMonth(int year, int month) {
		Calendar cal = Calendar.getInstance();
		cal.set(Calendar.YEAR, year);
		cal.set(Calendar.MONTH, month-1);
		cal.set(Calendar.DAY_OF_MONTH,cal.getMinimum(Calendar.DATE));
		return new SimpleDateFormat( "yyyy-MM-dd ").format(cal.getTime());
	}

	public static void main(String[] args) {
		String s = "1,2,5,6";
		List<Integer> sss = strToIntList(s);
		System.out.println(sss.get(0));
//		sendMail("allen_hou@126.com", "test", "test content", false);
	}
}
