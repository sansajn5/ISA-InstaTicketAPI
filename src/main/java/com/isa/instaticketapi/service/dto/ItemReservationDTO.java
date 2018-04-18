package com.isa.instaticketapi.service.dto;

public class ItemReservationDTO {
	
	
	private Long id;
	
	private String user;
	
	private String place;
	
	
	public ItemReservationDTO() {
		// TODO Auto-generated constructor stub
	}


	public ItemReservationDTO(Long id, String user, String place) {
		super();
		this.id = id;
		this.user = user;
		this.place = place;
	}


	public Long getId() {
		return id;
	}


	public void setId(Long id) {
		this.id = id;
	}


	public String getUser() {
		return user;
	}


	public void setUser(String user) {
		this.user = user;
	}


	public String getPlace() {
		return place;
	}


	public void setPlace(String place) {
		this.place = place;
	}
	
	

}
