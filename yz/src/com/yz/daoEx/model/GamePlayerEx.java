package com.yz.daoEx.model;

import com.yz.dao.model.GamePlayer;

public class GamePlayerEx extends GamePlayer{
	private String userName;
	private String img;
	private Integer type;
	
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getImg() {
		return img;
	}
	public void setImg(String img) {
		this.img = img;
	}
	public Integer getType() {
		return type;
	}
	public void setType(Integer type) {
		this.type = type;
	}
	
	
}