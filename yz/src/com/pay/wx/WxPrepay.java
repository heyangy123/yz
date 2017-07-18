package com.pay.wx;

import com.alibaba.fastjson.JSONObject;
import com.base.util.JSONUtils;
import com.pay.method.MFramePayType;
import com.pay.method.MPayType;
import com.pay.method.PayMethod;
import com.pay.wx.client.TenpayHttpClient;
import com.pay.wx.model.WxRefundQuery;
import com.pay.wx.model.WxRefundReturn;
import com.pay.wx.util.ConstantUtil;
import com.pay.wx.util.MD5Util;
import com.pay.wx.util.WXUtil;
import com.pay.wx.util.XMLUtil;

import java.io.PrintStream;
import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.SortedMap;
import java.util.TreeMap;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;

public class WxPrepay
{
  private static TenpayHttpClient client = new TenpayHttpClient();
  private static final String unifiedorder = "https://api.mch.weixin.qq.com/pay/unifiedorder";
  private static final String refundorder = "https://api.mch.weixin.qq.com/secapi/pay/refund";
  private static final String refundquery = "https://api.mch.weixin.qq.com/pay/refundquery";
  private static String[] WAP_PARAMS = { "appid", "noncestr", "package", "prepayid", "sign", "timestamp" };
  
  public static String getPrepayId(SortedMap<String, String> params)
  {
    try
    {
      String result = "";
      if (client.callHttpPost("https://api.mch.weixin.qq.com/pay/unifiedorder", getXml(params)))
      {
        result = client.getResContent();
        System.out.println(result);
        Map map = XMLUtil.doXMLParse(result);
        return (String)map.get("prepay_id");
      }
    }
    catch (Exception localException) {}
    return null;
  }
  
  public static Map getPrepayApp(SortedMap<String, String> params)
  {
    try
    {
      String result = "";
      if (client.callHttpPost("https://api.mch.weixin.qq.com/pay/unifiedorder", getXml(params)))
      {
        result = client.getResContent();
        System.out.println(result);
        Map map = XMLUtil.doXMLParse(result);
        return map;
      }
    }
    catch (Exception localException) {}
    return null;
  }
  
  private static String getCodeUrl(SortedMap<String, String> params)
  {
    try
    {
      String result = "";
      if (client.callHttpPost("https://api.mch.weixin.qq.com/pay/unifiedorder", getXml(params)))
      {
        result = client.getResContent();
        System.out.println(result);
        Map map = XMLUtil.doXMLParse(result);
        return (String)map.get("code_url");
      }
    }
    catch (Exception localException) {}
    return null;
  }
  
  private static WxRefundReturn refundReturn(SortedMap<String, String> params)
  {
    try
    {
      String result = "";
      if (client.callHttpPost("https://api.mch.weixin.qq.com/secapi/pay/refund", getXml(params)))
      {
        result = client.getResContent();
        
        Map map = XMLUtil.doXMLParse(result);
        return (WxRefundReturn)JSONUtils.deserialize(JSONUtils.serialize(map), WxRefundReturn.class);
      }
    }
    catch (Exception e)
    {
      e.printStackTrace();
    }
    return null;
  }
  
  private static WxRefundQuery refundQuery(SortedMap<String, String> params)
  {
    try
    {
      String result = "";
      if (client.callHttpPost("https://api.mch.weixin.qq.com/pay/refundquery", getXml(params)))
      {
        result = client.getResContent();
        System.out.println(result);
        Map map = XMLUtil.doXMLParse(result);
        return (WxRefundQuery)JSONUtils.deserialize(JSONUtils.serialize(map), WxRefundQuery.class);
      }
    }
    catch (Exception e)
    {
      e.printStackTrace();
    }
    return null;
  }
  
