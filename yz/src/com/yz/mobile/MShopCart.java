package com.yz.mobile;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.base.dialect.PaginationSupport;
import com.base.mobile.MobException;
import com.base.mobile.MobileInfo;
import com.base.mobile.server.annotation.Mobile;
import com.base.mobile.server.annotation.MobileMethod;
import com.base.util.DateUtil;
import com.eone.common.proto.MCommon.MRet;
import com.eone.common.proto.MShopCart.MExchangeLog;
import com.eone.common.proto.MShopCart.MExchangeLogList;
import com.eone.common.proto.MShopCart.MGoods;
import com.eone.common.proto.MShopCart.MGoodsList;
import com.item.dao.model.User;
import com.item.service.UserService;
import com.yz.dao.model.Order;
import com.yz.dao.model.Product;
import com.yz.dao.model.ProductExample;
import com.yz.daoEx.model.OrderEx;
import com.yz.service.OrderService;
import com.yz.service.ProductService;


@Mobile
@Component
public class MShopCart {
	
	@Autowired
	private ProductService productService;
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private OrderService orderService;
	
	
	/**
	@api MGoodsList MGoodsList //积分商品列表  
	@return MGoodsList
	*/
	@MobileMethod(methodno = "MGoodsList", isLogin = false ,isPage = true)
	public MGoodsList.Builder GoodsList (MobileInfo mobileInfo,Integer page,Integer limit){
		ProductExample example = new ProductExample();
		example.createCriteria().andStateEqualTo(1);
		PaginationSupport.byPage(page, limit,false);
		List<Product> list = productService.selectByExample(example);
		MGoodsList.Builder ret = MGoodsList.newBuilder();
		for (Product product : list) {
			MGoods.Builder m = MGoods.newBuilder();
			m.setTitle(product.getTitle());
			m.setCredit(product.getScore());
			m.setImg(product.getImg());
			m.setId(product.getId());
			m.setInfo(product.getInfo());
			m.setStock(product.getStock());
			ret.addGoods(m);
		}
		return ret;
	}
	
	/**
	@api MGoodsDetail MGoodsDetail //积分商品详情  
	@param required id 商品id
	@return MGoods
	*/
	@MobileMethod(methodno = "MGoodsDetail", isLogin = false )
	public MGoods.Builder GoodsDetail (MobileInfo mobileInfo,String id) throws Exception{
		
		if(StringUtils.isBlank(id)){
			throw new MobException("id");
		}
		
		ProductExample example = new ProductExample();
		example.createCriteria().andStateEqualTo(1);
		Product p = productService.selectByPrimaryKey(id);
		
		MGoods.Builder ret = MGoods.newBuilder();
		ret.setTitle(p.getTitle());
		ret.setCredit(p.getScore());
		ret.setImg(p.getImg());
		ret.setId(p.getId());
		ret.setInfo(p.getInfo());
		ret.setStock(p.getStock());
		return ret;
		
	}
	
	/**
	@api MExchangeGoods MExchangeGoods//兑换商品   (需要登录)
	@param required name // 姓名
	@param required tel // 电话
	@param required address // 地址
	@param required info // 备注
	@param required goodId // 商品id
 	@param required num // 数量
	@return MRet
	*/
	@MobileMethod(methodno = "MExchangeGoods", isLogin = true ,isPage = false)
	public MRet.Builder ExchangeGoods (MobileInfo mobileInfo,String goodId,String num,String name,String tel,String address,String info) throws Exception{
		
		if(StringUtils.isBlank(goodId)){
			throw new MobException("goodId");
		}
		if(StringUtils.isBlank(num)){
			throw new MobException("num");
		}
		if(StringUtils.isBlank(name)){
			throw new MobException("name");
		}
		if(StringUtils.isBlank(tel)){
			throw new MobException("tel");
		}
		if(StringUtils.isBlank(address)){
			throw new MobException("address");
		}
		if(StringUtils.isBlank(info)){
			throw new MobException("info");
		}
		
		MRet.Builder ret = MRet.newBuilder();
		
		ProductExample example = new ProductExample();
		example.createCriteria().andStateEqualTo(1);
		Product p = productService.selectByPrimaryKey(goodId);
		
		User u = userService.selectByPrimaryKey(mobileInfo.getUserid());
		
		if(p.getStock()<=0||(p.getStock()-Integer.parseInt(num)<0)){
			throw new MobException(MProdcutError.STOCK_NOT_ENOUGH);
		}
		
		if(u.getScore()<p.getScore()){
			throw new MobException(MProdcutError.CREDIT_NOT_ENOUGH);
		}
		
		u.setScore(u.getScore()-p.getScore());
		p.setStock(p.getStock()-Integer.parseInt(num));
		if(p.getStock()<=0){
			p.setState(0);
		}
		
		userService.updateByPrimaryKeySelective(u);
		productService.updateByPrimaryKeySelective(p);
		
		Order o = new Order();
		o.setProductId(goodId);
		o.setRemark(info);
		o.setScore(p.getScore());
		o.setState(1);
		o.setCreateTime(new Date());
		o.setUserId(mobileInfo.getUserid());
		o.setTitle(p.getTitle());
		o.setAddress(address);
		orderService.insert(o);
		
		ret.setCode(0);
		ret.setMsg("兑换成功");
		
		return ret;
		
	}
	
	/**
	@api MExchangeLog MExchangeLog //兑换记录   (需要登录)
	@return MExchangeLogList
	*/
	@MobileMethod(methodno = "MExchangeLog", isLogin = true ,isPage = true)
	public MExchangeLogList.Builder ExchangeLog (MobileInfo mobileInfo,Integer page,Integer limit) throws Exception{
		
		if(StringUtils.isBlank(mobileInfo.getUserid())){
			throw new MobException("userId");
		}
		
		MExchangeLogList.Builder ret = MExchangeLogList.newBuilder();
		
		Map<String,Object> map = new HashMap<String,Object>();
		
		map.put("userId", mobileInfo.getUserid());
		
		PaginationSupport.byPage(page, limit,false);
		
		List<OrderEx> list = orderService.selectList(map);
		for (OrderEx orderEx : list) {
			MExchangeLog.Builder m = MExchangeLog.newBuilder();
			m.setTitle(orderEx.getProductName());
			m.setCredit(orderEx.getScore());
			m.setImg(orderEx.getImg());
			m.setState(orderEx.getState());
			m.setTime(DateUtil.dateToStr(orderEx.getCreateTime(), DateUtil.YYMMDD));
			ret.addExchangeLog(m);
		}
		return ret;
	}
}
