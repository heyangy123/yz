package com.yz.daoEx;

import java.util.List;
import java.util.Map;

import com.yz.dao.model.Order;
import com.yz.daoEx.model.OrderEx;


public interface OrderMapperEx{
	List<OrderEx> selectList (Map<String,Object> map);

	List<OrderEx> selectBackList(Order order);
}