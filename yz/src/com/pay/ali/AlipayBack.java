package com.pay.ali;

import java.math.BigDecimal;

public class AlipayBack
{
  private String tradeNo;
  private BigDecimal back;
  private String reason;

  public AlipayBack(String tradeNo, BigDecimal back, String reason)
  {
    this.tradeNo = tradeNo;
    this.back = back;
    this.reason = reason;
  }

  public AlipayBack(String tradeNo, BigDecimal back) {
    this(tradeNo, back, "协商退款");
  }

  public String getTradeNo()
  {
    return this.tradeNo;
  }

  public void setTradeNo(String tradeNo) {
    this.tradeNo = tradeNo;
  }

  public BigDecimal getBack() {
    return this.back;
  }

  public void setBack(BigDecimal back) {
    this.back = back;
  }

  public String getReason() {
    return this.reason;
  }

  public void setReason(String reason) {
    this.reason = reason;
  }
}