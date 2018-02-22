package com.isa.instaticketapi.service.dto;

import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.NotBlank;

public class ChangePasswordDTO {

	@NotBlank
	@Size(max = 50)
	private String password;

	@NotBlank
	@Size(max = 50)
	private String rePassword;

	public ChangePasswordDTO(String password, String rePassword) {
		super();
		this.password = password;
		this.rePassword = rePassword;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getRePassword() {
		return rePassword;
	}

	public void setRePassword(String rePassword) {
		this.rePassword = rePassword;
	}

}
