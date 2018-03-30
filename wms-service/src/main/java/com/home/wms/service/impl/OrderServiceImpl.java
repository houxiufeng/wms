package com.home.wms.service.impl;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.StrUtil;
import com.google.common.collect.Lists;
import com.home.wms.dto.BranchProductInfo;
import com.home.wms.dto.OrderVo;
import com.home.wms.dto.QueryOrderParams;
import com.home.wms.entity.Torder;
import com.home.wms.entity.Vendor;
import com.home.wms.enums.OrderStatus;
import com.home.wms.service.BranchProductService;
import com.home.wms.service.OrderService;
import com.home.wms.utils.AppContextManager;
import com.ktanx.common.model.PageList;
import com.ktanx.jdbc.persist.JdbcDao;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

/**
 * Created by fitz on 2018/3/13.
 */
@Service
public class OrderServiceImpl implements OrderService {
	@Autowired
	private JdbcDao jdbcDao;
	private static Logger LOGGER = LoggerFactory.getLogger(OrderServiceImpl.class);
	@Autowired
	private BranchProductService branchProductService;

	@Override
	public PageList<OrderVo> findPageOrders(QueryOrderParams params) {
		StringBuffer sql =  new StringBuffer("select t.*,");
		sql.append("(select c.name from customer c where c.id = t.customer_id) customer_name,");
		sql.append("(select b.name from branch b where b.id = t.branch_id) branch_name,");
		sql.append("(select d.name from dict d where t.type = d.id) type_name,");
		sql.append("(select v.name from vendor v where t.vendor_id = v.id) vendor_name,");
		sql.append("(select p.model from product p where bp.product_id = p.id) product_model,");
		if (params.getOrganizationId() != null) {
			sql.append("(select ot.hours from order_time ot where ot.order_status = t.status and ot.type = 1 and ot.organization_id = t.organization_id) warn_hours,");
			sql.append("(select ot.hours from order_time ot where ot.order_status = t.status and ot.type = 2 and ot.organization_id = t.organization_id) over_hours,");
		} else {
			sql.append("(select ot.hours from order_time ot where ot.order_status = t.status and ot.type = 1 and ot.organization_id is null) warn_hours,");
			sql.append("(select ot.hours from order_time ot where ot.order_status = t.status and ot.type = 2 and ot.organization_id is null) over_hours,");
		}
		sql.append("(select p.name from product p where bp.product_id = p.id) product_name from torder t ");
		sql.append("left join branch_product bp on t.branch_product_id = bp.id where 1");

		List<Object> paramList = Lists.newArrayList();
		if (params.getOrganizationId() != null) {
			sql.append(" and t.organization_id = ?");
			paramList.add(params.getOrganizationId());
		}
		if (params.getStatus() != null) {
			sql.append(" and t.status = ?");
			paramList.add(params.getStatus());
		}
		if (params.getCustomerId() != null) {
			sql.append(" and t.customer_id = ?");
			paramList.add(params.getCustomerId());
		}
		if (StringUtils.isNotBlank(params.getOrderNo())) {
			sql.append(" and t.order_no = ?");
			paramList.add(params.getOrderNo());
		}
		if (StringUtils.isNotBlank(params.getStartTime())) {
			sql.append(" and t.created_time >= ?");
			paramList.add(params.getStartTime());
		}
		if (StringUtils.isNotBlank(params.getEndTime())) {
			sql.append(" and t.created_time <= ?");
			paramList.add(params.getEndTime() + " 23:59:59");
		}
		if (params.getVendorId() != null) {
			sql.append(" and t.vendor_id = ?");
			paramList.add(params.getVendorId());
		}
		if (params.getFlag() != null && params.getFlag() == 2) {//已评价或取消
			sql.append(" and ((t.score > 0 and t.status = 4) or t.status = 5)");
		}
		if (params.getFlag() != null && params.getFlag() == 1) {//未评价
			sql.append(" and t.score is null and t.status in (0,1,2,3,4)");
		}
        sql.append(" order by t.id desc");
		return (PageList<OrderVo>)jdbcDao.createNativeExecutor().resultClass(OrderVo.class)
				.command(sql.toString()).parameters(paramList.toArray())
				.pageList(params.getiDisplayStart()/params.getiDisplayLength() + 1, params.getiDisplayLength());
	}

