package com.isa.instaticketapi.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.isa.instaticketapi.domain.Offer;

@Repository
public interface OfferRepository extends JpaRepository<Offer,Long> {

	Offer findOneById(Long id);
	
	//@Query("SELECT it.fanZone as id FROM Item it where it.fanZone = ?1")
	List<Offer> findAll();
	
	
}
