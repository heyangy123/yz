package com.pay.method;

import java.math.BigDecimal;
import java.util.Map;

import com.pay.PayPropertySupport;
import com.pay.acp.UpmpBase;
import com.pay.acp.UpmpConfig;
import com.pay.ali.AlipayConfig;
import com.pay.ali.AlipaySubmit;
import com.pay.util.MPayPlatform;
import com.pay.wx.WxPrepay;
import com.pay.wx.util.ConstantUtil;

public class PayMethod
{
  public static String wapiFrame(String url)
  {
    String ret = "<!DOCTYPE html ><head><meta http-equiv='Content-Type' content='text/html; charset=utf-8' /><meta name='viewport' content='width=device-width, initial-scale=1.0, maximum-scale=1.0, user-scalable=0'>";
    ret = ret + "<title>支付宝支付</title><body style='margin:0;padding:0;' >";
    ret = ret + "<iframe src='" + url + "' style='width:100%;margin:0;padding:0;' scrolling='no' frameborder='0'  onload='this.height=this.contentWindow.document.documentElement.scrollHeight'  > " + "</iframe></body></html>";
    return ret;
  }
  
  public static String aliAppPay(String orderNo, BigDecimal totalPrice) throws Exception
  {
    return AlipaySubmit.AppPay(orderNo, totalPrice);
  }

  public static String aliPay(String orderId, BigDecimal total, String info, MPayPlatform payType, String back, String front, String partnerId, String sellderId, String payKey, int type, MPayType pay)
  {
    return AlipaySubmit.webPay(orderId, total, info, payType, back, front, partnerId, sellderId, payKey, type, pay);
  }

  public static String aliPay(String orderId, BigDecimal total, String info, MPayPlatform payType, String back, String front, String partnerId, String sellderId, String payKey, int type)
  {
    return aliPay(orderId, total, info, payType, back, front, partnerId, sellderId, payKey, type, MFramePayType.NORMAL_BUY);
  }

  public static String aliPay(String orderId, BigDecimal total, String info, String front, MPayPlatform payType, int type, MPayType pay)
  {
	  return aliPay(orderId, total, info, payType, AlipayConfig.notify_url, front, AlipayConfig.partner, AlipayConfig.seller_id, AlipayConfig.private_key, type, pay);
  }

  public static String aliPay(String orderId, BigDecimal total, String info, String front, MPayPlatform payType, int type)
  {
    return aliPay(orderId, total, info, front, payType, type, MFramePayType.NORMAL_BUY);
  }

  public static String aliPay(String orderId, BigDecimal total, String info, String back, String front, MPayPlatform payType, int type, MPayType pay)
  {
    return aliPay(orderId, total, info, payType, back, front, AlipayConfig.partner, AlipayConfig.seller_id, AlipayConfig.private_key, type, pay);
  }

  public static String aliWebPay(String orderId, BigDecimal total, String info, String front)
  {
    return aliWebPay(orderId, total, info, front, MFramePayType.NORMAL_BUY);
  }

  public static String aliWebPay(String orderId, BigDecimal total, String info, String front, MPayType pay)
  {
    return aliWebPay(orderId, total, info, front, AlipayConfig.notify_url, pay);
  }

  public static String aliWebPay(String orderId, BigDecimal total, String info, String back, String front, MPayType pay)
  {
    return aliPay(orderId, total, info, back, front, MPayPlatform.WEB , 1, pay);
  }

  public static String aliWapPay(String orderId, BigDecimal total, String info, String front, MPayType pay)
  {
    return aliWapPay(orderId, total, info, AlipayConfig.notify_url, front, pay);
  }

  public static String aliWapPay(String orderId, BigDecimal total, String info, MPayType pay)
  {
    return aliWapPay(orderId, total, info, AlipayConfig.return_url, pay);
  }

  public static String aliWapPay(String orderId, BigDecimal total, String info)
  {
    return aliWapPay(orderId, total, info, MFramePayType.NORMAL_BUY);
  }

  public static String aliWapPay(String orderId, BigDecimal total, String info, String back, String front, MPayType pay)
  {
    return aliPay(orderId, total, info, back, front, MPayPlatform.WAP, 1, pay);
  }

  public static String aliWapPayUrl(String orderId, BigDecimal total, String info, MPayType pay)
  {
    return aliWapPayUrl(orderId, total, info, AlipayConfig.return_url, pay);
  }

  public static String aliWapPayUrl(String orderId, BigDecimal total, String info)
  {
    return aliWapPayUrl(orderId, total, info, MFramePayType.NORMAL_BUY);
  }

