package com.isa.instaticketapi.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.isa.instaticketapi.domain.VoteForPlace;

/**
 * Spring Data JPA repository for the vote for event entity.
 */
@Repository
public interface VotePlaceRepository extends JpaRepository<VoteForPlace, Long> {

}
