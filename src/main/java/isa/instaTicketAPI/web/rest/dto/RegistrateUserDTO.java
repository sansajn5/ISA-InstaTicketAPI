package isa.instaTicketAPI.web.rest.dto;

import lombok.Getter;
import lombok.Setter;

public class RegistrateUserDTO {
	
	@Getter @Setter
	private String email;
	@Getter @Setter
	private String username;
	@Getter @Setter
	private String password;
	@Getter @Setter
	private String authority;
	
	public RegistrateUserDTO() { super(); }
	
	
}
