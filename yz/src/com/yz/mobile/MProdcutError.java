package com.yz.mobile;

import com.base.mobile.IMError;

public enum MProdcutError implements IMError{
	CREDIT_NOT_ENOUGH(10001,"积分不足！"),
	 
	STOCK_NOT_ENOUGH(10002,"库存不足！"),
	

	;

	/** 错误编码 */
	private int errorCode;

	/** 错误信息 */
	private String errorMsg;
	
	/**
	 * 构造函数
	 */
	private MProdcutError(int errorCode, String errorMsg) {
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
