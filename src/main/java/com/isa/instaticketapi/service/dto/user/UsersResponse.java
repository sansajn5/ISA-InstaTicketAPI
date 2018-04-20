package com.isa.instaticketapi.service.dto.user;

import java.util.List;

import com.isa.instaticketapi.domain.User;

public class UsersResponse {
	
	private List<User> users;
	
	private List<UserAuthDTO> authorities;
	
	
	public UsersResponse() {
		// TODO Auto-generated constructor stub
	}

	public UsersResponse(List<User> users, List<UserAuthDTO> authorities) {
		super();
		this.users = users;
		this.authorities = authorities;
	}

	public List<User> getUsers() {
		return users;
	}

	public void setUsers(List<User> users) {
		this.users = users;
	}
	

	public List<UserAuthDTO> getAuthorities() {
		return authorities;
	}

	public void setAuthorities(List<UserAuthDTO> authorities) {
		this.authorities = authorities;
	}
	
	
	
	
	

}