  private static String getXml(SortedMap<String, String> params)
  {
    String sign = createSign(params);
    params.remove("key");
    params.put("sign", sign);
    StringBuffer sb = new StringBuffer();
    sb.append("<xml>");
    Set set = params.entrySet();
    Iterator it = set.iterator();
    while (it.hasNext())
    {
      Map.Entry object = (Map.Entry)it.next();
      String k = (String)object.getKey();
      String v = (String)object.getValue();
      if (("attach".equalsIgnoreCase(k)) || ("body".equalsIgnoreCase(k)) || ("sign".equalsIgnoreCase(k))) {
        sb.append("<" + k + ">" + "<![CDATA[" + v + "]]></" + k + ">");
      } else {
        sb.append("<" + k + ">" + v + "</" + k + ">");
      }
    }
    sb.append("</xml>");
    return sb.toString();
  }
  
  public static String createSign(SortedMap<String, String> params)
  {
    StringBuffer sb = new StringBuffer();
    Set es = params.entrySet();
    Iterator it = es.iterator();
    while (it.hasNext())
    {
      Map.Entry entry = (Map.Entry)it.next();
      String k = (String)entry.getKey();
      String v = (String)entry.getValue();
      if ((v != null) && (!"".equals(v)) && 
        (!"sign".equals(k)) && (!"key".equals(k))) {
        sb.append(k + "=" + v + "&");
      }
    }
    sb.append("key=" + (String)params.get("key"));
    
    String sign = MD5Util.MD5Encode(sb.toString(), "utf-8").toUpperCase();
    
    return sign;
  }
  
  public static Map wxAppPrepareId(BigDecimal price, String orderId, String info, String ip)
  {
  SortedMap<String, String> params =new TreeMap();
    params.put("body", info); 
    params.put("nonce_str", WXUtil.getNonceStr());
    params.put("out_trade_no", orderId);
    params.put("total_fee", String.valueOf(price.multiply(new BigDecimal(100)).intValue()));
    params.put("spbill_create_ip", "8.8.8.8");
    params.put("notify_url", ConstantUtil.BACK_URL);
    params.put("trade_type", "APP");
    params.put("mch_id", ConstantUtil.PARTNER);
    params.put("appid", ConstantUtil.APP_ID);
    params.put("key", ConstantUtil.APP_KEY);
    params.put("sign", createSign(params));
    SortedMap<String,String> map =  new TreeMap();
    
    Map mapStr = getPrepayApp(params);
    
    map.put("key", ConstantUtil.APP_KEY);
    map.put("timestamp",  WXUtil.getTimeStamp());
    map.put("package","Sign=WXPay");
    map.put("noncestr", (String)mapStr.get("nonce_str"));
    map.put("partnerid", ConstantUtil.PARTNER);
    map.put("appid", ConstantUtil.APP_ID);
    map.put("prepayid",(String)mapStr.get("prepay_id"));
    map.put("sign",createSign(map));
    return map;
  }
  
  public static boolean isValiSign(SortedMap<String, String> params)
  {
    String sign = ((String)params.get("sign")).toUpperCase();
    return createSign(params).equals(sign);
  }
  
  public static String wxPrepareId(BigDecimal price, String orderId, String info, String ip, String[] backUrl)
  {
    return wxPrepareId(price, orderId, info, ip, MFramePayType.NORMAL_BUY, backUrl);
  }
  
  public static String wxPrepareId(BigDecimal price, String orderId, String info, String ip, MPayType pay, String[] backUrl)
  {
    SortedMap params = new TreeMap();
    params.put("body", info);
    params.put("out_trade_no", orderId);
    params.put("total_fee", Integer.valueOf(price.multiply(new BigDecimal(100)).intValue()));
    params.put("spbill_create_ip", ip);
    params.put("key", ConstantUtil.APP_KEY);
    params.put("attach", pay.toString());
    if ((backUrl != null) && (backUrl.length > 0))
    {
      if (backUrl.length > 3) {
        params.put("key", backUrl[3]);
      }
      if (backUrl.length > 2) {
        params.put("mch_id", backUrl[2]);
      }
      if (backUrl.length > 1) {
        params.put("appid", backUrl[1]);
      }
      params.put("notify_url", PayMethod.urlToUrl(backUrl[0]));
    }
    return getPrepayId(wxPrepareId(params));
  }
  
