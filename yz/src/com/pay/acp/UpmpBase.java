package com.pay.acp;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import org.apache.commons.lang.StringUtils;

import com.pay.method.MFramePayType;
import com.pay.method.MPayType;
import com.pay.method.PayMethod;
import com.unionpay.acp.sdk.HttpClient;
import com.unionpay.acp.sdk.SDKConfig;
import com.unionpay.acp.sdk.SDKUtil;
import com.unionpay.acp.sdk.SecureUtil;

public class UpmpBase
{
  public static String encoding = "UTF-8";

  public static String version = "5.0.0";

  public static String frontUrl = "http://localhost:8080/ACPTest/acp_front_url.do";

  public static String backUrl = "http://localhost:8080/ACPTest/acp_back_url.do";

  public static String createHtml(String action, Map<String, String> hiddens)
  {
    StringBuffer sf = new StringBuffer();
    sf.append("<html><head><meta http-equiv=\"Content-Type\" content=\"text/html; charset=UTF-8\"/></head><body>");
    sf.append("<form id = \"pay_form\" action=\"" + action + 
      "\" method=\"post\">");
    if ((hiddens != null) && (hiddens.size() != 0)) {
      Set set = hiddens.entrySet();
      Iterator it = set.iterator();
      while (it.hasNext()) {
        Map.Entry ey = (Map.Entry)it.next();
        String key = (String)ey.getKey();
        String value = (String)ey.getValue();
        sf.append("<input type=\"hidden\" name=\"" + key + "\" id=\"" + 
          key + "\" value=\"" + value + "\"/>");
      }
    }
    sf.append("</form>");
    sf.append("</body>");
    sf.append("<script type=\"text/javascript\">");
    sf.append("document.all.pay_form.submit();");
    sf.append("</script>");
    sf.append("</html>");
    return sf.toString();
  }

  public static Map<String, String> signData(Map<String, ?> contentData)
  {
    Map.Entry obj = null;
    Map submitFromData = new HashMap();
    for (Iterator it = contentData.entrySet().iterator(); it.hasNext(); ) {
      obj = (Map.Entry)it.next();
      String value = (String)obj.getValue();
      if (StringUtils.isNotBlank(value))
      {
        submitFromData.put((String)obj.getKey(), value.trim());
      }

    }

    SDKUtil.sign(submitFromData, encoding);

    return submitFromData;
  }

  public static Map<String, String> submitUrl(Map<String, String> submitFromData, String requestUrl)
  {
    String resultString = "";

    HttpClient hc = new HttpClient(requestUrl, 30000, 30000);
    try {
      int status = hc.send(submitFromData, encoding);
      if (200 == status) {
        resultString = hc.getResult();
        System.out.println(resultString);
      }
    } catch (Exception e) {
      e.printStackTrace();
    }
    Map resData = new HashMap();

    if ((resultString != null) && (!"".equals(resultString)))
    {
      resData = SDKUtil.convertResultStringToMap(resultString);
      if (SDKUtil.validate(resData, encoding))
        System.out.println("验证签名成功");
      else {
        System.out.println("验证签名失败");
      }

    }

    return resData;
  }

  public static void deCodeFileContent(Map<String, String> resData)
  {
    String fileContent = (String)resData.get("fileContent");
    if ((fileContent != null) && (!"".equals(fileContent)))
      try {
        byte[] fileArray = SecureUtil.inflater(
          SecureUtil.base64Decode(fileContent.getBytes(encoding)));
        String root = "D:\\";
        String filePath = null;
        if (SDKUtil.isEmpty((String)resData.get("fileName")))
          filePath = root + File.separator + (String)resData.get("merId") + 
            "_" + (String)resData.get("batchNo") + "_" + 
            (String)resData.get("txnTime") + ".txt";
        else {
          filePath = root + File.separator + (String)resData.get("fileName");
        }
        File file = new File(filePath);
        if (file.exists()) {
          file.delete();
        }
        file.createNewFile();
        FileOutputStream out = new FileOutputStream(file);
        out.write(fileArray, 0, fileArray.length);
        out.flush();
        out.close();
      }
      catch (UnsupportedEncodingException e) {
        e.printStackTrace();
      } catch (IOException e) {
        e.printStackTrace();
      }
  }

  public static Map<String, String> submitDate(Map<String, ?> contentData, String requestUrl)
  {
    Map submitFromData = signData(contentData);

    return submitUrl(submitFromData, requestUrl);
  }