	@Override
	public void saveOrder(Torder order) {
		order.setOrganizationId(AppContextManager.getCurrentUserInfo().getOrganizationId());
		order.setCreatedBy(AppContextManager.getCurrentUserInfo().getId());
		BranchProductInfo branchProductInfo = branchProductService.getBranchProductInfoById(order.getBranchProductId());
//		Long maxId = (Long)jdbcDao.createSelect(Torder.class).addSelectField("max(id)").notSelectEntityField().objectResult();
//		if (maxId == null) {
//			maxId = 0L;
//		}
//		maxId++;
		String orderNo = null;
		if (order.getOrganizationId() != null) {
			orderNo = (String)jdbcDao.createNativeExecutor().command("select order_no from torder where organization_id = ? order by id desc limit 1").parameters(new Object[]{order.getOrganizationId()}).resultClass(String.class).singleResult();
		} else {
			orderNo = (String)jdbcDao.createNativeExecutor().command("select order_no from torder where organization_id is null order by id desc limit 1").resultClass(String.class).singleResult();
		}
		String newOrderNo = null;
		if (StringUtils.isNotBlank(orderNo)) {
			Integer d = Integer.parseInt(orderNo.substring(0, 6));
			newOrderNo = String.format("%06d", ++d) + "-" +branchProductInfo.getProduct().getCode() +"-"+branchProductInfo.getBranch().getCode();
		} else {
			newOrderNo = "000001-" + branchProductInfo.getProduct().getCode() +"-"+branchProductInfo.getBranch().getCode();
		}
		order.setOrderNo(newOrderNo);
        jdbcDao.insert(order);
	}

	@Override
	public void updateOrder(Torder order) {
		order.setUpdatedTime(new Date());
		jdbcDao.update(order);
	}


	@Override
	public Torder getOrderById(Long id) {
		return jdbcDao.get(Torder.class, id);
	}
	@Override
	public OrderVo getOrderVoById(Long id) {
		StringBuffer sql =  new StringBuffer("select t.*,");
		sql.append("(select c.name from customer c where c.id = t.customer_id) customer_name,");
		sql.append("(select b.name from branch b where b.id = t.branch_id) branch_name,");
		sql.append("(select d.name from dict d where t.type = d.id) type_name,");
		sql.append("(select p.model from product p where bp.product_id = p.id) product_model,");
		sql.append("(select p.name from product p where bp.product_id = p.id) product_name from torder t ");
		sql.append("left join branch_product bp on t.branch_product_id = bp.id where t.id = ?");
		return (OrderVo)jdbcDao.createNativeExecutor().resultClass(OrderVo.class).command(sql.toString()).parameters(Lists.newArrayList(id).toArray()).singleResult();
	}

	@Override
	public List<Torder> findOrders(Torder torder) {
		return jdbcDao.queryList(torder);
	}

	@Override
	public void updateWithNull(Torder torder) {
		torder.setUpdatedTime(new Date());
		jdbcDao.createUpdate(Torder.class).setForEntityWhereId(torder).updateNull().execute();
	}

	@Override
	@Transactional
	public void feedback(Long orderId, Long vendorId, Integer score, String feedback) {
		Torder params = new Torder();
		params.setId(orderId);
		params.setFeedback(feedback);
		params.setScore(score);
		params.setUpdatedTime(new Date());
		jdbcDao.update(params);
		if (score == 1) {//好评
			jdbcDao.createUpdate(Vendor.class).set("{{[goodScore]}}","[goodScore]+1").where("id", vendorId).execute();
		} else if (score == 2) {//中评
			jdbcDao.createUpdate(Vendor.class).set("{{[moderateScore]}}","[moderateScore]+1").where("id", vendorId).execute();
		} else if (score == 3) {//差评
			jdbcDao.createUpdate(Vendor.class).set("{{[badScore]}}","[badScore]+1").where("id", vendorId).execute();
		}

	}
}