  public static String aliWapPayUrl(String orderId, BigDecimal total, String info, String front, MPayType pay)
  {
    return aliWapPayUrl(orderId, total, info, AlipayConfig.notify_url, front, pay);
  }

  public static String aliWapPayUrl(String orderId, BigDecimal total, String info, String back, String front, MPayType pay)
  {
    return aliPay(orderId, total, info, back, front, MPayPlatform.WAP, 2, pay);
  }

  public static String aliWxWapPayUrl(String orderId, BigDecimal total, String info, String back, String front, MPayType pay)
  {
    return wapiFrame(aliWapPayUrl(orderId, total, info, back, front, pay));
  }

  public static String aliWxWapPayUrl(String orderId, BigDecimal total, String info, String back, String front)
  {
    return aliWxWapPayUrl(orderId, total, info, back, front, MFramePayType.NORMAL_BUY);
  }

  public static String aliWxWapPayUrl(String orderId, BigDecimal total, String info)
  {
    return aliWxWapPayUrl(orderId, total, info, AlipayConfig.return_url, AlipayConfig.notify_url);
  }

  public static String upmpAppPay(BigDecimal price, String orderId)
  {
    return upmpAppPay(price, orderId, MFramePayType.NORMAL_BUY);
  }

  public static String upmpAppPay(BigDecimal price, String orderId, String back)
  {
    return upmpAppPay(price, orderId, MFramePayType.NORMAL_BUY, back);
  }

  public static String upmpAppPay(BigDecimal price, String orderId, MPayType pay, String back)
  {
    return UpmpBase.upmpPurchase(price, orderId, pay, new String[] { back });
  }

  public static String upmpAppPay(BigDecimal price, String orderId, MPayType pay)
  {
    return upmpAppPay(price, orderId, pay, UpmpConfig.MER_BACK_END_URL);
  }

  public static String upmpWebPay(BigDecimal price, String orderId, MPayType pay)
  {
    return upmpWebPay(price, orderId, pay, UpmpConfig.MER_FRONT_END_URL, UpmpConfig.MER_BACK_END_URL);
  }

  public static String upmpWebPay(BigDecimal price, String orderId)
  {
    return upmpWebPay(price, orderId, MFramePayType.NORMAL_BUY);
  }

  public static String upmpWebPay(BigDecimal price, String orderId, String front, String back)
  {
    return upmpWebPay(price, orderId, MFramePayType.NORMAL_BUY, front, back);
  }

  public static String upmpWebPay(BigDecimal price, String orderId, MPayType pay, String front, String back)
  {
    return UpmpBase.upmpWebPurchase(price, orderId, pay, new String[] { front, back });
  }

  public static String wxPrepayId(BigDecimal price, String orderId, String info, String ip, MPayType pay)
  {
    return wxPrepayId(price, orderId, info, ip, pay, ConstantUtil.BACK_URL);
  }

  public static String wxPrepayId(BigDecimal price, String orderId, String info, MPayType pay)
  {
    return wxPrepayId(price, orderId, info, "8.8.8.8", pay);
  }
  
  public static Map wxAppPay(BigDecimal price, String orderId, String info,String ip)
  {
    return WxPrepay.wxAppPrepareId(price,  orderId,  info, ip);
  }

  public static String wxPrepayId(BigDecimal price, String orderId, String info)
  {
    return wxPrepayId(price, orderId, info, "8.8.8.8");
  }

  public static String wxPrepayId(BigDecimal price, String orderId, String info, String ip)
  {
    return wxPrepayId(price, orderId, info, ip, MFramePayType.NORMAL_BUY);
  }

  public static String wxPrepayId(BigDecimal price, String orderId, String info, String ip, MPayType pay, String backUrl)
  {
    return wxPrepayId(price, orderId, info, ip, pay, backUrl, ConstantUtil.APP_ID, ConstantUtil.PARTNER, ConstantUtil.APP_KEY);
  }

  public static String wxPrepayId(BigDecimal price, String orderId, String info, String ip, MPayType pay, String backUrl, String appid, String partnerId, String appkey)
  {
    return WxPrepay.wxPrepareId(price, orderId, info, ip, pay, new String[] { backUrl, appid, partnerId, appkey });
  }

  public static String wxJsPay(BigDecimal price, String orderId, String info, String ip, String openId, String backUrl, String appid, String partnerId, String appkey)
  {
    return wxJsPay(price, orderId, info, ip, openId, MFramePayType.NORMAL_BUY, backUrl, appid, partnerId, appkey);
  }

