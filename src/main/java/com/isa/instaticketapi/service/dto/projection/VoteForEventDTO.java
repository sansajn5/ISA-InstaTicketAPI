package com.isa.instaticketapi.service.dto.projection;

public class VoteForEventDTO {
	private String vote;
	private String event;
	private String place;

	public VoteForEventDTO() {
		super();
		// TODO Auto-generated constructor stub
	}

	public VoteForEventDTO(String vote, String event, String place) {
		super();
		this.vote = vote;
		this.event = event;
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

	public String getEvent() {
		return event;
	}

	public void setEvent(String event) {
		this.event = event;
	}

}
