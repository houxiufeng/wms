package com.home.wms.service;

import com.home.wms.dto.*;
import com.home.wms.entity.Torder;
import com.ktanx.common.model.PageList;

import java.util.List;

/**
 * Created by fitz on 2018/3/13.
 */
public interface OrderService {
	PageList<OrderVo> findPageOrders(QueryOrderParams params);
	void saveOrder(Torder order);
	void updateOrder(Torder order);
	Torder getOrderById(Long id);
	OrderVo getOrderVoById(Long id);
	List<Torder> findOrders(Torder order);
	void updateWithNull(Torder torder);
	void feedback(Long orderId, Long engineerId, Integer score, String feedback);
	OrderInfo getOrderInfo(Long id);
	StatOrderVo statRecent6MonthsOrders(Long engineerId);
	PageList<EngineerOrderSum> findEngineerOrderSum(QueryEngineerOrderSum params);
	PageList<EngineerOrderRate> findEngineerOrderRate(QueryEngineerOrderSum params);
	PageList<OrderVo> findMonthOrders(QueryMonthOrderParams params);
	StatOrderVo statBranchRecent6MonthsOrders(Long branchId);
	PageList<CustomerOrderSum> findCustomerOrderSum(QueryCustomerOrderSum params);
}
