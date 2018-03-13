package com.isa.instaticketapi.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.isa.instaticketapi.domain.Hall;

/**
 * Spring Data JPA repository for the Hall entity.
 */
@Repository
public interface HallRepository extends JpaRepository<Hall,Long>  {

}