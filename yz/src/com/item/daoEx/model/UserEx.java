package com.item.daoEx.model;

import com.item.dao.model.User;

public class UserEx extends User{
	
	private Integer rank;
	
	private Integer isCaptain;

	public Integer getRank() {
		return rank;
	}

	public void setRank(Integer rank) {
		this.rank = rank;
	}
	
	private String newMsg;

	public String getNewMsg() {
		return newMsg;
	}

	public void setNewMsg(String newMsg) {
		this.newMsg = newMsg;
	}

	private String position;
	
	private Integer isSign;
	
	private Integer type;

	public String getPosition() {
		return position;
	}

	public void setPosition(String position) {
		this.position = position;
	}

	public Integer getType() {
		return type;
	}

	public void setType(Integer type) {
		this.type = type;
	}

	public Integer getIsSign() {
		return isSign;
	}

	public void setIsSign(Integer isSign) {
		this.isSign = isSign;
	}

	public Integer getIsCaptain() {
		return isCaptain;
	}

	public void setIsCaptain(Integer isCaptain) {
		this.isCaptain = isCaptain;
	}

	@Override
	public String toString() {
		return "UserEx [rank=" + rank + ", isCaptain=" + isCaptain
				+ ", newMsg=" + newMsg + ", position=" + position + ", isSign="
				+ isSign + ", type=" + type + "]";
	}
	
	
}