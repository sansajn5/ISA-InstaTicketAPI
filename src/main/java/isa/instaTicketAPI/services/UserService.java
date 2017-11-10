package isa.instaTicketAPI.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import isa.instaTicketAPI.domain.User;
import isa.instaTicketAPI.repositories.UserRepository;

@Service
public class UserService {

	@Autowired
	UserRepository userRepository;
	/*
	 * TEST
	 */
	public User getUser() {
		User user = new User();
		user = userRepository.findAll().get(0);
		return user;
	}
	
}
