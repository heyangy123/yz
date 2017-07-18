package com.yz.mobile;

import com.alibaba.fastjson.JSONObject;
import com.base.action.CoreController;
import com.base.mobile.server.annotation.Mobile;
import com.base.mobile.server.annotation.MobileMethod;
import com.base.pay.PayPropertySupport;
import com.base.pay.ali.AlipayNotify;
import com.base.pay.wx.WxPrepay;
import com.base.pay.wx.util.XMLUtil;
import com.base.util.DateUtil;
import com.base.util.ResponseUtils;
import com.item.dao.model.Notify;
import com.item.service.NotifyService;
import com.item.service.UserService;
import com.pay.ali.AlipayConfig;
import com.pay.method.PayMethod;
import com.pay.wx.util.ConstantUtil;
import com.yz.dao.model.*;
import com.yz.service.GameDetailService;
import com.yz.service.GamePlaceService;
import com.yz.service.GamePlayerService;
import com.yz.service.GameService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.math.BigDecimal;
import java.util.*;


@Mobile
@Component
@Controller
@RequestMapping(value = "/pay")
public class PayController  extends CoreController {
	
	@Autowired
	private GameDetailService gameDetailService;
	
	@Autowired
	private GameService gameService;

	@Autowired
	private UserService userService;

	@Autowired
	private GamePlayerService gamePlayerService;

	@Autowired
	private NotifyService notifyService;

	@Autowired
	private GamePlaceService gamePlaceService;
	
	
	/**
	 * 支付
	 */
	@MobileMethod(methodno = "MToPay", isLogin = true, isPage = false)
	public JSONObject toPay(String gameDetailId,String amount,String payMethod,String userid) throws Exception{
		
		JSONObject j = new JSONObject();
		j.put("code", 0);
		
		//数据校验
		if(StringUtils.isBlank(gameDetailId)){
			j.put("msg", "缺少gameDetailId");
			return j;
		}
		if(StringUtils.isBlank(amount)){
			j.put("msg", "缺少amount");
			return j;
		}
		if(StringUtils.isBlank(payMethod)){
			j.put("msg", "缺少payMethod");
			return j;
		}
		
		GameDetail g = gameDetailService.selectByPrimaryKey(gameDetailId);
		g.setAmount(new BigDecimal(amount));
		g.setPayMethod(payMethod);
		gameDetailService.updateByPrimaryKey(g);
		
		if(payMethod.equals(AlipayConfig.ALIPAY)){
			String str = PayMethod.aliAppPay(g.getId(), new BigDecimal(amount));
			j.put("str", str);
			j.put("code", 1);
			j.put("msg", "success");
			return j;
		}else if(payMethod.equals(AlipayConfig.WXPAY)){
			Map m = PayMethod.wxAppPay(new BigDecimal(amount), g.getId(), "乐动约战-缴费订单","8.8.8.8");
			j.put("m", m);
			j.put("code", 1);
			j.put("msg", "success");
			return j;
		}
		j.put("msg", "fail");
		return j;
	}
	
	
	//支付宝回调
	@RequestMapping(value = "/mobilePayNotify")
	public void alipayNotify(HttpServletRequest request, HttpServletResponse response) throws Exception {
		logger.info("$$$$alipayNotify进入!");
		try {
			// 获取支付宝POST过来反馈信息
			Map<String, String> params = new HashMap<String, String>();
			Map<String, String[]> requestParams = request.getParameterMap();
			for (Iterator<String> iter = requestParams.keySet().iterator(); iter.hasNext();) {
				String name = iter.next();
				String[] values = (String[]) requestParams.get(name);
				String valueStr = "";
				for (int i = 0; i < values.length; i++) {
					valueStr = (i == values.length - 1) ? valueStr + values[i] : valueStr + values[i] + ",";
				}
				params.put(name, valueStr);
			}
			// 买家付款账号
			String buyer_logon_id = params.get("buyer_logon_id");
			BigDecimal total_amount = new BigDecimal(params.get("total_amount"));
			// 商户订单号
			String out_trade_no = params.get("out_trade_no");
			// 支付宝交易号
			String trade_no = params.get("trade_no");
			// 交易状态
			String trade_status = params.get("trade_status");
			String extraParam = "";
			int offset = out_trade_no.indexOf("_");
			if(offset>-1){
				extraParam = out_trade_no.substring(offset+1);
				out_trade_no = out_trade_no.substring(0,offset);
			}

			// 获得支付信息
			String payKey = PayPropertySupport.getProperty("pay.ali.publicKey");
			logger.info("-------------------------》payKey："+payKey);
			if (AlipayNotify.verifyMobileNotify(params, payKey)) {// 验证成功
				if (trade_status.equals("TRADE_FINISHED") || trade_status.equals("TRADE_SUCCESS")) {
					// 支付成功后执行相关业务
					logger.info("订单编号:"+out_trade_no+";支付金额:"+total_amount);
					afterPay(out_trade_no, trade_no,ConstantUtil.alipay);
				}
			} else {
				logger.info("$$$$alipayNotify验证失败——商户订单号:" + out_trade_no + ";支付宝交易号:" + trade_no + ",交易状态:" + trade_status);
			}
			ResponseUtils.renderText(response, "success");
		} catch (Exception e) {
			logger.info("$$$$alipayNotify业务逻辑异常:" + e.getMessage(), e);
			ResponseUtils.renderText(response, "fail");
		}
	}
	
