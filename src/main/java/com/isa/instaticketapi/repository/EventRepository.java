package com.isa.instaticketapi.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.isa.instaticketapi.domain.Place;
import com.isa.instaticketapi.domain.Event;

/**
 * Spring Data JPA repository for the Projection entity.
 */
@Repository
public interface EventRepository extends JpaRepository<Event, Long> {

	Event findOneById(Long id);
}