  public static String getCustomer(String encoding)
  {
    StringBuffer sf = new StringBuffer("{");

    String certifTp = "01";

    String certifId = "1301212386859081945";

    String customerNm = "测试";

    String phoneNo = "18613958987";

    String smsCode = "123311";

    String pin = "123213";

    String cvn2 = "400";

    String expired = "1212";
    sf.append("certifTp=" + certifTp + "&");
    sf.append("certifId=" + certifId + "&");
    sf.append("customerNm=" + customerNm + "&");
    sf.append("phoneNo=" + phoneNo + "&");
    sf.append("smsCode=" + smsCode + "&");

    sf.append("pin=" + SDKUtil.encryptPin("622188123456789", pin, encoding) + 
      "&");

    sf.append("cvn2=" + cvn2 + "&");

    sf.append("expired=" + expired);
    sf.append("}");
    String customerInfo = sf.toString();
    try {
      return new String(SecureUtil.base64Encode(sf.toString().getBytes(
        encoding)));
    } catch (UnsupportedEncodingException e) {
      e.printStackTrace();
    } catch (IOException e) {
      e.printStackTrace();
    }
    return customerInfo;
  }

  public static void main(String[] args) {
    Map data = new HashMap();
    String orderTime = new SimpleDateFormat("yyyyMMddHHmmss")
      .format(new Date());

    data.put("version", "5.0.0");

    data.put("encoding", "utf-8");

    data.put("signMethod", "01");

    data.put("txnType", "01");

    data.put("txnSubType", "01");

    data.put("bizType", "000201");

    data.put("channelType", "07");

    data.put("backUrl", UpmpConfig.MER_BACK_END_URL);
    data.put("frontUrl", UpmpConfig.MER_FRONT_END_URL);

    data.put("accessType", "0");

    data.put("merId", "802360048160506");

    data.put("orderId", "1231231231231231133");

    data.put("txnTime", orderTime);

    data.put("txnAmt", "1");

    data.put("currencyCode", "156");
    Map submitFromData = signData(data);
    String requestAppUrl = SDKConfig.getConfig().getAppRequestUrl();

    System.out.println("------------");

    System.out.println(submitDada(submitFromData, 1));
  }

  public static String upmpRefund(String orderId, String trade_no)
  {
    Map data = new HashMap();
    data.put("orderId", orderId);
    data.put("queryId", trade_no);
    try {
      return upmpRefund(data);
    } catch (Exception localException) {
    }
    return null;
  }

  public static String upmpPurchase(BigDecimal price, String orderId)
  {
    return upmpPurchase(price, orderId);
  }

  public static String upmpPurchase(BigDecimal price, String orderId, String[] url)
  {
    return upmpPurchase(price, orderId, MFramePayType.NORMAL_BUY, url);
  }

  public static String upmpPurchase(BigDecimal price, String orderId, MPayType pay, String[] url)
  {
    Map data = new HashMap();
    data.put("orderId", orderId);
    data.put("txnAmt", price.multiply(new BigDecimal(100)).intValue());
    data.put("backUrl", PayMethod.urlToUrl(UpmpConfig.MER_BACK_END_URL));
    data.put("reqReserved", pay.toString());
    if ((url != null) && 
      (url.length == 1)) {
      data.put("backUrl", PayMethod.urlToUrl(url[0]));
    }

    return upmpAppPurchase(data);
  }

  public static String upmpWebPurchase(BigDecimal price, String orderId, String[] url)
  {
    return upmpWebPurchase(price, orderId, MFramePayType.NORMAL_BUY, url);
  }

  public static String upmpWebPurchase(BigDecimal price, String orderId, MPayType type, String[] url)
  {
    Map data = new HashMap();
    data.put("orderId", orderId);
    data.put("txnAmt", price.multiply(new BigDecimal(100)).intValue());
    data.put("frontUrl", PayMethod.urlToUrl(UpmpConfig.MER_FRONT_END_URL));
    data.put("backUrl", PayMethod.urlToUrl(UpmpConfig.MER_BACK_END_URL));
    data.put("reqReserved", type.toString());
    if (url != null) {
      if (url.length == 1) {
        data.put("frontUrl", PayMethod.urlToUrl(url[0]));
      }
      if (url.length == 2) {
        data.put("backUrl", PayMethod.urlToUrl(url[1]));
      }
    }
    return upmpWebPurchase(data);
  }

  public static String upmpAppPurchase(Map<String, String> params)
  {
    return upmpPurchase(params, Integer.valueOf(1));
  }

  public static String upmpWebPurchase(Map<String, String> params)
  {
    return upmpPurchase(params, Integer.valueOf(2));
  }

