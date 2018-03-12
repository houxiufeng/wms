package com.home.wms.service.impl;

import com.home.wms.entity.User;
import com.ktanx.jdbc.persist.JdbcDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.home.wms.service.WmsService;

@Service("wmsService")
public class WmsServiceImpl implements WmsService{
	@Autowired
	private JdbcDao jdbcDao;

	@Override
	public String sayHello(String name) {
		User u = jdbcDao.get(User.class, 1);
		return String.format("hello %s this is from wms", name);
	}

}
