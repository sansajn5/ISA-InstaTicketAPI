package com.isa.instaticketapi.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.isa.instaticketapi.domain.VoteForEvent;

/**
 * Spring Data JPA repository for the vote for place entity.
 */
@Repository
public interface VoteEventRepository extends JpaRepository<VoteForEvent, Long>  {

}
