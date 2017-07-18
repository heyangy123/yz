package com.yz.action;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.base.action.CoreController;
import com.base.dao.model.Grid;
import com.base.dialect.PaginationSupport;
import com.base.util.JSONUtils;
import com.yz.dao.model.Order;
import com.yz.daoEx.model.OrderEx;
import com.yz.service.OrderService;

/**
 * 订单列表
 * @author Cailin
 *
 */
@Controller
@RequestMapping("/order")
public class OrderController extends CoreController {
	@Autowired
	private OrderService orderService;
	
	@RequestMapping("list")
	@ResponseBody
	public String list(Order order , Integer page,Integer rows, Model model) {
		PaginationSupport.byPage(page, rows);
		List<OrderEx> list = orderService.selectBackList(order);
		return JSONUtils.serialize(new Grid(PaginationSupport.getContext().getTotalCount(), list));
	}
	
	
	@ResponseBody
	@RequestMapping("/findById")
	public String findById(String id) throws Exception{
		Order order = orderService.selectByPrimaryKey(id);
		return JSONUtils.serialize(order);
	}
	
	@RequestMapping("/deliveryConfirm")
	public String deliveryConfirm(Order order) throws Exception{
		if(order.getId() != null && !order.getId().isEmpty()){
			order.setDeliveryStatus(1);//已发货
			orderService.updateByPrimaryKeySelective(order);
		}
		return "order/orderList";
	}
	
	
}
