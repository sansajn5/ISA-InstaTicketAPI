package com.isa.instaticketapi.domain;

import java.io.Serializable;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.Size;

/**
 * 
 * Entity FanZone
 * 
 * @author Dejan
 *
 */

@Entity
@Table(name = "FanZone")
public class FanZone extends AbstractAuditingEntity implements Serializable  {
	
	
	
	
	
	private static final long serialVersionUID = 1L;
	
	
	private Set<Item> fanZoneItems;
	
	
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long fanZoneId;
	
	@Size(min = 5, max = 100)
    @Column(length = 100, unique = true)
	private String name;
	
	
	@Column
	private String placeID;
	
	
	@OneToMany(mappedBy = "fZoneId", cascade = CascadeType.ALL)
    public Set<Item> getItems() {
        return fanZoneItems;
    }



	public void setFanZoneItems(Set<Item> fanZoneItems) {
		this.fanZoneItems = fanZoneItems;
	}


	public Long getFanZoneId() {
		return fanZoneId;
	}


	public void setFanZoneId(Long fanZoneId) {
		this.fanZoneId = fanZoneId;
	}


	public String getName() {
		return name;
	}


	public void setName(String name) {
		this.name = name;
	}


	public String getPlaceID() {
		return placeID;
	}


	public void setPlaceID(String placeID) {
		this.placeID = placeID;
	}


	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	
	
	




	

	
	
	
	
	
	
	
	
	
	
	

}
