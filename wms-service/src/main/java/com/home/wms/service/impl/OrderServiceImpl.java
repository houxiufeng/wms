package com.home.wms.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.date.DateTime;
import cn.hutool.core.date.DateUtil;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.home.wms.dto.*;
import com.home.wms.entity.*;
import com.home.wms.entity.Dict;
import com.home.wms.service.BranchProductService;
import com.home.wms.service.BranchService;
import com.home.wms.service.DictService;
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

import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Map;

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
	@Autowired
	private BranchService branchService;
	@Autowired
	private DictService dictService;
	private static final String[] months = {"Jan", "Feb", "Mar", "Apr", "May", "Jun", "Jul", "Aug", "Sep", "Oct", "Nov", "Dec"};

	@Override
	public PageList<OrderVo> findPageOrders(QueryOrderParams params) {
		StringBuffer sql =  new StringBuffer("select t.*,");
		sql.append("(select c.name from customer c where c.id = t.customer_id) customer_name,");
		sql.append("(select b.name from branch b where b.id = t.branch_id) branch_name,");
		sql.append("(select b.city from branch b where b.id = t.branch_id) branch_city,");
		sql.append("(select d.name from dict d where t.type = d.id) type_name,");
		sql.append("(select v.name from engineer v where t.engineer_id = v.id) engineer_name,");
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
		if (params.getBranchId() != null) {
			sql.append(" and t.branch_id = ?");
			paramList.add(params.getBranchId());
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
		if (params.getEngineerId() != null) {
			sql.append(" and t.engineer_id = ?");
			paramList.add(params.getEngineerId());
		}
		if (params.getFlag() != null && params.getFlag() == 2) {//已评价或取消(手机端)
			sql.append(" and ((t.score > 0 and t.status = 4) or t.status = 5)");
		}
		if (params.getFlag() != null && params.getFlag() == 1) {//未评价（手机端）
			sql.append(" and t.score is null and t.status in (0,1,2,3,4)");
		}
		if (params.getFeedbackFlag() != null && params.getFeedbackFlag() == 2) {//已评价(web端)
			sql.append(" and t.score > 0 and t.status = 4");
		}
		if (params.getFeedbackFlag() != null && params.getFeedbackFlag() == 1) {//未评价（web端）
			sql.append(" and t.score is null and t.status = 4");
		}
        sql.append(" order by t.id desc");
		return (PageList<OrderVo>)jdbcDao.createNativeExecutor().resultClass(OrderVo.class)
				.command(sql.toString()).forceNative(true).parameters(paramList.toArray())
				.pageList(params.getiDisplayStart()/params.getiDisplayLength() + 1, params.getiDisplayLength());
	}

	@Override
	public void saveOrder(Torder order) {
		order.setOrganizationId(AppContextManager.getCurrentUserInfo().getOrganizationId());
		order.setCreatedBy(AppContextManager.getCurrentUserInfo().getId());
		BranchProductInfo branchProductInfo = branchProductService.getBranchProductInfoById(order.getBranchProductId());
		PageList<String> orderNos = jdbcDao.createSelect(Torder.class).include("orderNo").where("organizationId",order.getOrganizationId()).orderBy("id").desc().oneColPageList(String.class, 1, 1);
		String orderNo = orderNos.size() > 0 ? orderNos.get(0) : null;
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
		return (OrderVo)jdbcDao.createNativeExecutor().resultClass(OrderVo.class).command(sql.toString()).forceNative(true).parameters(Lists.newArrayList(id).toArray()).singleResult();
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
	public void feedback(Long orderId, Long engineerId, Integer score, String feedback) {
		Torder params = new Torder();
		params.setId(orderId);
		params.setFeedback(feedback);
		params.setScore(score);
		params.setUpdatedTime(new Date());
		jdbcDao.update(params);
		if (score == 1) {
			jdbcDao.createUpdate(Engineer.class).set("{{[oneStar]}}","[oneStar]+1").where("id", engineerId).execute();
		} else if (score == 2) {
			jdbcDao.createUpdate(Engineer.class).set("{{[twoStar]}}","[twoStar]+1").where("id", engineerId).execute();
		} else if (score == 3) {
			jdbcDao.createUpdate(Engineer.class).set("{{[threeStar]}}","[threeStar]+1").where("id", engineerId).execute();
		} else if (score == 4) {
			jdbcDao.createUpdate(Engineer.class).set("{{[fourStar]}}","[fourStar]+1").where("id", engineerId).execute();
		} else if (score == 5) {
			jdbcDao.createUpdate(Engineer.class).set("{{[fiveStar]}}","[fiveStar]+1").where("id", engineerId).execute();
		}

	}

	@Override
	public OrderInfo getOrderInfo(Long id) {
		Torder order = jdbcDao.get(Torder.class, id);
		OrderInfo orderInfo = null;
		if (order != null) {
			orderInfo = new OrderInfo();
			OrderVo ordervo = new OrderVo();
			BeanUtil.copyProperties(order, ordervo);
			Dict dict = jdbcDao.get(Dict.class, order.getType());
			if (dict != null) {
				ordervo.setTypeName(dict.getName());
			}
			orderInfo.setOrder(ordervo);
			orderInfo.setBranch(branchService.getBranchVoById(order.getBranchId()));
			BranchProduct branchProduct = jdbcDao.get(BranchProduct.class, order.getBranchProductId());
			if (branchProduct != null) {
				orderInfo.setBranchProduct(branchProduct);
				Product product = jdbcDao.get(Product.class, branchProduct.getProductId());
				if (product != null) {
					ProductVo productVo = new ProductVo();
					BeanUtil.copyProperties(product, productVo);
					productVo.setSparePartListStr(dictService.findDictNamesByIds(product.getSparePartList()));
					productVo.setCheckListStr(dictService.findDictNamesByIds(product.getCheckList()));
					orderInfo.setProduct(productVo);
				}
			}
		}
		return orderInfo;
	}


	@Override
	public StatOrderVo statRecent6MonthsOrders(Long engineerId) {
		List<Object[]> tickList = Lists.newArrayList();
		int nowMonth = DateUtil.thisMonth();
		for (int i = 5; i >=0; i--)  {
			tickList.add(new Object[]{i, months[nowMonth]});
			nowMonth--;
			if (nowMonth < 0) {
				nowMonth = months.length - 1;
			}
		}
		Collections.reverse(tickList);
		Object[][] ticks = tickList.toArray(new Object[2][]);
		StatOrderVo vo = new StatOrderVo();
		vo.setTicks(ticks);
		DateTime checkoutTime = DateUtil.beginOfMonth(DateUtil.offsetMonth(new Date(), -5));
		List<Torder> torders = jdbcDao.createSelect(Torder.class).where("status",">",0)
				.and("status","!=",5)
				.and("checkTime",">",checkoutTime)
				.and("engineerId",engineerId)
				.and("organizationId",AppContextManager.getCurrentUserInfo().getOrganizationId())
				.orderBy("checkTime").asc().list();
		Map<Integer, Integer> monthCountMap = Maps.newHashMap();
		for (Torder torder : torders) {
			int month = DateUtil.month(torder.getCheckTime());
			if (monthCountMap.get(month) != null) {
				monthCountMap.put(month, monthCountMap.get(month) + 1);
			} else {
				monthCountMap.put(month, 1);
			}
		}

		int[][] datas = new int[6][2];
		int month = DateUtil.month(checkoutTime);
		for (int i = 0; i <=5; i++) {
			int[] data = new int[2];
			data[0] = i;
			data[1] = 0;
			if (monthCountMap.get(month) != null) {
				data[1] = monthCountMap.get(month);
			}
			datas[i] = data;
			month++;
			if (month > 11) {
				month = 0;
			}
		}
		vo.setData(datas);
		return vo;
	}

	@Override
	public PageList<EngineerOrderSum> findEngineerOrderSum(QueryEngineerOrderSum params) {
		List<Object> values = Lists.newArrayList();
		StringBuffer sb = new StringBuffer("select engineer_id, year(check_time) year, month(check_time) month, count(status=1 or null) checking_num, count(status=2 or null) fixing_num, count(status=4 or null) complete_num ");
		sb.append(" from `torder` where engineer_id = ? ");
		values.add(params.getEngineerId());
		if (StringUtils.isNotBlank(params.getStartTime())) {
			sb.append(" and check_time > ?");
			values.add(params.getStartTime());
		}
		if (StringUtils.isNotBlank(params.getEndTime())) {
			sb.append(" and check_time < ?");
			values.add(params.getEndTime());
		}
		if (params.getOrganizationId() != null) {
			sb.append(" and organization_id = ?");
			values.add(params.getOrganizationId());
		}
		sb.append(" group by YEAR(check_time), MONTH(check_time)");
		return (PageList<EngineerOrderSum>)jdbcDao.createNativeExecutor().resultClass(EngineerOrderSum.class).command(sb.toString()).forceNative(true).parameters(values.toArray())
		.pageList(params.getiDisplayStart()/params.getiDisplayLength() + 1, params.getiDisplayLength());
	}

	@Override
	public PageList<EngineerOrderRate> findEngineerOrderRate(QueryEngineerOrderSum params) {
		List<Object> values = Lists.newArrayList();
		StringBuffer sb = new StringBuffer("select engineer_id, year(check_time) year, month(check_time) month, count(score=1 or null) good_num, count(score=2 or null) normal_num, count(score=3 or null) bad_num ");
		sb.append(" from `torder` where engineer_id = ? ");
		values.add(params.getEngineerId());
		if (StringUtils.isNotBlank(params.getStartTime())) {
			sb.append(" and check_time > ?");
			values.add(params.getStartTime());
		}
		if (StringUtils.isNotBlank(params.getEndTime())) {
			sb.append(" and check_time < ?");
			values.add(params.getEndTime());
		}
		if (params.getOrganizationId() != null) {
			sb.append(" and organization_id = ?");
			values.add(params.getOrganizationId());
		}
		sb.append(" group by YEAR(check_time), MONTH(check_time)");
		return (PageList<EngineerOrderRate>)jdbcDao.createNativeExecutor().resultClass(EngineerOrderRate.class).command(sb.toString()).forceNative(true).parameters(values.toArray())
				.pageList(params.getiDisplayStart()/params.getiDisplayLength() + 1, params.getiDisplayLength());
	}


	@Override
	public PageList<OrderVo> findMonthOrders(QueryMonthOrderParams params) {
		String sql = "select t.*, (select d.name from dict d where t.type = d.id) type_name from torder t" +
				" where t.check_time > ? and t.check_time < ? and t.organization_id = ? and t.status = ?";
		List<Object> values = Lists.newArrayList(params.getMonthBegin(), params.getMonthEnd(), params.getOrganizationId(), params.getStatus());
		if (params.getEngineerId() != null) {
			sql += " and t.engineer_id = ?";
			values.add(params.getEngineerId());
		}
		if (params.getBranchId() != null) {
			sql += " and t.branch_id = ?";
			values.add(params.getBranchId());
		}
		return (PageList<OrderVo>)jdbcDao.createNativeExecutor().resultClass(OrderVo.class)
				.command(sql).forceNative(true).parameters(values.toArray())
				.pageList(params.getiDisplayStart()/params.getiDisplayLength() + 1, params.getiDisplayLength());

	}

	@Override
	public StatOrderVo statBranchRecent6MonthsOrders(Long branchId) {
		List<Object[]> tickList = Lists.newArrayList();
		int nowMonth = DateUtil.thisMonth();
		for (int i = 5; i >=0; i--)  {
			tickList.add(new Object[]{i, months[nowMonth]});
			nowMonth--;
			if (nowMonth < 0) {
				nowMonth = months.length - 1;
			}
		}
		Collections.reverse(tickList);
		Object[][] ticks = tickList.toArray(new Object[2][]);
		StatOrderVo vo = new StatOrderVo();
		vo.setTicks(ticks);
		DateTime checkTime = DateUtil.beginOfMonth(DateUtil.offsetMonth(new Date(), -5));
		List<Torder> torders = jdbcDao.createSelect(Torder.class).where("status",new Object[]{1,2,4})
				.and("checkTime",">",checkTime)
				.and("branchId",branchId)
				.and("organizationId",AppContextManager.getCurrentUserInfo().getOrganizationId())
				.orderBy("checkTime").asc().list();
		Map<Integer, Integer> monthCountMap = Maps.newHashMap();
		for (Torder torder : torders) {
			int month = DateUtil.month(torder.getCheckTime());
			if (monthCountMap.get(month) != null) {
				monthCountMap.put(month, monthCountMap.get(month) + 1);
			} else {
				monthCountMap.put(month, 1);
			}
		}

		int[][] datas = new int[6][2];
		int month = DateUtil.month(checkTime);
		for (int i = 0; i <=5; i++) {
			int[] data = new int[2];
			data[0] = i;
			data[1] = 0;
			if (monthCountMap.get(month) != null) {
				data[1] = monthCountMap.get(month);
			}
			datas[i] = data;
			month++;
			if (month > 11) {
				month = 0;
			}
		}
		vo.setData(datas);
		return vo;
	}

	@Override
	public PageList<CustomerOrderSum> findCustomerOrderSum(QueryCustomerOrderSum params) {
		List<Object> values = Lists.newArrayList();
		StringBuffer sb = new StringBuffer("select branch_id, year(check_time) year, month(check_time) month, count(status=1 or null) checking_num, count(status=2 or null) fixing_num, count(status=4 or null) complete_num ");
		sb.append(" from `torder` where branch_id = ? ");
		values.add(params.getBranchId());
		if (StringUtils.isNotBlank(params.getStartTime())) {
			sb.append(" and check_time > ?");
			values.add(params.getStartTime());
		}
		if (StringUtils.isNotBlank(params.getEndTime())) {
			sb.append(" and check_time < ?");
			values.add(params.getEndTime());
		}
		if (params.getOrganizationId() != null) {
			sb.append(" and organization_id = ?");
			values.add(params.getOrganizationId());
		}
		sb.append(" group by YEAR(check_time), MONTH(check_time)");
		return (PageList<CustomerOrderSum>)jdbcDao.createNativeExecutor().resultClass(CustomerOrderSum.class).command(sb.toString()).forceNative(true).parameters(values.toArray())
				.pageList(params.getiDisplayStart()/params.getiDisplayLength() + 1, params.getiDisplayLength());
	}

}
