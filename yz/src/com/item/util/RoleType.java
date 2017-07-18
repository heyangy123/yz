package com.item.util;

public enum RoleType {

	INSIDE("inside"),
	
	;
	
	private String role;
	
	private RoleType(String role){
		this.role = role;
	}
	
	public String getRole(){
		return this.role;
	}
}
