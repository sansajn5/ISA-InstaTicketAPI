package isa.instaTicketAPI.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.NotNull;

import lombok.Getter;
import lombok.Setter;

@Entity
public class User {

	@Id
    @GeneratedValue(strategy=GenerationType.AUTO)
	@Getter @Setter private Long id;
	
	@Column(nullable = false, unique = true)
	@NotNull(message = "Name cannot be null.")
	@Getter @Setter 
	private String username;
}
