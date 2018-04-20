package com.isa.instaticketapi.service.dto.user;

import com.isa.instaticketapi.domain.User;

public class UserResponse {

	private User user;
		
	public UserResponse() {
		// TODO Auto-generated constructor stub
	}

	public UserResponse(User user) {
		super();
		this.user = user;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	
}
