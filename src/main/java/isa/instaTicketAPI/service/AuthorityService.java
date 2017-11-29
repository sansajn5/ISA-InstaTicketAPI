package isa.instaTicketAPI.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import isa.instaTicketAPI.domain.Authority;
import isa.instaTicketAPI.repositories.AuthorityRepository;

@Service
public class AuthorityService {

	@Autowired
	AuthorityRepository authorityRepository;
	
	public Authority findByName(String name) {
		return authorityRepository.findByName(name);
	}
}
