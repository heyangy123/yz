package com.pay.wx.model;

public class WxRefundReturn extends WxRefundInfo
{
  private String out_refund_no;
  private String refund_id;
  private String refund_channel;
  private int refund_fee;
  private int cash_refund_fee;
  private int coupon_refund_fee;
  private int coupon_refund_count;
  private String coupon_refund_id;

  public String getOut_refund_no()
  {
    return this.out_refund_no;
  }

  public void setOut_refund_no(String out_refund_no) {
    this.out_refund_no = out_refund_no;
  }

  public String getRefund_id() {
    return this.refund_id;
  }

  public void setRefund_id(String refund_id) {
    this.refund_id = refund_id;
  }

  public String getRefund_channel() {
    return this.refund_channel;
  }

  public void setRefund_channel(String refund_channel) {
    this.refund_channel = refund_channel;
  }

  public int getRefund_fee() {
    return this.refund_fee;
  }

  public void setRefund_fee(int refund_fee) {
    this.refund_fee = refund_fee;
  }

  public int getCash_refund_fee() {
    return this.cash_refund_fee;
  }

  public void setCash_refund_fee(int cash_refund_fee) {
    this.cash_refund_fee = cash_refund_fee;
  }

  public int getCoupon_refund_fee() {
    return this.coupon_refund_fee;
  }

  public void setCoupon_refund_fee(int coupon_refund_fee) {
    this.coupon_refund_fee = coupon_refund_fee;
  }

  public int getCoupon_refund_count() {
    return this.coupon_refund_count;
  }

  public void setCoupon_refund_count(int coupon_refund_count) {
    this.coupon_refund_count = coupon_refund_count;
  }

  public String getCoupon_refund_id() {
    return this.coupon_refund_id;
  }

  public void setCoupon_refund_id(String coupon_refund_id) {
    this.coupon_refund_id = coupon_refund_id;
  }
}