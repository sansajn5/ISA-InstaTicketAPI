package com.isa.instaticketapi.service.dto;

import javax.validation.constraints.NotNull;

public class MailForgetDTO {
	
	
	@NotNull
	private String email;
	
	
	

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	
	
	
}