  public static String wxJsPay(BigDecimal price, String orderId, String info, String ip, String openId, MPayType pay, String backUrl, String appid, String partnerId, String appkey)
  {
    return WxPrepay.wxWapCodeUrl(price, orderId, info, ip, openId, pay, new String[] { backUrl, appid, partnerId, appkey });
  }

  public static String wxJsPay(BigDecimal price, String orderId, String info, String ip, String openId, MPayType pay, String backUrl)
  {
    return wxJsPay(price, orderId, info, ip, openId, pay, backUrl, ConstantUtil.WEB_APP_ID, ConstantUtil.WEB_PARTNER, ConstantUtil.APP_KEY);
  }

  public static String wxJsPay(BigDecimal price, String orderId, String info, String ip, String openId, MPayType pay)
  {
    return wxJsPay(price, orderId, info, ip, openId, pay, ConstantUtil.BACK_URL);
  }

  public static String wxJsPay(BigDecimal price, String orderId, String info, String ip, String openId)
  {
    return wxJsPay(price, orderId, info, ip, openId, MFramePayType.NORMAL_BUY);
  }

  public static String wxJsPay(BigDecimal price, String orderId, String info, String openId)
  {
    return wxJsPay(price, orderId, info, "8.8.8.8", openId);
  }

  public static String wxPayUrl(BigDecimal price, String orderId, String info, String ip, String backUrl, String appid, String partnerId, String appkey)
  {
    return wxPayUrl(price, orderId, info, ip, MFramePayType.NORMAL_BUY, backUrl, appid, partnerId, appkey);
  }

  public static String wxPayUrl(BigDecimal price, String orderId, String info, String ip, MPayType pay, String backUrl, String appid, String partnerId, String appkey)
  {
    return WxPrepay.wxCodeUrl(price, orderId, info, ip, pay, new String[] { backUrl, appid, partnerId, appkey });
  }

  public static String wxPayUrl(BigDecimal price, String orderId, String info, String ip, MPayType pay, String backUrl)
  {
    return wxPayUrl(price, orderId, info, ip, pay, backUrl, ConstantUtil.APP_ID, ConstantUtil.PARTNER, ConstantUtil.APP_KEY);
  }

  public static String wxPayUrl(BigDecimal price, String orderId, String info, String ip, MPayType pay)
  {
    return wxPayUrl(price, orderId, info, ip, pay, ConstantUtil.BACK_URL);
  }

  public static String wxPayUrl(BigDecimal price, String orderId, String info, MPayType pay)
  {
    return wxPayUrl(price, orderId, info, "8.8.8.8", pay, ConstantUtil.BACK_URL);
  }

  public static String wxPayUrl(BigDecimal price, String orderId, String info, String ip)
  {
    return wxPayUrl(price, orderId, info, ip, MFramePayType.NORMAL_BUY);
  }

  public static String wxPayUrl(BigDecimal price, String orderId, String info)
  {
    return wxPayUrl(price, orderId, info, "8.8.8.8");
  }

  public static String wxWapPayUrl(BigDecimal price, String orderId, String info, String ip, MPayType pay, String backUrl, String appid, String partnerId, String appkey)
  {
    return WxPrepay.wxPayUrl(price, orderId, info, ip, pay, new String[] { backUrl, appid, partnerId, appkey });
  }

  public static String wxWapPayUrl(BigDecimal price, String orderId, String info, String ip, String backUrl, String appid, String partnerId, String appkey)
  {
    return wxWapPayUrl(price, orderId, info, ip, MFramePayType.NORMAL_BUY, backUrl, appid, partnerId, appkey);
  }

  public static String wxWapPayUrl(BigDecimal price, String orderId, String info, String ip, MPayType pay, String backUrl)
  {
    return wxWapPayUrl(price, orderId, info, ip, pay, backUrl, ConstantUtil.WEB_APP_ID, ConstantUtil.WEB_PARTNER, ConstantUtil.APP_KEY);
  }

  public static String wxWapPayUrl(BigDecimal price, String orderId, String info, String ip, MPayType pay)
  {
    return wxWapPayUrl(price, orderId, info, ip, pay, ConstantUtil.BACK_URL);
  }

  public static String wxWapPayUrl(BigDecimal price, String orderId, String info, String ip)
  {
    return wxWapPayUrl(price, orderId, info, ip, MFramePayType.NORMAL_BUY);
  }

  public static String wxWapPayUrl(BigDecimal price, String orderId, String info)
  {
    return wxWapPayUrl(price, orderId, info, "8.8.8.8");
  }

  public static String urlToUrl(String url) {
    if (url == null) return null;
    if (url.indexOf("http") == 0) {
      return url;
    }
    return PayPropertySupport.getProperty("pay.url") + url;
  }
}