package com.item.mobile;

import com.base.mobile.IMError;

public enum MEnumError implements IMError {
	SERVER_BUSY_ERROR(10000,"服务器忙，请稍后再试"),
	UPLOAD_FAILURE_ERROR(10001,"文件上传失败"),
	USER_NOEXIST_ERROR(10002,"用户不存在"), 
	LOGIN_FAILURE_ERROR(10003,"用户名或密码不正确"), 
	PHONE_EXISTS_ERROR(10004,"手机号已存在"), 
	PHONE_NOTEXISTS_ERROR(10005,"手机号不存在"), 
	MOBILE_CODE_ERROR(10006,"短信验证码错误"),
	CREATE_ACCOUNT_ERROR(10007,"注册失败"), 
	ACCOUNT_STOP_ERROR(10008,"账号已被封停"), 
	UPDATE_ACCOUNT_ERROR(10009,"账号更新失败"), 
	PASSWORD_FALSE_ERROR(10010,"密码错误"),
	OAUTHLOGIN_FAILURE_ERROR(10011,"登录失败"),
	ACCOUNT_VERIFY_LOAD(10012,"账号审核中"),
	
	
	
	
	SMS_ERROR_4086(10100,"发送失败，短时间内该号码提交次数过多，如有疑问请联系客服。"),
	SMS_ERROR(20101,"短信发送失败"), 
	
	CASH_NOT_ENOUGHT(10901,"余额不足"), 
	WM_ORDER_NOTEXISTS(10902,"订单不存在"), 
	WM_ORDER_CANDOTHIS(10903,"订单无法做此项操作"), 
	
	;
	
	/** 错误编码 */
	private int errorCode;

	/** 错误信息 */
	private String errorMsg;
	
	/**
	 * 构造函数
	 */
	private MEnumError(int errorCode, String errorMsg) {
		this.errorCode = errorCode;
		this.errorMsg = errorMsg;
	}

	/**
	 * @return int 错误代码
	 */
	public int getErrorCode() {
		return errorCode;
	}

	/**
	 * @return String 错误信息
	 */
	public String getErrorMsg() {
		return errorMsg;
	}
}
