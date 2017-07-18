package com.yz.daoEx.model;

import java.util.List;

import com.yz.dao.model.Social;

public class SocialEx extends Social{
	private String nickName;
	
	private String logo;
	
	private String userId;

	private List<ImgView> imgViewList;


	public List<ImgView> getImgViewList() {
		return imgViewList;
	}

	public void setImgViewList(List<ImgView> imgViewList) {
		this.imgViewList = imgViewList;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getLogo() {
		return logo;
	}

	public void setLogo(String logo) {
		this.logo = logo;
	}

	public String getNickName() {
		return nickName;
	}

	public void setNickName(String nickName) {
		this.nickName = nickName;
	}
	
	
}