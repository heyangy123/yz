package com.pay.ali;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Map;

public class AlipayNotify
{
  private static final String HTTPS_VERIFY_URL = "https://mapi.alipay.com/gateway.do?service=notify_verify&";

  public static boolean verify(Map<String, String> params, String[] param)
  {
    String publicKey = AlipayConfig.ali_public_key;
    String partner = AlipayConfig.partner;
    if (param.length > 0) {
      publicKey = param[0];
    }
    if (param.length > 1) {
      partner = param[1];
    }

    String responseTxt = "true";
    if (params.get("notify_id") != null) {
      String notify_id = (String)params.get("notify_id");
      responseTxt = verifyResponse(notify_id, new String[] { partner });
    }
    String sign = "";
    if (params.get("sign") != null) {
      sign = (String)params.get("sign");
    }
    boolean isSign = getSignVeryfy(params, sign, new String[] { publicKey });

    if ((isSign) && (responseTxt.equals("true"))) {
      return true;
    }
    return false;
  }
  
  public static boolean verify(Map<String, String> params)
  {
    String publicKey = AlipayConfig.ali_public_key;
    String partner = AlipayConfig.partner;
    return verify(params,new String[]{publicKey,partner});
  }

  private static boolean getSignVeryfy(Map<String, String> Params, String sign, String[] publicKey)
  {
    Map sParaNew = AlipayCore.paraFilter(Params);

    String preSignStr = AlipayCore.createLinkString(sParaNew);

    boolean isSign = false;

    String key = AlipayConfig.ali_public_key;
    if (publicKey.length > 0) {
      key = publicKey[0];
    }

    if (AlipayConfig.sign_type.equals("RSA")) {
      isSign = RSA.verify(preSignStr, sign, key, 
        AlipayConfig.input_charset);
    }
    return isSign;
  }

  private static String verifyResponse(String notify_id, String[] param)
  {
    String partner = AlipayConfig.partner;
    if (param.length > 0) {
      partner = param[1];
    }

    String veryfy_url = "https://mapi.alipay.com/gateway.do?service=notify_verify&partner=" + partner + 
      "&notify_id=" + notify_id;
    return checkUrl(veryfy_url);
  }

  public static void main(String[] args)
  {
    String n = "body=订单号：140513091151734&buyer_email=576462028@qq.com&buyer_id=2088202631744809&discount=0.00&gmt_close=2014-05-26 16:54:37&gmt_create=2014-05-26 16:54:36&gmt_payment=2014-05-26 16:54:37&is_total_fee_adjust=N&notify_id=dc32b8abb7bc58c705d22cdc11380d5f6g&notify_time=2014-05-26 16:54:37&notify_type=trade_status_sync&out_trade_no=8b9d2127-da3b-11e3-9fc1-74d4357db269&payment_type=1&price=0.01&quantity=1&seller_email=udowscrmadmin@163.com&seller_id=2088111040781043&subject=订单号：140513091151734&total_fee=0.01&trade_no=2014052639560280&trade_status=TRADE_FINISHED&use_coupon=N";
    String sign = "hynGdKyIrKqTUwFy986A7KIydbDvWvB2VzDDtQ7qWC7U3p7n7lav3J13QosFgzKarxxcPu+c5WlNfkZw94m7Ik2uFPmojrJ5C+F7lfzD4JFxKLQ4Xht8CjOhdnBFHlSc3LuQlNPGUrQ/w8oDNDFVJO8h9VbHqhrn2w+uctk749E=";
    String key = "MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQCpWYDVbBsdgL43h9cO/qjWA8d94XPCPTQM7vCGj11qtaCp8vRbTIaZSK3VqTLEmcES8jWfSz7oViB0IpupgwzRhGp0aQAvBIkAqCNYY2KXr1ssq7OY0v+bRK9zMWhLtI7YbLWIChtVlNs4s5pXmy9IKVYrBsjQ6XAl6JspDytZvQIDAQAB";
    System.out.println(RSA.verify(n, sign, key, "utf-8"));
  }

  private static String checkUrl(String urlvalue)
  {
    String inputLine = "";
    try
    {
      URL url = new URL(urlvalue);
      HttpURLConnection urlConnection = (HttpURLConnection)url
        .openConnection();
      BufferedReader in = new BufferedReader(new InputStreamReader(
        urlConnection.getInputStream()));
      inputLine = in.readLine().toString();
    } catch (Exception e) {
      e.printStackTrace();
      inputLine = "";
    }

    return inputLine;
  }

  public static boolean verifyMobileNotify(Map<String, String> params, String payKey)
  {
    String sign = "";

    if (params.get("sign") != null) {
      sign = (String)params.get("sign");
    }

    Map sParaNew = AlipayCore.paraFilter(params);

    String preSignStr = AlipayCore.createLinkString(sParaNew);

    boolean isSign = false;
    isSign = RSA.verify(preSignStr, sign, payKey, "utf-8");
    if (isSign) {
      return true;
    }
    String sWord = "isSign=" + isSign + "\n 返回回来的参数：" + preSignStr;
    AlipayCore.logResult(sWord);
    return false;
  }
}