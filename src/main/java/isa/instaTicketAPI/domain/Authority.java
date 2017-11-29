package isa.instaTicketAPI.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Getter;
import lombok.Setter;

@Entity
public class Authority {
	
	@Id
	@GeneratedValue
	@Getter @Setter
	private Long id;
	
	@Column(nullable = false)
	@Getter @Setter
	String name;

}
