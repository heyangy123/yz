package com.pay.wx.pub;

import com.base.pay.wx.util.ConstantUtil;
import java.net.URLEncoder;

public class GetWeiXinCode
{
  public String GetCodeRequest = "https://open.weixin.qq.com/connect/oauth2/authorize?appid=APPID&redirect_uri=REDIRECT_URI&response_type=code&scope=SCOPE&state=STATE#wechat_redirect";

  public String getCodeRequest(String REDIRECT_URI, String SCOPE)
  {
    String result = null;

    this.GetCodeRequest = this.GetCodeRequest.replace("APPID", urlEnodeUTF8(ConstantUtil.WEB_APP_ID));

    this.GetCodeRequest = this.GetCodeRequest.replace("REDIRECT_URI", urlEnodeUTF8(REDIRECT_URI));

    this.GetCodeRequest = this.GetCodeRequest.replace("SCOPE", SCOPE);

    result = this.GetCodeRequest;

    return result;
  }

  public static String urlEnodeUTF8(String str)
  {
    String result = str;
    try
    {
      result = URLEncoder.encode(str, "UTF-8");
    }
    catch (Exception e)
    {
      e.printStackTrace();
    }

    return result;
  }
}