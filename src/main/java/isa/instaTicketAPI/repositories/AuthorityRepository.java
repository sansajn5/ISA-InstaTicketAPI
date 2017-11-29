package isa.instaTicketAPI.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import isa.instaTicketAPI.domain.Authority;


public interface AuthorityRepository extends JpaRepository<Authority, Long> {

	public Authority findByName(String name);
	
}
