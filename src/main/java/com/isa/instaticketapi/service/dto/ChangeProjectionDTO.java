package com.isa.instaticketapi.service.dto;

import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.NotBlank;

public class ChangeProjectionDTO {
	@NotBlank
	@Size(max = 60)
	private String name;

	@Size(max = 5000)
	private String actors;

	@NotBlank
	@Size(max = 50)
	private String type;

	@Size(max = 50)
	private String director;

	@Size(max = 4)
	private int duration;

	@Size(max = 5000)
	private String description;

	
	public ChangeProjectionDTO() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	

	public String getDescription() {
		return description;
	}



	public void setDescription(String description) {
		this.description = description;
	}



	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getActors() {
		return actors;
	}

	public void setActors(String actors) {
		this.actors = actors;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getDirector() {
		return director;
	}

	public void setDirector(String director) {
		this.director = director;
	}

	public int getDuration() {
		return duration;
	}

	public void setDuration(int duration) {
		this.duration = duration;
	}

}