  public static String wxCodeUrl(BigDecimal price, String orderId, String info, String ip, String[] backUrl)
  {
    return wxCodeUrl(price, orderId, info, ip, MFramePayType.NORMAL_BUY, backUrl);
  }
  
  public static String wxCodeUrl(BigDecimal price, String orderId, String info, String ip, MPayType pay, String[] backUrl)
  {
    SortedMap params = new TreeMap();
    params.put("body", info);
    params.put("out_trade_no", orderId);
    params.put("total_fee", Integer.valueOf(price.multiply(new BigDecimal(100)).intValue()));
    params.put("spbill_create_ip", ip);
    params.put("notify_url", PayMethod.urlToUrl(ConstantUtil.BACK_URL));
    params.put("trade_type", "NATIVE");
    params.put("product_id", UUID.randomUUID().toString().replace("-", ""));
    params.put("device_info", "web");
    params.put("key", ConstantUtil.APP_KEY);
    params.put("attach", pay.toString());
    if ((backUrl != null) && (backUrl.length > 0))
    {
      if (backUrl.length > 3) {
        params.put("key", backUrl[3]);
      }
      if (backUrl.length > 2) {
        params.put("mch_id", backUrl[2]);
      }
      if (backUrl.length > 1) {
        params.put("appid", backUrl[1]);
      }
      params.put("notify_url", PayMethod.urlToUrl(backUrl[0]));
    }
    return getCodeUrl(wxWebPrepareId(params));
  }
  
  public static String wxPayUrl(BigDecimal price, String orderId, String info, String ip, MPayType pay, String[] backUrl)
  {
    SortedMap params = new TreeMap();
    params.put("body", info);
    params.put("out_trade_no", orderId);
    params.put("total_fee", Integer.valueOf(price.multiply(new BigDecimal(100)).intValue()));
    params.put("spbill_create_ip", ip);
    params.put("notify_url", PayMethod.urlToUrl(ConstantUtil.BACK_URL));
    params.put("trade_type", "WAP");
    params.put("product_id", UUID.randomUUID().toString().replace("-", ""));
    params.put("device_info", "wap");
    params.put("key", ConstantUtil.APP_KEY);
    params.put("attach", pay.toString());
    if ((backUrl != null) && (backUrl.length > 0))
    {
      if (backUrl.length > 3) {
        params.put("key", backUrl[3]);
      }
      if (backUrl.length > 2) {
        params.put("mch_id", backUrl[2]);
      }
      if (backUrl.length > 1) {
        params.put("appid", backUrl[1]);
      }
      params.put("notify_url", PayMethod.urlToUrl(backUrl[0]));
    }
    params = wxWebPrepareId(params);
    String prePayId = getPrepayId(params);
    String ret = null;
    if (prePayId != null)
    {
      params.put("package", "WAP");
      params.put("prepayid", prePayId);
      try
      {
        ret = getWapPayUrl(params);
        ret = "weixin：//wap/pay?" + URLEncoder.encode(ret, "utf-8");
      }
      catch (Exception localException) {}
    }
    return ret;
  }
  
  public static String wxPayUrl(BigDecimal price, String orderId, String info, String ip, String[] backUrl)
  {
    return wxPayUrl(price, orderId, info, ip, MFramePayType.NORMAL_BUY, backUrl);
  }
  
  public static String getWapPayUrl(SortedMap<String, String> params)
    throws UnsupportedEncodingException
  {
    StringBuffer sb = new StringBuffer();
    Set es = params.entrySet();
    Iterator it = es.iterator();
    while (it.hasNext())
    {
      Map.Entry entry = (Map.Entry)it.next();
      String k = (String)entry.getKey();
      String v = (String)entry.getValue();
      if (hasWapKey(k)) {
        sb.append(k + "=" + URLEncoder.encode(v, "utf-8") + "&");
      }
    }
    return sb.substring(0, sb.length() - 1);
  }
  
