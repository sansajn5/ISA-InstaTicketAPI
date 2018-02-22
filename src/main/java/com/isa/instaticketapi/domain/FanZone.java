package com.isa.instaticketapi.domain;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.Size;



@Entity
@Table(name = "FanZone")
public class FanZone implements Serializable {
	
	
	
	/**
	 * 
	 */
	
	
	private static final long serialVersionUID = 1L;
	
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private String fanZoneId;
	
	@Size(min = 5, max = 100)
    @Column(length = 100, unique = true)
	private String name;
	
	
	@Column
	private String placeID;
	
	
	




	

	
	
	
	
	
	
	
	
	
	
	

}