	//微信回调
	@RequestMapping("/wxMobileNotify")
	public void wxNotify(HttpServletRequest request, HttpServletResponse response) throws Exception {
		logger.info("@@@@收到微信支付信息,进入notify流程@@@@");
		try {
			InputStream in = request.getInputStream();
			String s = null;
			BufferedReader br = new BufferedReader(new InputStreamReader(in, "utf-8"));
			StringBuffer sb = new StringBuffer();
			while ((s = br.readLine()) != null) {
				sb.append(s);
			}
			br.close();
			in.close();
			Map<String, String> params = XMLUtil.doXMLParse(sb.toString());

			SortedMap<String, String> newParams = new TreeMap<String, String>(params);
			
			newParams.put("key", ConstantUtil.APP_KEY);

			String out_trade_no = (String) params.get("out_trade_no");

			String trade_no = (String) params.get("transaction_id");
			// 总金额，分
			String total = (String) params.get("total_fee");

			String respCode = (String) params.get("result_code");

			String openId = (String) params.get("openid");
			// 自定义参数
			String extraParam = (String) params.get("attach");
			if (WxPrepay.isValiSign(newParams)) {
				logger.info("@@@@验证成功@@@@");
				if (respCode != null && respCode.equals("SUCCESS")) {
					
					//支付成功后 业务逻辑 
					afterPay(out_trade_no, trade_no,ConstantUtil.wxpay);
					
				} else {
					logger.info("@@@@支付交易状态未知——订单号:" + out_trade_no + ";交易状态:" + respCode + ";微信支付订单号:" + trade_no);
				}
			} else {
				logger.info("@@@@验证失败@@@@");
			}
		} catch (Exception e) {
			logger.info("@@@@支付后业务逻辑异常" + e.getMessage() + "@@@@");
		}

	}


	@RequestMapping("/test")
	public void test(){
		afterPay("1334753c3a0642a2aefc1ce9f789ee54","400343200120170715086071545","wxpay");
	}

	public void afterPay(String out_trade_no,String trade_no,String payMethod){
		GameDetailExample gameDetailExample = new GameDetailExample();
		gameDetailExample.createCriteria().andTradeNoEqualTo(trade_no);
		List<GameDetail> gdList = gameDetailService.selectByExample(gameDetailExample);
		if(gdList.size() > 0){
			logger.info("支付宝已回调");
		}else{
			//更新支付信息
			GameDetail gd = gameDetailService.selectByPrimaryKey(out_trade_no);
			gd.setPayState(1);
			gd.setPayTime(new Date());
			gd.setTradeNo(trade_no);
			gd.setPayMethod(payMethod);
			gameDetailService.updateByPrimaryKey(gd);

			//改变比赛状态
			Game g = gameService.selectByPrimaryKey(gd.getGameId());
			GamePlace gamePlace = gamePlaceService.selectByPrimaryKey(g.getGamePlaceId());
			String time = DateUtil.dateToStr(g.getPlayTime());
			if(gd.getType() == 0){
				g.setState(1);
				//约战方通知
				GamePlayerExample example = new GamePlayerExample();
				example.createCriteria().andGameIdEqualTo(gd.getGameId()).andTypeEqualTo(0);
				List<GamePlayer> list = gamePlayerService.selectByExample(example);
				for(GamePlayer gp: list){
					Notify n = new Notify();
					n.setUserId(gp.getUserId());
					n.setTitle(g.getTheme() + "比赛通知");
					n.setIsRead(0);
					n.setContent("欢迎加入"+ g.getTheme() + "比赛！比赛时间：" + time+",比赛地点：" + gamePlace.getAddress() + gamePlace.getPlace());
					n.setCreateTime(new Date());
					n.setFriendId("0");
					n.setState(3);
					n.setRedirectType(1);
					notifyService.insert(n);
				}
			}
			if(gd.getType() == 1){
				g.setState(3);
				//约战方通知
				GamePlayerExample example = new GamePlayerExample();
				example.createCriteria().andGameIdEqualTo(gd.getGameId()).andTypeEqualTo(0);
				List<GamePlayer> list = gamePlayerService.selectByExample(example);
				for(GamePlayer gp: list){
					Notify n = new Notify();
					n.setUserId(gp.getUserId());
					n.setTitle(g.getTheme()+"比赛通知");
					n.setIsRead(0);
					n.setContent("比赛："+ g.getTheme() + "已被应战！");
					n.setCreateTime(new Date());
					n.setFriendId("0");
					n.setState(3);
					n.setRedirectType(1);
					notifyService.insert(n);
				}
				//应战方通知
				GamePlayerExample example2 = new GamePlayerExample();
				example2.createCriteria().andGameIdEqualTo(gd.getGameId()).andTypeEqualTo(1);
				List<GamePlayer> list2 = gamePlayerService.selectByExample(example2);
				for(GamePlayer gp: list2){
					Notify n = new Notify();
					n.setUserId(gp.getUserId());
					n.setTitle(g.getTheme() + "比赛通知");
					n.setIsRead(0);
					n.setContent("欢迎加入"+ g.getTheme() + "比赛！比赛时间：" + time+",比赛地点：" + gamePlace.getAddress() + gamePlace.getPlace());
					n.setCreateTime(new Date());
					n.setFriendId("0");
					n.setState(3);
					n.setRedirectType(1);
					notifyService.insert(n);
				}
			}
			gameService.updateByPrimaryKey(g);
		}

	}

}
