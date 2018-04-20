package com.home.wms.service.impl;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.extra.mail.MailAccount;
import cn.hutool.extra.mail.MailUtil;
import com.home.wms.service.AsyncService;
import com.home.wms.utils.MyUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.io.File;

/**
 * Created by fitz on 2018/4/7.
 */
@Service
public class AsyncServiceImpl implements AsyncService {
	private static final Logger LOG = LoggerFactory.getLogger(AsyncServiceImpl.class);
	@Override
	@Async
	public void sendMail(String to, String subject, String content, File... files) {
		MyUtils.sendMail(to, subject, content, false, files);
	}
}
