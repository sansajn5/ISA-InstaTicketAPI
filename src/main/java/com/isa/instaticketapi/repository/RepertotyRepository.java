package com.isa.instaticketapi.repository;

import java.text.SimpleDateFormat;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.isa.instaticketapi.domain.Repertory;

/**
 * Spring Data JPA repository for the Repertory entity.
 */
@Repository
public interface RepertotyRepository extends JpaRepository<Repertory, Long> {
	Repertory findOneByDate(String date);
}
