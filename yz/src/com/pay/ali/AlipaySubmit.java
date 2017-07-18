package com.pay.ali;

import java.math.BigDecimal;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.SortedMap;
import java.util.TreeMap;
import java.util.Map.Entry;

import org.apache.commons.httpclient.NameValuePair;

import com.alibaba.fastjson.JSON;
import com.alipay.api.AlipayApiException;
import com.alipay.api.internal.util.AlipaySignature;
import com.base.util.DateUtil;
import com.pay.ali.httpClient.HttpProtocolHandler;
import com.pay.ali.httpClient.HttpRequest;
import com.pay.ali.httpClient.HttpResponse;
import com.pay.ali.httpClient.HttpResultType;
import com.pay.method.MFramePayType;
import com.pay.method.MPayType;
import com.pay.method.PayMethod;
import com.pay.util.MPayPlatform;


public class AlipaySubmit
{
  private static final String ALIPAY_GATEWAY_NEW = "https://mapi.alipay.com/gateway.do?";

  public static String buildRequestMysign(Map<String, String> sPara, String payKey)
  {
    String prestr = AlipayCore.createLinkString(sPara);
    String mysign = "";
    if (AlipayConfig.sign_type.equals("RSA"))
      mysign = RSA.sign(prestr, payKey, AlipayConfig.input_charset);
    else {
      mysign = MD5.sign(prestr, payKey, AlipayConfig.input_charset);
    }
    return mysign;
  }

  private static Map<String, String> buildRequestPara(Map<String, String> sParaTemp, String payKey)
  {
    Map sPara = AlipayCore.paraFilter(sParaTemp);

    String mysign = buildRequestMysign(sPara, payKey);

    sPara.put("sign", mysign);
    sPara.put("sign_type", AlipayConfig.sign_type);

    return sPara;
  }

  public static String buildRequest(Map<String, String> sParaTemp, String strMethod, String strButtonName, String payKey)
  {
    Map sPara = buildRequestPara(sParaTemp, payKey);
    List keys = new ArrayList(sPara.keySet());

    StringBuffer sbHtml = new StringBuffer();

    sbHtml.append("<form id=\"alipaysubmit\" name=\"alipaysubmit\" action=\"https://mapi.alipay.com/gateway.do?_input_charset=" + 
      AlipayConfig.input_charset + "\" method=\"" + strMethod + 
      "\">");

    for (int i = 0; i < keys.size(); i++) {
      String name = (String)keys.get(i);
      String value = (String)sPara.get(name);

      sbHtml.append("<input type=\"hidden\" name=\"" + name + "\" value=\"" + value + "\"/>");
    }

    sbHtml.append("<input type=\"submit\" value=\"" + strButtonName + "\" style=\"display:none;\"></form>");
    sbHtml.append("<script>document.forms['alipaysubmit'].submit();</script>");

    return sbHtml.toString();
  }

  public static String buildRequestUrl(Map<String, String> sParaTemp, String payKey)
  {
    Map sPara = buildRequestPara(sParaTemp, payKey);
    sPara.put("sign", URLEncoder.encode((String)sPara.get("sign")));
    return "https://mapi.alipay.com/gateway.do?" + AlipayCore.createLinkString(sPara);
  }

  public static String buildRequest(String strParaFileName, String strFilePath, Map<String, String> sParaTemp, String payKey)
    throws Exception
  {
    Map sPara = buildRequestPara(sParaTemp, payKey);

    HttpProtocolHandler httpProtocolHandler = HttpProtocolHandler.getInstance();

    HttpRequest request = new HttpRequest(HttpResultType.BYTES);

    request.setCharset(AlipayConfig.input_charset);

    request.setParameters(generatNameValuePair(sPara));
    request.setUrl("https://mapi.alipay.com/gateway.do?_input_charset=" + AlipayConfig.input_charset);

    HttpResponse response = httpProtocolHandler.execute(request, strParaFileName, strFilePath);
    if (response == null) {
      return null;
    }

    String strResult = response.getStringResult();

    return strResult;
  }

//  public static String query_timestamp()
//    throws MalformedURLException, DocumentException, IOException
//  {
//    String strUrl = "https://mapi.alipay.com/gateway.do?service=query_timestamp&partner=" + AlipayConfig.partner + "&_input_charset" + AlipayConfig.input_charset;
//    StringBuffer result = new StringBuffer();
//
//    SAXReader reader = new SAXReader();
//    Document doc = reader.read(new URL(strUrl).openStream());
//
//    List nodeList = doc.selectNodes("//alipay/*");
//    Iterator localIterator2;
//    label185: for (Iterator localIterator1 = nodeList.iterator(); localIterator1.hasNext(); 
//      localIterator2.hasNext())
//    {
//      Node node = (Node)localIterator1.next();
//
//      if ((!node.getName().equals("is_success")) || (!node.getText().equals("T")))
//        break label185;
//      List nodeList1 = doc.selectNodes("//response/timestamp/*");
//      localIterator2 = nodeList1.iterator(); continue; 
//      Node node1 = (Node)localIterator2.next();
//      result.append(node1.getText());
//    }
//    return result.toString();
//  }

