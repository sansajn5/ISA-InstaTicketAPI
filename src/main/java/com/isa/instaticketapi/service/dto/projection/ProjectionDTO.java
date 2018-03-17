package com.isa.instaticketapi.service.dto.projection;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

public class ProjectionDTO {

	private Long id;

	@NotNull
	@Size(min = 5, max = 5)
	private String startTime;

	@NotNull
	@Size(min = 5, max = 5)
	private String endTime;

	private String event;

	private String date;

	public ProjectionDTO() {
		super();
		// TODO Auto-generated constructor stub
	}

	public ProjectionDTO(Long id, String startTime, String endTime, String event, String date) {
		super();
		this.id = id;
		this.startTime = startTime;
		this.endTime = endTime;
		this.event = event;
		this.date = date;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getStartTime() {
		return startTime;
	}

	public void setStartTime(String startTime) {
		this.startTime = startTime;
	}

	public String getEndTime() {
		return endTime;
	}

	public void setEndTime(String endTime) {
		this.endTime = endTime;
	}

	public String getEvent() {
		return event;
	}

	public void setEvent(String event) {
		this.event = event;
	}

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

}
