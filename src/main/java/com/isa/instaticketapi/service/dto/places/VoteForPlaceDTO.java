package com.isa.instaticketapi.service.dto.places;

public class VoteForPlaceDTO {

	private String vote;
	private String place;

	public VoteForPlaceDTO() {
		super();
		// TODO Auto-generated constructor stub
	}

	public VoteForPlaceDTO(String vote, String place) {
		super();
		this.vote = vote;
		this.place = place;
	}

	public String getVote() {
		return vote;
	}

	public void setVote(String vote) {
		this.vote = vote;
	}

	public String getPlace() {
		return place;
	}

	public void setPlace(String place) {
		this.place = place;
	}

}
