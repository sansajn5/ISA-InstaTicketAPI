package com.isa.instaticketapi.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.isa.instaticketapi.domain.EmploymentFanZone;
import com.isa.instaticketapi.domain.FanZone;
import com.isa.instaticketapi.domain.Place;
import com.isa.instaticketapi.domain.User;

public interface EmploymentFanZoneRepository extends JpaRepository<EmploymentFanZone,Long>{
	
	
	EmploymentFanZone findOneById(Long id);
	
	List<User> findAllByFanZone(FanZone place);
	
	List<FanZone> findAllByAdmin(User user);

}
