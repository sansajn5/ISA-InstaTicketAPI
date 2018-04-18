package com.isa.instaticketapi.web.rest.vm.FanZoneResource;

import com.isa.instaticketapi.domain.Item;
import com.isa.instaticketapi.domain.User;

public class ReservationResponse {

	private Item item;
	
	private User user;
	
	public ReservationResponse() {
		// TODO Auto-generated constructor stub
	}

	public ReservationResponse(Item item, User user) {
		super();
		this.item = item;
		this.user = user;
	}

	public Item getItem() {
		return item;
	}

	public void setItem(Item item) {
		this.item = item;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}
	
	
	
}
