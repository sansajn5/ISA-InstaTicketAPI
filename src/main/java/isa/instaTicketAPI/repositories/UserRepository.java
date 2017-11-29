package isa.instaTicketAPI.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import isa.instaTicketAPI.domain.User;

public interface UserRepository extends JpaRepository<User, Long> {

	public User findByUsername(String username);
	
	public User findByEmail(String email);
	
	public User findByConfirmationLink(String link);
	
}
