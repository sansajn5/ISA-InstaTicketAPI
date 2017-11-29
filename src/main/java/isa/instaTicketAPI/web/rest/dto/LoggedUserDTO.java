package isa.instaTicketAPI.web.rest.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@ToString
public class LoggedUserDTO {

	@Getter @Setter
	private String name;
	@Getter @Setter
	private String surname;
	@Getter @Setter
	private String role;

	public LoggedUserDTO() {
		super();
	}

	public LoggedUserDTO(String name, String surname, String role) {
		super();
		this.name = name;
		this.surname = surname;
		this.role = role;
	}

}