  private static String upmpPurchase(Map<String, String> map, Integer type)
  {
    String orderTime = new SimpleDateFormat("yyyyMMddHHmmss")
      .format(new Date());

    Map data = new HashMap();

    data.put("version", UpmpConfig.VERSION);

    data.put("encoding", UpmpConfig.CHARSET);

    data.put("signMethod", UpmpConfig.SIGN_METHOD);

    data.put("txnType", "01");

    data.put("txnSubType", "01");

    data.put("bizType", "000201");

    data.put("channelType", "07");

    data.put("backUrl", PayMethod.urlToUrl(UpmpConfig.MER_BACK_END_URL));

    data.put("accessType", "0");

    data.put("merId", UpmpConfig.MER_ID);

    data.put("orderId", orderTime);

    data.put("txnTime", orderTime);

    data.put("txnAmt", "1");

    data.put("currencyCode", "156");

    data.putAll(map);

    Map submitFromData = signData(data);

    return submitDada(submitFromData, type.intValue());
  }

  public static String getCurrentTime()
  {
    return new SimpleDateFormat("yyyyMMddHHmmss")
      .format(new Date());
  }

  private static String upmpRefund(Map<String, String> map) throws InterruptedException {
    Map resmap = upmpRefundQuery(map);
    String respCode = (String)resmap.get("respCode");
    if (StringUtils.isNotBlank(respCode))
    {
      if (!"00".equals(respCode)) {
        System.out.println("处理不成功");
        return (String)resmap.get("respMsg");
      }

      Map contentData = new HashMap();

      contentData.put("version", UpmpConfig.VERSION);

      contentData.put("encoding", UpmpConfig.CHARSET);

      contentData.put("signMethod", UpmpConfig.SIGN_METHOD);

      contentData.put("txnType", "00");

      contentData.put("txnSubType", "00");

      contentData.put("bizType", "000000");

      contentData.put("accessType", "0");

      contentData.put("merId", resmap.get("merId"));

      contentData.put("txnTime", resmap.get("txnTime"));

      contentData.put("orderId", resmap.get("orderId"));

      Map queryResmap = submitDate(contentData, SDKConfig.getConfig().getBackRequestUrl());

      String origRespcode = (String)queryResmap.get("origRespCode");
      System.out.println("第==1==次查询返回 ： OrigRespcode:" + origRespcode + 
        ",RespCode:" + (String)queryResmap.get("RespCode"));

      if ((StringUtils.isNotBlank(origRespcode)) && 
        (origRespcode.substring(0, 2).equals("05"))) {
        int queryTimes = 5;
        for (int i = 1; i < queryTimes; i++) {
          Integer sleepTime = Integer.valueOf((int)Math.pow(2.0D, i));

          Thread.sleep(sleepTime.intValue() * 1000);

          System.out.println("发起第===" + (i + 1) + "===交易状查询");

          queryResmap = submitDate(contentData, SDKConfig.getConfig().getBackRequestUrl());

          origRespcode = (String)queryResmap.get("origRespCode");
          System.out.println("origRespcode:" + origRespcode);
          if (!"05".equals(origRespcode)) {
            System.out.println("查询成功,退出查询");
            break;
          }
        }

      }

      System.out.println("退货结果为");
      System.out.println(queryResmap.toString());
    }
    else
    {
      System.out.println("服务器返回错误");
    }
    return respCode;
  }

  private static Map<String, String> upmpRefundQuery(Map<String, String> map) {
    String orderTime = getCurrentTime();
    Map contentData = new HashMap();

    contentData.put("version", UpmpConfig.VERSION);

    contentData.put("encoding", UpmpConfig.CHARSET);

    contentData.put("signMethod", UpmpConfig.SIGN_TYPE);

    contentData.put("txnType", "00");

    contentData.put("txnSubType", "00");

    contentData.put("bizType", "000000");

    contentData.put("accessType", "0");

    contentData.put("merId", UpmpConfig.MER_ID);

    contentData.put("txnTime", orderTime);

    contentData.put("orderId", orderTime);

    contentData.put("queryId", orderTime);

    contentData.put("reserved", "");

    contentData.putAll(map);

    Map submitFromData = signData(contentData);

    return submitUrl(submitFromData, SDKConfig.getConfig().getBackRequestUrl());
  }

  private static String submitDada(Map<String, String> submitFromData, int type)
  {
    String requestUrl = "";
    if (type == 1) {
      requestUrl = SDKConfig.getConfig().getAppRequestUrl();
      Map resmap = submitUrl(submitFromData, 
        requestUrl);
      return (String)resmap.get("tn");
    }if (type == 2) {
      requestUrl = SDKConfig.getConfig().getFrontRequestUrl();
      String html = createHtml(requestUrl, submitFromData);
      return html;
    }if (type == 3) {
      requestUrl = SDKConfig.getConfig().getBackRequestUrl();
      Map resmap = submitUrl(submitFromData, requestUrl);
      return (String)resmap.get("respCode");
    }
    return null;
  }
}