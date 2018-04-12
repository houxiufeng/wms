package com.home.wms.service.impl;

import com.google.common.collect.Lists;
import com.home.wms.dto.OrderTimeSetting;
import com.home.wms.dto.QueryDictParams;
import com.home.wms.entity.Dict;
import com.home.wms.entity.OrderTime;
import com.home.wms.enums.DictType;
import com.home.wms.enums.OrderStatus;
import com.home.wms.service.DictService;
import com.home.wms.utils.AppContextManager;
import com.ktanx.common.model.PageList;
import com.ktanx.jdbc.command.entity.Select;
import com.ktanx.jdbc.persist.JdbcDao;
import org.apache.commons.lang3.StringUtils;
import org.aspectj.weaver.ast.Or;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by fitz on 2018/3/10.
 */
@Service
public class DictServiceImpl implements DictService{

	@Autowired
	private JdbcDao jdbcDao;
	private static Logger LOGGER = LoggerFactory.getLogger(DictServiceImpl.class);

	@Override
	public PageList<Dict> findPageDicts(QueryDictParams params) {
		Select<Dict> s = jdbcDao.createSelect(Dict.class);
		Dict entity = new Dict();
		entity.setName(params.getName());
		entity.setOrganizationId(params.getOrganizationId());
		entity.setType(params.getType());
		s.andConditionEntity(entity);
		return s.orderBy("id").desc().pageList(params.getiDisplayStart()/params.getiDisplayLength() + 1, params.getiDisplayLength());
	}

	@Override
	public void saveDict(Dict dict) {
		dict.setOrganizationId(AppContextManager.getCurrentUserInfo().getOrganizationId());
		dict.setName(dict.getName().trim());
        jdbcDao.insert(dict);
	}

	@Override
	public void deleteDict(Long id) {
        jdbcDao.delete(Dict.class, id);
	}

	@Override
	public List<Dict> findByType(Short type) {
		QueryDictParams dictParams = new QueryDictParams();
		dictParams.setOrganizationId(AppContextManager.getCurrentUserInfo().getOrganizationId());
		dictParams.setType(type);
		dictParams.setiDisplayStart(0);
		dictParams.setiDisplayLength(Integer.MAX_VALUE);
		return findPageDicts(dictParams);
	}

	@Override
	public void saveOrderTime(OrderTime orderTime) {
		jdbcDao.insert(orderTime);
	}

	@Override
	public void updateOrderTime(OrderTime orderTime) {
		jdbcDao.createUpdate(OrderTime.class).setForEntityWhereId(orderTime).updateNull().execute();
	}

	@Override
	public OrderTime getOrderTime(Integer orderStatus, Integer type, Long organizationId) {
		return jdbcDao.createSelect(OrderTime.class).where("organizationId", organizationId).and("orderStatus",orderStatus).and("type",type).singleResult();
//		if (organizationId != null) {
//			List<OrderTime> list = (List<OrderTime>)jdbcDao.createNativeExecutor().command("select * from order_time where order_status = ? and type = ? and organization_id = ? limit 1").parameters(new Object[]{orderStatus, type, organizationId}).resultClass(OrderTime.class).list();
//			return list.size() > 0 ? list.get(0) : null;
//		} else {
//			List<OrderTime> list = (List<OrderTime>)jdbcDao.createNativeExecutor().command("select * from order_time where order_status = ? and type = ? and organization_id is null limit 1").parameters(new Object[]{orderStatus, type}).resultClass(OrderTime.class).list();
//			return list.size() > 0 ? list.get(0) : null;
//		}
	}
	@Override
	public void saveOrUpdateOrderTime(OrderTimeSetting orderTimeSetting) {
		Long organizationId = AppContextManager.getCurrentUserInfo().getOrganizationId();
		settingOrderTime(orderTimeSetting.getAssignWarn(), 1, OrderStatus.ASSIGNING.getValue(), organizationId);
		settingOrderTime(orderTimeSetting.getAssignOver(), 2, OrderStatus.ASSIGNING.getValue(), organizationId);
		settingOrderTime(orderTimeSetting.getCheckWarn(), 1, OrderStatus.CHECKING.getValue(), organizationId);
		settingOrderTime(orderTimeSetting.getCheckOver(), 2, OrderStatus.CHECKING.getValue(), organizationId);
		settingOrderTime(orderTimeSetting.getFixWarn(), 1, OrderStatus.FIXING.getValue(), organizationId);
		settingOrderTime(orderTimeSetting.getFixOver(), 2, OrderStatus.FIXING.getValue(), organizationId);
	}

	@Override
	public List<OrderTime> findAllOrderTimes(Long organizationId) {
		Select<OrderTime> s = jdbcDao.createSelect(OrderTime.class);
		if (organizationId == null) {
			s.where("organizationId","is", null);
		} else {
			s.where("organizationId", organizationId);
		}
		return s.list();
	}

	@Override
	public OrderTimeSetting findOrderTimeSetting(Long organizationId) {
		List<OrderTime> orderTimes = findAllOrderTimes(organizationId);
		OrderTimeSetting orderTimeSetting = new OrderTimeSetting();
		for (OrderTime item : orderTimes) {
			if (item.getOrderStatus() == OrderStatus.ASSIGNING.getValue()) {
				if (item.getType() == 1) {
					orderTimeSetting.setAssignWarn(item.getHours());
				} else if (item.getType() == 2) {
					orderTimeSetting.setAssignOver(item.getHours());
				}
			} else if (item.getOrderStatus() == OrderStatus.CHECKING.getValue()) {
				if (item.getType() == 1) {
					orderTimeSetting.setCheckWarn(item.getHours());
				} else if (item.getType() == 2) {
					orderTimeSetting.setCheckOver(item.getHours());
				}
			} else if (item.getOrderStatus() == OrderStatus.FIXING.getValue()) {
				if (item.getType() == 1) {
					orderTimeSetting.setFixWarn(item.getHours());
				} else if (item.getType() == 2) {
					orderTimeSetting.setFixOver(item.getHours());
				}
			}
		}
		return orderTimeSetting;
	}

	private void settingOrderTime(Integer hours, Integer type, Integer orderStatus, Long organizationId) {
		hours = hours == null ? 0 : hours;
		OrderTime ot = getOrderTime(orderStatus, type, organizationId);
		if (ot != null) {
			ot.setHours(hours);
			updateOrderTime(ot);
		} else {
			ot = new OrderTime();
			ot.setHours(hours);
			ot.setOrderStatus(orderStatus);
			ot.setOrganizationId(organizationId);
			ot.setType(type);
			saveOrderTime(ot);
		}
	}

}
