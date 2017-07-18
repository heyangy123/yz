package com.pay.wx.pub;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

import javax.net.ssl.HttpsURLConnection;

import com.base.pay.wx.util.ConstantUtil;
import com.google.gson.Gson;

public class WxUtil
{
  private static String OPENID_URL = "https://api.weixin.qq.com/sns/oauth2/access_token";

  public static WxRet code2openId(String openCode) throws Exception {
    WxRet ret = new WxRet();
    String result = getRetByCode(openCode);
    Gson json = new Gson();
    ret = (WxRet)json.fromJson(result, WxRet.class);
    return ret;
  }

  public static String getRetByCode(String openCode) throws Exception
  {
    String ret = "";
    Map params = new HashMap();
    params.put("grant_type", "authorization_code");
    params.put("code", openCode);

    params.put("secret", ConstantUtil.WEB_SECRET);
    params.put("appid", ConstantUtil.WEB_APP_ID);

    String u = OPENID_URL + "?" + WxUrlUtil.buildByMap(params);
    URL url = new URL(u);
    HttpsURLConnection connect = null;
    try {
      connect = (HttpsURLConnection)url.openConnection();
      connect.setDoInput(true);
      connect.setRequestMethod("POST");
      connect.connect();
      InputStream in = null;
      try {
        in = connect.getInputStream();
        ret = getStreamString(in);
        System.out.println(ret);
      }
      finally {
        if (in != null)
          in.close();
      }
    }
    finally {
      if (connect != null) {
        connect.disconnect();
      }
    }

    return ret;
  }

  public static String getStreamString(InputStream tInputStream) {
    if (tInputStream != null) {
      try {
        BufferedReader tBufferedReader = new BufferedReader(new InputStreamReader(tInputStream));
        StringBuffer tStringBuffer = new StringBuffer();
        String sTempOneLine = new String("");
        while ((sTempOneLine = tBufferedReader.readLine()) != null) {
          tStringBuffer.append(sTempOneLine);
        }
        return tStringBuffer.toString();
      } catch (Exception ex) {
        ex.printStackTrace();
      }
    }
    return null;
  }
}
