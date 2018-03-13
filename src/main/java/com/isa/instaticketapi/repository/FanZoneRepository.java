package com.isa.instaticketapi.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.isa.instaticketapi.domain.FanZone;

@Repository
public interface FanZoneRepository extends JpaRepository<FanZone,Long>{
	
	FanZone findOneById(Long id);
}