  public static String buildRequest(String ALIPAY_GATEWAY_NEW, Map<String, String> sParaTemp, String strMethod, String strButtonName, String payKey)
  {
    Map sPara = buildRequestPara(sParaTemp, payKey);
    List keys = new ArrayList(sPara.keySet());

    StringBuffer sbHtml = new StringBuffer();

    sbHtml.append("<form id=\"alipaysubmit\" name=\"alipaysubmit\" action=\"" + ALIPAY_GATEWAY_NEW + 
      "_input_charset=utf-8\" method=\"" + strMethod + 
      "\">");

    for (int i = 0; i < keys.size(); i++) {
      String name = (String)keys.get(i);
      String value = (String)sPara.get(name);

      sbHtml.append("<input type=\"hidden\" name=\"" + name + "\" value=\"" + value + "\"/>");
    }

    sbHtml.append("<input type=\"submit\" value=\"" + strButtonName + "\" style=\"display:none;\"></form>");
    sbHtml.append("<script>document.forms['alipaysubmit'].submit();</script>");

    return sbHtml.toString();
  }

  public static String buildRequest(String ALIPAY_GATEWAY_NEW, Map<String, String> sParaTemp, String strMethod, String strButtonName, String strParaFileName, String payKey)
  {
    Map sPara = buildRequestPara(sParaTemp, payKey);
    List keys = new ArrayList(sPara.keySet());

    StringBuffer sbHtml = new StringBuffer();

    sbHtml.append("<form id=\"alipaysubmit\" name=\"alipaysubmit\"  enctype=\"multipart/form-data\" action=\"" + ALIPAY_GATEWAY_NEW + 
      "_input_charset=utf-8\" method=\"" + strMethod + 
      "\">");

    for (int i = 0; i < keys.size(); i++) {
      String name = (String)keys.get(i);
      String value = (String)sPara.get(name);

      sbHtml.append("<input type=\"hidden\" name=\"" + name + "\" value=\"" + value + "\"/>");
    }

    sbHtml.append("<input type=\"file\" name=\"" + strParaFileName + "\" />");

    sbHtml.append("<input type=\"submit\" value=\"" + strButtonName + "\" style=\"display:none;\"></form>");

    return sbHtml.toString();
  }

  public static String buildRequest(String ALIPAY_GATEWAY_NEW, String strParaFileName, String strFilePath, Map<String, String> sParaTemp, String payKey)
    throws Exception
  {
    Map sPara = buildRequestPara(sParaTemp, payKey);

    HttpProtocolHandler httpProtocolHandler = HttpProtocolHandler.getInstance();

    HttpRequest request = new HttpRequest(HttpResultType.BYTES);

    request.setCharset("utf-8");

    request.setParameters(generatNameValuePair(sPara));
    request.setUrl(ALIPAY_GATEWAY_NEW + "_input_charset=" + "utf-8");

    HttpResponse response = httpProtocolHandler.execute(request, strParaFileName, strFilePath);
    if (response == null) {
      return null;
    }

    String strResult = response.getStringResult();

    return strResult;
  }

  private static NameValuePair[] generatNameValuePair(Map<String, String> properties)
  {
    NameValuePair[] nameValuePair = new NameValuePair[properties.size()];
    int i = 0;
    for (Map.Entry entry : properties.entrySet()) {
      nameValuePair[(i++)] = new NameValuePair((String)entry.getKey(), (String)entry.getValue());
    }

    return nameValuePair;
  }
  
  public static String AppPay(String orderNo,BigDecimal totalPrice) throws Exception{
	  Map<String, String> params = new HashMap<String, String>();
		params.put("app_id", AlipayConfig.app_id);
		Map<String, String> biz_content = new HashMap<String, String>();
		biz_content.put("subject", "乐动约战-缴费订单");
		biz_content.put("out_trade_no", orderNo);
		biz_content.put("total_amount",totalPrice.toString());
		biz_content.put("product_code", "QUICK_MSECURITY_PAY");
		params.put("biz_content", JSON.toJSONString(biz_content));
		params.put("charset", "utf-8");
		params.put("format", "json");
		params.put("method", "alipay.trade.app.pay");
		params.put("notify_url", AlipayConfig.notify_url); //回调地址
		params.put("sign_type", "RSA");
		params.put("timestamp", DateUtil.asString(new Date()));
		params.put("version", "1.0");
		String sign = AlipaySignature.rsaSign(params, AlipayConfig.private_key, AlipayConfig.input_charset);
		List<String> list = new ArrayList<String>();
		for (Entry<String, String> entry : params.entrySet()) {
			list.add(entry.getKey()+"="+URLEncoder.encode(entry.getValue(), "utf-8")+"&");
		}
		int size = list.size();
      String [] arrayToSort = list.toArray(new String[size]);
      Arrays.sort(arrayToSort, String.CASE_INSENSITIVE_ORDER);
      StringBuilder sb = new StringBuilder();
      for(int i = 0; i < size; i ++) {
          sb.append(arrayToSort[i]);
      }
      sb.append("sign="+URLEncoder.encode(sign, "utf-8"));
      
      return sb.toString();
  }

