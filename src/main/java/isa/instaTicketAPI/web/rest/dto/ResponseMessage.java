package isa.instaTicketAPI.web.rest.dto;

import lombok.Getter;
import lombok.Setter;

public class ResponseMessage {

	@Getter @Setter
	private String message;
	
	public ResponseMessage(String message) {
		this.message = message;
	}
	
}
