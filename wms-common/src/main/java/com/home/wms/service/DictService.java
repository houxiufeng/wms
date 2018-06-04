package com.home.wms.service;

import com.home.wms.dto.OrderTimeSetting;
import com.home.wms.dto.QueryDictParams;
import com.home.wms.entity.Dict;
import com.home.wms.entity.OrderTime;
import com.ktanx.common.model.PageList;

import java.util.List;

/**
 * Created by fitz on 2018/3/6.
 */
public interface DictService {
	PageList<Dict> findPageDicts(QueryDictParams params);
	void saveDict(Dict dict);
	void deleteDict(Long id);
	List<Dict> findByType(Short type);
    void saveOrderTime(OrderTime orderTime);
    void updateOrderTime(OrderTime orderTime);
    OrderTime getOrderTime(Integer orderStatus, Integer type, Long organizationId);
    void saveOrUpdateOrderTime(OrderTimeSetting orderTimeSetting);
    List<OrderTime> findAllOrderTimes(Long organizationId);
    OrderTimeSetting findOrderTimeSetting(Long organizationId);
    String findDictNamesByIds(String ids);
}
