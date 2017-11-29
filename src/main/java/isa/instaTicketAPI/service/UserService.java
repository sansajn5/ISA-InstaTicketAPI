package isa.instaTicketAPI.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import isa.instaTicketAPI.domain.User;
import isa.instaTicketAPI.domain.enumerations.UserStatus;
import isa.instaTicketAPI.repositories.UserRepository;
import isa.instaTicketAPI.utils.MailUtils;


@Service
public class UserService {

	
	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	MailUtils mailUtils;
	
	@Autowired
	private PasswordEncoder passwordEncoder;
	
	public void register(User user) {
		mailUtils.sendVerificationEmail(user.getEmail(), user.getConfirmationLink());
		userRepository.save(user);
	}
	
	public User findByUsername(String username) {
		return userRepository.findByUsername(username);
	}
	
	public User findByEmail(String email) {
		return userRepository.findByEmail(email);
	}
	
	public boolean checkEnabled(String username) {
		if(userRepository.findByUsername(username).getStatus().equals(UserStatus.ENABLED)) {
			return true;
		}
		return false;
	}
	
	public boolean checkLink(String token) {
		User u = userRepository.findByConfirmationLink(token);
		if( u == null)
			return false;	
		return true;
	}
	
	public boolean confirmAccount(String password,String token) {
		User u = userRepository.findByConfirmationLink(token);
		if( u == null)
			return false;
		System.out.println(passwordEncoder.matches(password, u.getPassword()));
		if(passwordEncoder.matches(password, u.getPassword())) {
			u.setStatus(UserStatus.ENABLED);
			userRepository.save(u);
		}
		return true;
	}
	
}
