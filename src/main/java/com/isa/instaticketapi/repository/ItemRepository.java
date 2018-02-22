package com.isa.instaticketapi.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.isa.instaticketapi.domain.Item;


/**
 * 
 * Spring Data JPA repository for the fan zone item entity
 * 
 * @author Dejan
 *
 */

@Repository
public interface ItemRepository extends JpaRepository<Item,Long>{
	
	
	List<Item> findAllByFanZoneID(String fZoneId);

}
