package isa.instaTicketAPI.utils;

import javax.servlet.ServletRequest;
import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Component;

import isa.instaTicketAPI.domain.User;
import isa.instaTicketAPI.service.UserService;
import isa.instaTicketAPI.web.rest.dto.LoggedUserDTO;

@Component
public class UserUtils {

	@Autowired
	private TokenUtils tokenUtils;

	@Autowired
	private UserService userService;

	@Autowired
	private UserDetailsService userDetailsService;

	private static final String ADMIN = "ADMIN";
	private static final String USER = "USER";
	

	public Object getLoggedUser(ServletRequest request) {

		HttpServletRequest httpRequest = (HttpServletRequest) request;
		String token = httpRequest.getHeader("X-Auth-Token");

		UserDetails details = userDetailsService.loadUserByUsername(tokenUtils.getUsernameFromToken(token));
		if (details.getAuthorities().contains(new SimpleGrantedAuthority(ADMIN)) || details.getAuthorities().contains(new SimpleGrantedAuthority(USER)) ) {
			return userService.findByUsername(details.getUsername());
		} else {
			return null;
		}
	}

	public LoggedUserDTO getLoggedUserData(ServletRequest request) {

		HttpServletRequest httpRequest = (HttpServletRequest) request;
		String token = httpRequest.getHeader("X-Auth-Token");

		UserDetails details = userDetailsService.loadUserByUsername(tokenUtils.getUsernameFromToken(token));
		if (details.getAuthorities().contains(new SimpleGrantedAuthority(ADMIN))) {
			User user = userService.findByUsername(details.getUsername());
			return new LoggedUserDTO(user.getUsername(), user.getEmail(), ADMIN);
		} else if (details.getAuthorities().contains(new SimpleGrantedAuthority(USER))) {
			User user = userService.findByUsername(details.getUsername());
			return new LoggedUserDTO(user.getUsername(), user.getEmail(), USER);
		} else {
			return null;
		}
	}

	public boolean checkUniqueEmailAndUsername(String email, String username) {

		if (userService.findByUsername(username) != null || userService.findByEmail(email) != null) {
			return false;
		}
		return true;
	}
	
}
