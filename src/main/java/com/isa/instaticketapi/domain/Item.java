package com.isa.instaticketapi.domain;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

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
	

}
