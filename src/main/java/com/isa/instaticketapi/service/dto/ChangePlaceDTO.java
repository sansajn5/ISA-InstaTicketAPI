package com.isa.instaticketapi.service.dto;

import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.NotBlank;

public class ChangePlaceDTO {
	@NotBlank
	@Size(max = 50)
	private String name;

	@Size(max = 50)
	private String address;

	@Size(max = 5000)
	private String descripton;

	@Size(max = 20)
	private String type;

	public ChangePlaceDTO() {
		super();
		// TODO Auto-generated constructor stub
	}

	public ChangePlaceDTO(String name, String address, String description, String type) {
		super();
		this.name = name;
		this.address = address;
		this.description = description;
		this.type = type;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

}
