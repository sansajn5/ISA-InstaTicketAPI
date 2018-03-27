package com.isa.instaticketapi.domain;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "EmploymentFanZone")
public class EmploymentFanZone extends AbstractAuditingEntity {
	
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	
	@ManyToOne(fetch=FetchType.EAGER)
	private FanZone fanZone;
	
	@ManyToOne(fetch=FetchType.EAGER)
	private User admin;
	
	
	
	public EmploymentFanZone() {
		// TODO Auto-generated constructor stub
	}



	public EmploymentFanZone(FanZone fanZone, User admin) {
		super();
		this.fanZone = fanZone;
		this.admin = admin;
	}



	public Long getId() {
		return id;
	}



	public void setId(Long id) {
		this.id = id;
	}



	public FanZone getFanZone() {
		return fanZone;
	}



	public void setFanZone(FanZone fanZone) {
		this.fanZone = fanZone;
	}



	public User getAdmin() {
		return admin;
	}



	public void setAdmin(User admin) {
		this.admin = admin;
	}
	
	
	
	

}
