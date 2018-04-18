package com.isa.instaticketapi.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.isa.instaticketapi.domain.Item;
import com.isa.instaticketapi.domain.ItemReservation;

@Repository
public interface ItemReservationRepository extends JpaRepository<ItemReservation,Long> {
	
	
	ItemReservation findOneById(Long id);

}
