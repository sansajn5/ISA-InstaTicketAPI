package com.isa.instaticketapi.domain;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.Size;

/**
 * 
 * Entity item in FanZone
 * 
 * @author Dejan
 *
 */



@Entity
@Table(name = "Item")
public class Item implements Serializable {
	
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long itemID;
	
	
	@Size(min = 5, max = 100)
    @Column(length = 100, unique = false)
	private String itemName;
	
	
	@Column
	private String fanZoneId;


	public Long getItemID() {
		return itemID;
	}


	public void setItemID(Long itemID) {
		this.itemID = itemID;
	}


	public String getItemName() {
		return itemName;
	}


	public void setItemName(String itemName) {
		this.itemName = itemName;
	}

	@ManyToOne
	@JoinColumn(name = "fanZoneId")
	public String getFanZoneId() {
		return fanZoneId;
	}


	public void setFanZoneId(String fanZoneId) {
		this.fanZoneId = fanZoneId;
	}
	
	
	
	
	
	

}
