package com.pay.acp;

import com.base.pay.PayPropertySupport;

public class UpmpConfig
{
  public static String VERSION;
  public static String CHARSET;
  public static String TRADE_URL;
  public static String QUERY_URL;
  public static String MER_ID;
  public static String MER_BACK_END_URL;
  public static String MER_FRONT_END_URL;
  public static String MER_FRONT_WEB_URL;
  public static String SIGN_TYPE;
  public static String SECURITY_KEY;
  private static final String KEY_VERSION = "version";
  private static final String KEY_CHARSET = "charset";
  private static final String KEY_TRADE_URL = "upmp.trade.url";
  private static final String KEY_QUERY_URL = "upmp.query.url";
  private static final String KEY_MER_ID = "mer.id";
  private static final String KEY_MER_BACK_END_URL = "mer.back.end.url";
  private static final String KEY_MER_FRONT_END_URL = "mer.front.end.url";
  private static final String KEY_SIGN_METHOD = "sign.method";
  private static final String KEY_SECURITY_KEY = "security.key";
  public final String RESPONSE_CODE_SUCCESS = "00";

  public static String SIGNATURE = "signature";

  public static String SIGN_METHOD = "01";

  public static String RESPONSE_CODE = "respCode";

  public static String RESPONSE_MSG = "respMsg";

  static {
    try {
      VERSION = PayPropertySupport.getProperty("version");
      CHARSET = PayPropertySupport.getProperty("charset");
      TRADE_URL = PayPropertySupport.getProperty("upmp.trade.url");
      QUERY_URL = PayPropertySupport.getProperty("upmp.query.url");
      MER_ID = PayPropertySupport.getProperty("mer.id");
      MER_BACK_END_URL = PayPropertySupport.getProperty("mer.back.end.url");
      MER_FRONT_END_URL = PayPropertySupport.getProperty("mer.front.end.url");
      SIGN_TYPE = PayPropertySupport.getProperty("sign.method");
      SECURITY_KEY = PayPropertySupport.getProperty("security.key");
      SIGN_METHOD = PayPropertySupport.getProperty("sign.method");
    } catch (Exception e) {
      e.printStackTrace();
    }
  }
}