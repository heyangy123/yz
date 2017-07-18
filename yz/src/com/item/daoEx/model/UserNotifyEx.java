package com.item.daoEx.model;

import com.item.dao.model.UserNotify;

public class UserNotifyEx extends UserNotify{
	private String userName;
	private String headImg;
	
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getHeadImg() {
		return headImg;
	}
	public void setHeadImg(String headImg) {
		this.headImg = headImg;
	}
}