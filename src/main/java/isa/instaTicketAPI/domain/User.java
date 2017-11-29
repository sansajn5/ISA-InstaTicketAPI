package isa.instaTicketAPI.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import isa.instaTicketAPI.domain.enumerations.Role;
import isa.instaTicketAPI.domain.enumerations.UserStatus;
import lombok.Getter;
import lombok.Setter;

@Entity
public class User {

	@Id
    @GeneratedValue(strategy=GenerationType.AUTO)
	@Getter @Setter private Long id;
	
	@Column(name="username", nullable = false, unique = true)
	@NotNull(message = "Name cannot be null.")
	@Getter @Setter 
	private String username;
	
	@Column(name="password",nullable = false)
	@NotNull(message = "Password cannot be null")
	@Getter @Setter
	private String password;
	
	@Column(name="email",nullable = false, unique = true)
	@NotNull(message = "Email cannot be null")
	@Getter @Setter
	private String email;
	
	@NotNull
	@Column(name= "role")
	@Enumerated(EnumType.STRING)
	@Getter @Setter
	private Role role;
	
	@ManyToOne
	@Getter @Setter
	private Authority authority;
	
	@NotNull
	@Column(name="status")
	@Enumerated(EnumType.STRING)
	@Getter @Setter
	private UserStatus status;
	
	@Column(name = "ActivationLink", unique = true)
	@Getter @Setter
	private String confirmationLink;
	
	
}
