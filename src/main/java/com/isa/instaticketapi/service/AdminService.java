package com.isa.instaticketapi.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.isa.instaticketapi.domain.Authority;
import com.isa.instaticketapi.domain.User;
import com.isa.instaticketapi.repository.AuthorityRepository;
import com.isa.instaticketapi.repository.UserRepository;
import com.isa.instaticketapi.security.AuthoritiesConstants;

@Service
public class AdminService {

	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private AuthorityRepository authorityRepository;
	
	
	public void setSystemAdminRole(String username) {
		
		User user = userRepository.findOneByUsername(username).get();
		
		Authority authoritySuperAdmin = authorityRepository.findOne(AuthoritiesConstants.SUPER_ADMIN);
	
		if(!user.getAuthorities().contains(authoritySuperAdmin)) {
			
			user.getAuthorities().add(authoritySuperAdmin);
		}
		
	}
}
