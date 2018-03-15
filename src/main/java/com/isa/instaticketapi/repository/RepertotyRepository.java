package com.isa.instaticketapi.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.isa.instaticketapi.domain.Reperotry;
/**
 * Spring Data JPA repository for the Repertory entity.
 */
@Repository
public interface RepertotyRepository  extends JpaRepository<Reperotry, Long>{

}
