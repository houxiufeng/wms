package com.home.wms.service;

import com.home.wms.dto.OrderVo;
import com.home.wms.dto.QueryOrderParams;
import com.home.wms.entity.Torder;
import com.ktanx.common.model.PageList;

/**
 * Created by fitz on 2018/3/13.
 */
public interface OrderService {
	PageList<OrderVo> findPageOrders(QueryOrderParams params);
	void saveOrder(Torder order);
	void updateOrder(Torder order);
	Torder getOrderById(Long id);
	OrderVo getOrderVoById(Long id);
}
