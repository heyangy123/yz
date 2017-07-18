package com.pay.util;

import com.pay.PayPropertySupport;
import com.pay.ali.AlipayConfig;


public class AliPayConfigEx extends AlipayConfig{
	//团购订单通知
	public static String NOTIFY_URL_TG=PayPropertySupport.getProperty("pay.activity.ali.notifyUrl");
	//团购订单app返回地址
	public static String RETURN_URL_TG_APP=PayPropertySupport.getProperty("pay.activity.ali.returnUrl");
	//团购订单web返回地址
	public static String RETURN_URL_TG_WEB=PayPropertySupport.getProperty("pay.activity.ali.returnUrlWeb");
	
	//团购订单通知
	public static String NOTIFY_URL_CAR=PayPropertySupport.getProperty("pay.ali.notifyUrl");
	//团购订单app返回地址
	public static String RETURN_URL_CAR_APP=PayPropertySupport.getProperty("pay.ali.returnUrl");
	//团购订单web返回地址
	public static String RETURN_URL_CAR_WEB=PayPropertySupport.getProperty("pay.ali.returnUrlWeb");
	
	
	
}
