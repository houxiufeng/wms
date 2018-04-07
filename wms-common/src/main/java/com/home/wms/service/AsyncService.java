package com.home.wms.service;

import java.io.File;

/**
 * Created by fitz on 2018/4/7.
 */
public interface AsyncService {
	void sendMail(String to, String subject, String content, File... files);
}
