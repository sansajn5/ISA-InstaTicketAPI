package com.isa.instaticketapi.domain;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table(name = "ItemReservation")
public class ItemReservation extends AbstractAuditingEntity {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Column
	private String user;
	
	@OneToOne(cascade = CascadeType.ALL)
	private Item item;
	
	@Column
	private String place;
	
	public ItemReservation() {
		// TODO Auto-generated constructor stub
	}
	
	
	
	

	public ItemReservation(String user, Item item, String place) {
		super();
		this.user = user;
		this.item = item;
		this.place = place;
	}





	public String getUser() {
		return user;
	}

	public void setUser(String user) {
		this.user = user;
	}

	public Item getItem() {
		return item;
	}

	public void setItem(Item item) {
		this.item = item;
	}

	public String getPlace() {
		return place;
	}

	public void setPlace(String place) {
		this.place = place;
	}
	
	
	
}
