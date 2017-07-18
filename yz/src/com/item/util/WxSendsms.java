package com.item.util;

public enum WxSendsms {
	SEND_FAIL("0","提交失败"),
	SEND_SUCCESS("2","提交成功"),
	ACCOUNT_NULL("40","帐号不能为空"),
	LOGIN_FAIL("30","用户名或密码不正确"),
	LESS_COUNT("41","剩余条数不足"),
	IP_WRONG("43","访问ip与备案ip不符"),
	PHONE_WRONG("51","手机格式不正确"),
	TEMPLATE_WRONG("50","短信内容与模板不匹配"),
	;
	
	private String code;
	
	private String msg;
	
	private WxSendsms(String code, String msg){
		this.code = code;
		this.msg = msg;
	}
	
	public String getMsg(){
		return this.msg;
	}
	
	public String getCode(){
		return this.code;
	}
}
