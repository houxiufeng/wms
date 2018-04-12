package com.home.wms.web.test;

import cn.hutool.core.util.StrUtil;
import com.alibaba.fastjson.JSON;
import com.google.common.collect.Lists;
import com.home.wms.dto.UserVo;
import com.home.wms.entity.Dict;
import com.home.wms.entity.Permission;
import com.home.wms.entity.Torder;
import com.home.wms.entity.User;
import com.ktanx.common.model.PageList;
import com.ktanx.jdbc.persist.JdbcDao;
import org.apache.commons.lang.StringUtils;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.List;

/**
 * Created by fitz on 2018/1/18.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:applicationContext_test.xml"})
public class TestJdbcDao {

	@Autowired
	private JdbcDao jdbcDao;


	@Test
	public void testInsert() {
		User user = new User();
		user.setName("我是睡");
		jdbcDao.insert(user);
	}

	@Test
	public void testUpdate() {
		jdbcDao.createUpdate(User.class).set("{{age}}","age + 1").where("id", 1).execute();
	}

	@Test
	public void testSqlQuery() {
		List<UserPhone> list = (List<UserPhone>)jdbcDao.createNativeExecutor().resultClass(UserPhone.class)
				.command("select a.id, a.name, a.age, b.phone from user a left join user_phone b on a.id = b.user_id where a.id = ?")
				.parameters(new Object[]{1}).list();
		for (UserPhone uf : list) {
			System.out.println(JSON.toJSONString(uf));
		}
		StringUtils.isNotBlank("");
	}

	@Test
	public void testSqlQuery2() {
		StringBuffer sql =  new StringBuffer("select a.*, (select b.name from permission b where b.id = a.pid) pname from permission a where a.status = 1");
		List<Object> paramList = Lists.newArrayList();
		List<Long> ids = Lists.newArrayList(5L,6L,7L,8L);
		if (true) {
			sql.append(" and a.pid in  (?) ");
			paramList.add("5,6,7,8");
		}
		List<Permission> ps = (PageList<Permission>)jdbcDao.createNativeExecutor().resultClass(Permission.class).command(sql.toString()).parameters(paramList.toArray()).pageList(1, 10);
		for (Permission p : ps) {
			System.out.println(p.getName());
		}
	}
	@Test
	public void testSS() {
		Integer maxSeq = (Integer)jdbcDao.createSelect(Permission.class).addSelectField("max(seq)").notSelectEntityField().objectResult();
		System.out.println(maxSeq);
	}
	@Test
	public void testSingle() {
		List<Torder> orders = (List<Torder>)jdbcDao.createNativeExecutor().command("select * from torder where organization_id = 10 order by id desc limit 1").resultClass(Torder.class).list();
		String orderNo = orders.size() > 0 ? orders.get(0).getOrderNo():null;
		System.out.println(orderNo);
	}

	@Test
	public void testSingle2() {
		User user = new User();
		user.setOrganizationId(8L);
		User users = jdbcDao.querySingleResult(user);
		if (users != null) {
			System.out.println(users.getName());
		}
	}

	@Test
	public void testSingle3() {
		List<String> ss = jdbcDao.createSelect(Torder.class).include("orderNo").where("id","<",10).and("organizationId",null).orderById().desc().singleColumnList(String.class);
		PageList<String> stt = jdbcDao.createSelect(Torder.class).include("orderNo").where("id",">",10).and("organizationId",null).orderBy("id").desc().singleColumnPageList(String.class, 1, 1);
		Torder torder = jdbcDao.createSelect(Torder.class).where("organizationId", null).and("id",54).singleResult();
		String s = jdbcDao.createSelect(Torder.class).include("orderNo").where("id",120).singleColumnResult(String.class);
		System.out.println(s);
	}

	@Test
	public void testNative() {
		String sql = "select a.*, b.name role_name, b.code role_code from user a left join role b on a.role_id = b.id";
		List<UserVo> userVos = (List<UserVo>)jdbcDao.createNativeExecutor().command(sql).resultClass(UserVo.class).list();
		for(UserVo vo : userVos) {
			System.out.println(vo.getName() + ": " + vo.getRoleName());
		}
	}

	@Test
	public void testMoreOrLess() {
		//user @Transient on get method can ignore filed in table
 		Dict dict = jdbcDao.get(Dict.class, 1);
//		dict.setGender(dict.getName() + "---ssf");
//		System.out.println(dict.getName());
//		System.out.println(dict.getGender());
	}

	public static void main(String[] args) {
		List<Long> ids = Lists.newArrayList(5L,6L,7L,8L);
		System.out.println(StrUtil.join(",",ids));
	}
}