  private static boolean hasWapKey(String k)
  {
    for (String str : WAP_PARAMS) {
      if (str.equals(k)) {
        return true;
      }
    }
    return false;
  }
  
  public static String wxJsPrepareId(BigDecimal price, String orderId, String info, String ip, String code)
  {
    SortedMap params = new TreeMap();
    params.put("body", info);
    params.put("out_trade_no", orderId);
    params.put("total_fee", Integer.valueOf(price.multiply(new BigDecimal(100)).intValue()));
    params.put("spbill_create_ip", ip);
    params.put("trade_type", "JSAPI");
    params.put("openid", getOpenid(code));
    return getPrepayId(wxWebPrepareId(params));
  }
  
  public static void main(String[] args)
    throws Exception
  {
    System.out.println(wxCodeUrl(new BigDecimal(0.01D), "12315326534", "测试", "8.8.8.8", new String[0]));
  }
  
  public static SortedMap<String, String> wxPrepareId(Map<String, String> map)
  {
    SortedMap params = new TreeMap();
    params.put("appid", ConstantUtil.APP_ID);
    params.put("mch_id", ConstantUtil.PARTNER);
    params.put("nonce_str", WXUtil.getNonceStr());
    params.put("body", "微信订单");
    params.put("out_trade_no", WXUtil.getTimeStamp());
    params.put("total_fee", "0.01");
    params.put("spbill_create_ip", "8.8.8.8");
    params.put("notify_url", PayMethod.urlToUrl(ConstantUtil.BACK_URL));
    params.put("trade_type", "APP");
    params.putAll(map);
    return params;
  }
  
  public static String wxWapCodeUrl(BigDecimal price, String orderId, String info, String ip, String openId, MPayType pay, String[] backUrl)
  {
    SortedMap params = new TreeMap();
    params.put("body", info);
    params.put("out_trade_no", orderId);
    params.put("total_fee", Integer.valueOf(price.multiply(new BigDecimal(100)).intValue()));
    params.put("spbill_create_ip", ip);
    params.put("notify_url", PayMethod.urlToUrl(ConstantUtil.BACK_URL));
    params.put("trade_type", "JSAPI");
    params.put("product_id", UUID.randomUUID().toString().replace("-", ""));
    params.put("device_info", "web");
    params.put("openid", openId);
    params.put("key", ConstantUtil.APP_KEY);
    params.put("attach", pay.toString());
    if ((backUrl != null) && (backUrl.length > 0))
    {
      if (backUrl.length > 3) {
        params.put("key", backUrl[3]);
      }
      if (backUrl.length > 2) {
        params.put("mch_id", backUrl[2]);
      }
      if (backUrl.length > 1) {
        params.put("appid", backUrl[1]);
      }
      params.put("notify_url", PayMethod.urlToUrl(backUrl[0]));
    }
    params = wxWebPrepareId(params);
    return getPrepayId(params);
  }
  
  public static SortedMap<String, String> wxWebPrepareId(Map<String, String> map)
  {
    SortedMap params = new TreeMap();
    params.put("appid", ConstantUtil.WEB_APP_ID);
    params.put("mch_id", ConstantUtil.WEB_PARTNER);
    params.put("nonce_str", WXUtil.getNonceStr());
    params.put("body", "微信订单");
    params.put("out_trade_no", WXUtil.getTimeStamp());
    params.put("total_fee", "0.01");
    params.put("spbill_create_ip", "8.8.8.8");
    params.put("notify_url", PayMethod.urlToUrl(ConstantUtil.BACK_URL));
    params.put("trade_type", "WAP");
    params.putAll(map);
    return params;
  }
  
