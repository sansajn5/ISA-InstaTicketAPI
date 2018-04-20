package com.isa.instaticketapi.service.dto.user;

import java.util.List;

import com.isa.instaticketapi.domain.Authority;

public class UserAuthDTO {
	
	Long id;
	
	List<String> authorities;

	public UserAuthDTO() {
		// TODO Auto-generated constructor stub
	}

	public UserAuthDTO(Long id, List<String> authorities) {
		super();
		this.id = id;
		this.authorities = authorities;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public List<String> getAuthorities() {
		return authorities;
	}

	public void setAuthorities(List<String> authorities) {
		this.authorities = authorities;
	}

	
	
	
	
}
