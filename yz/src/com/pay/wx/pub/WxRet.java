package com.pay.wx.pub;

public class WxRet
{
  private String access_token;
  private String refresh_token;
  private String openid;
  private String expires_in;

  public String getAccess_token()
  {
    return this.access_token;
  }
  public void setAccess_token(String access_token) {
    this.access_token = access_token;
  }
  public String getRefresh_token() {
    return this.refresh_token;
  }
  public void setRefresh_token(String refresh_token) {
    this.refresh_token = refresh_token;
  }
  public String getOpenid() {
    return this.openid;
  }
  public void setOpenid(String openid) {
    this.openid = openid;
  }
  public String getExpires_in() {
    return this.expires_in;
  }
  public void setExpires_in(String expires_in) {
    this.expires_in = expires_in;
  }
}