  private static String getOpenid(Map<String, String> params)
  {
    try
    {
      String result = "";
      if (client.callHttpPost(ConstantUtil.ACCESS_TOKEN_URL, getJsonUrl(params)))
      {
        result = client.getResContent();
        System.out.println(result);
        JSONObject map = JSONObject.parseObject(result);
        return map.getString("openid");
      }
    }
    catch (Exception localException) {}
    return null;
  }
  
  public static String getOpenid(String code)
  {
    Map params = new HashMap();
    params.put("appid", ConstantUtil.WEB_APP_ID);
    params.put("secret", ConstantUtil.WEB_PARTNER);
    params.put("code", code);
    params.put("grant_type", "authorization_code");
    return getOpenid(params);
  }
  
  private static String getJsonUrl(Map<String, String> params)
  {
    StringBuffer sb = new StringBuffer();
    Set es = params.entrySet();
    Iterator it = es.iterator();
    while (it.hasNext())
    {
      Map.Entry entry = (Map.Entry)it.next();
      String k = (String)entry.getKey();
      String v = (String)entry.getValue();
      sb.append(k + "=" + v + "&");
    }
    return sb.toString();
  }
  
  public static SortedMap<String, String> refundMap(Map<String, String> map)
  {
    SortedMap params = new TreeMap();
    params.put("appid", ConstantUtil.WEB_APP_ID);
    params.put("mch_id", ConstantUtil.WEB_PARTNER);
    params.put("nonce_str", WXUtil.getNonceStr());
    params.put("total_fee", "1");
    params.put("refund_fee", "1");
    params.put("op_user_id", (String)params.get("mch_id"));
    params.putAll(map);
    return params;
  }
  
  public static SortedMap<String, String> refundQueryMap(Map<String, String> map)
  {
    SortedMap params = new TreeMap();
    params.put("appid", ConstantUtil.WEB_APP_ID);
    params.put("mch_id", ConstantUtil.WEB_PARTNER);
    params.put("nonce_str", WXUtil.getNonceStr());
    params.putAll(map);
    return params;
  }
  
  public static WxRefundReturn refund(String orderId, String refundId, BigDecimal total, BigDecimal refund, String appId, String partner, String key)
  {
    SortedMap params = new TreeMap();
    params.put("appid", appId);
    params.put("mch_id", partner);
    params.put("out_trade_no", orderId);
    params.put("out_refund_no", refundId);
    params.put("total_fee", Integer.valueOf(total.multiply(new BigDecimal(100)).intValue()));
    params.put("refund_fee", Integer.valueOf(refund.multiply(new BigDecimal(100)).intValue()));
    params.put("key", key);
    params = refundMap(params);
    return refundReturn(params);
  }
  
  public static WxRefundReturn refundByCode(String tradeNo, String refundId, BigDecimal total, BigDecimal refund, String appId, String partner, String key)
  {
    SortedMap params = new TreeMap();
    params.put("appid", appId);
    params.put("mch_id", partner);
    params.put("transaction_id", tradeNo);
    params.put("out_refund_no", refundId);
    params.put("total_fee", Integer.valueOf(total.multiply(new BigDecimal(100)).intValue()));
    params.put("refund_fee", Integer.valueOf(refund.multiply(new BigDecimal(100)).intValue()));
    params.put("key", key);
    params = refundMap(params);
    return refundReturn(params);
  }
  
  public static WxRefundQuery refundQuery(String order, Integer type, String appId, String partner, String key)
  {
    SortedMap params = new TreeMap();
    params.put("appid", appId);
    params.put("mch_id", partner);
    params.put("key", key);
    switch (type.intValue())
    {
    case 1: 
      params.put("transaction_id", order);
      break;
    case 2: 
      params.put("out_trade_no", order);
      break;
    case 3: 
      params.put("out_refund_no", order);
      break;
    case 4: 
      params.put("refund_id", order);
    }
    params = refundQueryMap(params);
    return refundQuery(params);
  }
}