  public static String webPay(String orderId, BigDecimal total, String info, MPayPlatform payType, String back, String front, String partnerId, String sellerId, String payKey, int type)
  {
    return webPay(orderId, total, info, payType, back, front, partnerId, sellerId, payKey, type, MFramePayType.NORMAL_BUY);
  }

  public static String webPay(String orderId, BigDecimal total, String info, MPayPlatform payType, String back, String front, String partnerId, String sellerId, String payKey, int type, MPayType pay)
  {
    Map params = new HashMap();
    params.put("notify_url", PayMethod.urlToUrl(back));
    params.put("return_url", PayMethod.urlToUrl(front));
    params.put("out_trade_no", pay==null?orderId:(orderId + "_" + pay.toString()));
    params.put("subject", info);
    params.put("total_fee", total.toString());
    params.put("partner", partnerId);
    if (payType.equals(MPayPlatform.WAP)) {
      params.put("service", "alipay.wap.create.direct.pay.by.user");
      params.put("seller_id", sellerId);
    }
    else if (payType.equals(MPayPlatform.WEB)) {
      params.put("service", "create_direct_pay_by_user");
      params.put("seller_email", sellerId);
    }

    params.put("extra_common_param", pay.toString());

    SortedMap param = initMap(params);

    if (type == 1) {
      return buildRequest(param, "post", "提交", payKey);
    }
    return buildRequestUrl(param, payKey);
  }

  public static String webPay(String orderId, BigDecimal total, String info, MPayPlatform payType, String back, String front, int type)
  {
    return webPay(orderId, total, info, payType, back, front, AlipayConfig.partner, AlipayConfig.seller_id, AlipayConfig.private_key, type);
  }

  public static SortedMap<String, String> initMap(Map<String, String> map) {
    SortedMap params = new TreeMap();
    params.put("partner", AlipayConfig.partner);
    params.put("_input_charset", AlipayConfig.input_charset);
    params.put("payment_type", "1");
    params.put("extra_common_param", MFramePayType.NORMAL_BUY.name());
    params.putAll(map);
    return params;
  }

  public static String refund(String batchNo, String tradeNo, BigDecimal backMoney, String backReason, String backUrl)
  {
    return refund(batchNo, new AlipayBack(tradeNo, backMoney, backReason), backUrl);
  }

  public static String refund(String batchNo, AlipayBack list, String backUrl) {
    List back = new ArrayList();
    back.add(list);
    return refund(batchNo, back, backUrl);
  }

  public static String refund(String batchNo, List<AlipayBack> list, String backUrl) {
    return refund(batchNo, list, backUrl, AlipayConfig.partner, AlipayConfig.seller_id, AlipayConfig.private_key);
  }

  public static String refund(String batchNo, List<AlipayBack> list, String backUrl, String partnerId, String sellerId, String payKey)
  {
    Map params = new HashMap();
    params.put("service", "refund_fastpay_by_platform_pwd");
    params.put("notify_url", PayMethod.urlToUrl(backUrl));
    params.put("partner", partnerId);
    params.put("_input_charset", AlipayConfig.input_charset);
    params.put("seller_email", sellerId);
    params.put("refund_date", UtilDate.getDateFormatter());
    params.put("batch_no", batchNo);
    params.put("batch_num", list.size());
    params.put("detail_data", detailData(list));
    return buildRequestUrl(params, payKey);
  }

  private static String detailData(List<AlipayBack> list)
  {
    StringBuffer sb = new StringBuffer();
    for (int i = 0; i < list.size(); i++) {
      AlipayBack back = (AlipayBack)list.get(i);
      if (i == list.size() - 1)
        sb.append(back.getTradeNo()).append("^").append(back.getBack().toString()).append("^").append(back.getReason());
      else {
        sb.append(back.getTradeNo()).append("^").append(back.getBack().toString()).append("^").append(back.getReason()).append("#");
      }
    }
    return sb.toString();
  }
}