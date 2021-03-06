package com.isa.instaticketapi.domain;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "Item")
public class Item extends AbstractAuditingEntity implements Serializable{
	
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@NotNull
	@Column(length = 100, unique = true)
	private String name;
	
	@Column(length = 200)
	private String description;
	
	@Column
	private String image;
	
	@Column
	private String price;
	
	@Column
	private Boolean sold;
	
	public Item() {
		// TODO Auto-generated constructor stub
		this.sold = false;
	}
	
	
	public Item(String name, String description, String image, String price) {
		super();
		this.name = name;
		this.description = description;
		this.image = image;
		this.price = price;
		this.sold = false;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getImage() {
		return image;
	}

	public void setImage(String image) {
		this.image = image;
	}


	public String getPrice() {
		return price;
	}


	public void setPrice(String price) {
		this.price = price;
	}


	public Boolean getSold() {
		return sold;
	}


	public void setSold(Boolean sold) {
		this.sold = sold;
	}

	
	
	
	
	

}
