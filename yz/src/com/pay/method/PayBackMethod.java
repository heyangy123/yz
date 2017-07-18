package com.pay.method;


import java.math.BigDecimal;
import java.util.List;

import com.pay.acp.UpmpBase;
import com.pay.ali.AlipayBack;
import com.pay.ali.AlipayConfig;
import com.pay.ali.AlipaySubmit;
import com.pay.wx.WxPrepay;
import com.pay.wx.model.WxRefundReturn;
import com.pay.wx.util.ConstantUtil;

public class PayBackMethod
{
  public static String refund(String batchNo, String tradeNo, BigDecimal backMoney, String backReason, String backUrl)
  {
    return AlipaySubmit.refund(batchNo, tradeNo, backMoney, backReason, backUrl);
  }

  public static String refund(String batchNo, String tradeNo, BigDecimal backMoney, String backReason)
  {
    return refund(batchNo, tradeNo, backMoney, backReason, AlipayConfig.refund_url);
  }

  public static String refund(String batchNo, List<AlipayBack> list)
  {
    return refund(batchNo, list, AlipayConfig.refund_url, AlipayConfig.partner, AlipayConfig.seller_id, AlipayConfig.private_key);
  }

  public static String refund(String batchNo, List<AlipayBack> list, String backUrl, String partnerId, String sellerId, String payKey)
  {
    return AlipaySubmit.refund(batchNo, list, backUrl, partnerId, sellerId, 
      payKey);
  }

  public static WxRefundReturn refund(String orderId, String refundId, BigDecimal total, BigDecimal refund, String appId, String partner, String key)
  {
    return WxPrepay.refund(orderId, refundId, total, refund, appId, partner, key);
  }

  public static WxRefundReturn refundApp(String orderId, String refundId, BigDecimal total, BigDecimal refund)
  {
    return refund(orderId, refundId, total, refund, ConstantUtil.APP_ID, ConstantUtil.PARTNER, ConstantUtil.APP_KEY);
  }

  public static WxRefundReturn refundWeb(String orderId, String refundId, BigDecimal total, BigDecimal refund)
  {
    return refund(orderId, refundId, total, refund, ConstantUtil.WEB_APP_ID, ConstantUtil.WEB_PARTNER, ConstantUtil.APP_KEY);
  }

  public static WxRefundReturn refundByCode(String tradeNo, String refundId, BigDecimal total, BigDecimal refund, String appId, String partner, String key)
  {
    return WxPrepay.refundByCode(tradeNo, refundId, total, refund, appId, partner, key);
  }

  public static WxRefundReturn refundAppByCode(String orderId, String refundId, BigDecimal total, BigDecimal refund)
  {
    return refundByCode(orderId, refundId, total, refund, ConstantUtil.APP_ID, ConstantUtil.PARTNER, ConstantUtil.APP_KEY);
  }

  public static WxRefundReturn refundWebByCode(String orderId, String refundId, BigDecimal total, BigDecimal refund)
  {
    return refundByCode(orderId, refundId, total, refund, ConstantUtil.WEB_APP_ID, ConstantUtil.WEB_PARTNER, ConstantUtil.APP_KEY);
  }

  public static String upmpRefund(String orderId, String trade_no)
  {
    return UpmpBase.upmpRefund(orderId, trade_no);
  }
}
