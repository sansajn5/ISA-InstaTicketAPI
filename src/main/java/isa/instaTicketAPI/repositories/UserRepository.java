package isa.instaTicketAPI.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import isa.instaTicketAPI.domain.User;

public interface UserRepository extends JpaRepository<User, Long> {

}
