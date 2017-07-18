package com.yz.daoEx.model;

import com.yz.dao.model.SocialReply;

public class SocialReplyEx extends SocialReply{
	private String criticName ;
	private String img;
	private String targetName;
	public String getImg() {
		return img;
	}
	public void setImg(String img) {
		this.img = img;
	}
	public String getCriticName() {
		return criticName;
	}
	public void setCriticName(String criticName) {
		this.criticName = criticName;
	}
	public String getTargetName() {
		return targetName;
	}
	public void setTargetName(String targetName) {
		this.targetName = targetName;
	}
	
	
}