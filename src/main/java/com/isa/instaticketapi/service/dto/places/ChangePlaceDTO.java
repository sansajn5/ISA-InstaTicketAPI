package com.isa.instaticketapi.service.dto.places;

import java.time.Instant;

import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.NotBlank;

/**
 *  DTO representing object for creating/edit place
 */
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

	private String lastModifiedBy;

	private Instant lastModifiedDate;

	public ChangePlaceDTO() {
		super();
		// TODO Auto-generated constructor stub
	}

	public ChangePlaceDTO(String name, String address, String descripton, String type, String lastModifiedBy,
			Instant lastModifiedDate) {
		super();
		this.name = name;
		this.address = address;
		this.descripton = descripton;
		this.type = type;
		this.lastModifiedBy = lastModifiedBy;
		this.lastModifiedDate = lastModifiedDate;
	}

	public String getDescripton() {
		return descripton;
	}

	public void setDescripton(String descripton) {
		this.descripton = descripton;
	}

	public String getLastModifiedBy() {
		return lastModifiedBy;
	}

	public void setLastModifiedBy(String lastModifiedBy) {
		this.lastModifiedBy = lastModifiedBy;
	}

	public Instant getLastModifiedDate() {
		return lastModifiedDate;
	}

	public void setLastModifiedDate(Instant lastModifiedDate) {
		this.lastModifiedDate = lastModifiedDate;
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
		return descripton;
	}

	public void setDescription(String description) {
		this.descripton = description;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

}
