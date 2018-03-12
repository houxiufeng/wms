package com.home.wms.web.test;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.convert.Convert;
import cn.hutool.core.util.StrUtil;
import com.google.common.base.Function;
import com.google.common.collect.Lists;
import com.home.wms.dto.UserVo;
import com.home.wms.entity.User;

import java.util.List;

/**
 * Created by fitz on 2018/2/27.
 */
public class Test1 {
	public static void main(String[] args) {
		UserVo vo = new UserVo();
		vo.setName("aabbcc");
		vo.setRoleCode("code");
		vo.setRoleName("namesssss");
		vo.setAddress("xxxxxxx");
		User user = new User();
		BeanUtil.copyProperties(vo, user);
		System.out.println(user.getAddress());
		System.out.println(user.getName());
		List<User> users = Lists.newArrayList();
		User u1 = new User();
		u1.setName("aaa");
		u1.setId(1L);
		User u2 = new User();
		u2.setName("bbb");
		u2.setId(2L);
		User u3 = new User();
		u3.setName("ccc");
		u3.setId(3L);
		users.add(u1);
		users.add(u2);
		users.add(u3);
		List<Long> userIds = Lists.transform(users, new Function<User, Long>(){
			@Override
			public Long apply(User input) {
				return input.getId();
			}
		});
		List<Long> newList = Lists.newArrayList();
		newList.addAll(userIds);
		for (Long item : userIds) {
			System.out.println(item);
		}
		User u4 = new User();
		u4.setName("ddd");
		u4.setId(4L);
		users.add(u4);
		u3.setId(33L);
		System.out.println("================");
		for (Long item : userIds) {
			System.out.println(item);
		}
		System.out.println("----------------");
		for (Long item : newList) {
			System.out.println(item);
		}

	}
}
