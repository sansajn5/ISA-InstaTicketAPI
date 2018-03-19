package com.isa.instaticketapi.service.dto.account;

import javax.validation.constraints.Size;

public class AdminRoleDTO {

	
	@Size(min = 1, max = 50)
    private String username;
	
	
	public AdminRoleDTO(){} 
	

	public AdminRoleDTO(String username) {
		super();
		this.username = username;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	
	
	
}
