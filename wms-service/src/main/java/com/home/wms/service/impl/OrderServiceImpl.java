package com.home.wms.service.impl;

import com.google.common.collect.Lists;
import com.home.wms.dto.BranchProductInfo;
import com.home.wms.dto.OrderVo;
import com.home.wms.dto.QueryOrderParams;
import com.home.wms.entity.Torder;
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
		sql.append("(select p.model from product p where bp.product_id = p.id) product_model,");
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
		Long maxId = (Long)jdbcDao.createSelect(Torder.class).addSelectField("max(id)").notSelectEntityField().objectResult();
		if (maxId == null) {
			maxId = 0L;
		}
		maxId++;
		order.setOrderNo(branchProductInfo.getProduct().getCode() +"-"+branchProductInfo.getBranch().getCode()+ "-" +maxId);
        jdbcDao.insert(order);
	}

	@Override
	public void updateOrder(Torder order) {
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
}
