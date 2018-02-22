package com.isa.instaticketapi.domain;

import java.io.Serializable;

import javax.persistence.*;
import javax.validation.constraints.Size;



@Entity
@Table(name = "FanZone")
public class FanZone extends AbstractAuditingEntity implements Serializable  {
	
	
	
	/**
	 * 
	 */
	
	private static final long serialVersionUID = 1L;
	
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long fanZoneId;
	
	@Size(min = 5, max = 100)
    @Column(length = 100, unique = true)
	private String name;
	
	
	@Column
	private String placeID;
	
	
	




	

	
	
	
	
	
	
	
	
	
	
	

}
