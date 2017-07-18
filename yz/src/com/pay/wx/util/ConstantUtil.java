package com.pay.wx.util;

import com.base.pay.PayPropertySupport;

public class ConstantUtil
{
	
	public static final String alipay = "alipay";
	public static final String wxpay = "wxpay";
	
  public static String APP_ID = PayPropertySupport.getProperty("wx.appid");
  public static String APP_SECRET = PayPropertySupport.getProperty("wx.secret");

  public static String APP_KEY = PayPropertySupport.getProperty("wx.key");
  public static String PARTNER = PayPropertySupport.getProperty("wx.partnerId");
  public static String PARTNER_KEY = PayPropertySupport.getProperty("wx.parterKey");

  public static String WEB_APP_ID = PayPropertySupport.getProperty("wx.webAppid");
  public static String WEB_SECRET = PayPropertySupport.getProperty("wx.webSecret");
  public static String WEB_PARTNER = PayPropertySupport.getProperty("wx.webPartnerId");

  public static String TOKENURL = "https://api.weixin.qq.com/cgi-bin/token";
  public static String GRANT_TYPE = "client_credential";
  public static String EXPIRE_ERRCODE = "42001";
  public static String FAIL_ERRCODE = "40001";
  public static String GATEURL = "https://api.weixin.qq.com/pay/genprepay?access_token=";
  public static String ACCESS_TOKEN = "access_token";
  public static String ERRORCODE = "errcode";
  public static String SIGN_METHOD = "sha1";

  public static String BACK_URL = PayPropertySupport.getProperty("wx.backUrl");
  public static String FRONT_URL = PayPropertySupport.getProperty("wx.frontUrl");

  public static String packageValue = "bank_type=WX&body=%B2%E2%CA%D4&fee_type=1&input_charset=GBK&notify_url=http%3A%2F%2F127.0.0.1%3A8180%2Ftenpay_api_b2c%2FpayNotifyUrl.jsp&out_trade_no=2051571832&partner=1900000109&sign=10DA99BCB3F63EF23E4981B331B0A3EF&spbill_create_ip=127.0.0.1&time_expire=20131222091010&total_fee=1";
  public static String traceid = "testtraceid001";

  public static String ACCESS_TOKEN_URL = "https://api.weixin.qq.com/sns/oauth2/access_token";
}