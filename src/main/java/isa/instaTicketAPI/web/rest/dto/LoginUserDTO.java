package isa.instaTicketAPI.web.rest.dto;

import lombok.Getter;
import lombok.Setter;

public class LoginUserDTO {
	
	@Getter @Setter
	private String username;
	@Getter @Setter
	private String password;
	
	public LoginUserDTO() { super(); }
	
}
