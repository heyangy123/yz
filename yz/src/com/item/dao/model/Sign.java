package com.item.dao.model;



public class Sign {

	private String id;

	public void setId(String id) {
		this.id=id == null ? id : id.trim();
	}

	public String getId() {
		return id;
	}

}
