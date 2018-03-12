package com.home.wms.utils;


import com.home.wms.dto.CurrentUserInfo;

/**
 * Created by fitz on 2017/6/10.
 */
public class AppContextManager {
	private static ThreadLocal<CurrentUserInfo> currentUserInfoHolder = new ThreadLocal<CurrentUserInfo>();

	public static void setCurrentUserInfo(CurrentUserInfo currentUserInfo) {
		currentUserInfoHolder.set(currentUserInfo);
	}

	public static CurrentUserInfo getCurrentUserInfo() {
		return currentUserInfoHolder.get();
	}

}
