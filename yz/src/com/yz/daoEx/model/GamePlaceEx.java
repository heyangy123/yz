package com.yz.daoEx.model;

import com.yz.dao.model.GamePlace;

public class GamePlaceEx extends GamePlace{
	private String provinceCode;
	private String province;
	private String city;
	
	public String getProvinceCode() {
		return provinceCode;
	}
	public void setProvinceCode(String provinceCode) {
		this.provinceCode = provinceCode;
	}
	public String getProvince() {
		return province;
	}
	public void setProvince(String province) {
		this.province = province;
	}
	public String getCity() {
		return city;
	}
	public void setCity(String city) {
		this.city = city;
	}
	
	
}