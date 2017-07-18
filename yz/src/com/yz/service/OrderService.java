package com.yz.service;

import com.yz.dao.OrderMapper;
import com.yz.dao.model.Order;
import com.yz.dao.model.OrderExample;
import com.yz.daoEx.OrderMapperEx;
import com.yz.daoEx.model.OrderEx;

import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class OrderService {
    @Autowired
    private OrderMapper orderMapper;
    
    @Autowired
    private OrderMapperEx orderMapperEx;

    private static final Logger logger = Logger.getLogger(OrderService.class);

    public int countByExample(OrderExample example) {
        return this.orderMapper.countByExample(example);
    }

    public Order selectByPrimaryKey(String id) {
        return this.orderMapper.selectByPrimaryKey(id);
    }

    public List<Order> selectByExample(OrderExample example) {
        return this.orderMapper.selectByExampleWithBLOBs(example);
    }

    public int deleteByPrimaryKey(String id) {
        return this.orderMapper.deleteByPrimaryKey(id);
    }

    public int updateByPrimaryKeySelective(Order record) {
        return this.orderMapper.updateByPrimaryKeySelective(record);
    }

    public int updateByPrimaryKey(Order record) {
        return this.orderMapper.updateByPrimaryKeyWithBLOBs(record);
    }

    public int deleteByExample(OrderExample example) {
        return this.orderMapper.deleteByExample(example);
    }

    public int updateByExampleSelective(Order record, OrderExample example) {
        return this.orderMapper.updateByExampleSelective(record, example);
    }

    public int updateByExample(Order record, OrderExample example) {
        return this.orderMapper.updateByExampleWithBLOBs(record, example);
    }

    public int insert(Order record) {
        return this.orderMapper.insert(record);
    }

    public int insertSelective(Order record) {
        return this.orderMapper.insertSelective(record);
    }
    
    public List<OrderEx> selectList(Map<String,Object> map){
    	return this.orderMapperEx.selectList(map);
    }
    
    /**
     * 后台展示列表
     * @param example
     * @return
     */
    public List<OrderEx> selectBackList(Order order) {
        return this.orderMapperEx.selectBackList(order);
    }
}