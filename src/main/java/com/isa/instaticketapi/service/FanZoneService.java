package com.isa.instaticketapi.service;

import java.util.List;

import javax.transaction.Transactional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.isa.instaticketapi.domain.Item;
import com.isa.instaticketapi.repository.FanZoneRepository;
import com.isa.instaticketapi.repository.ItemRepository;

/**
 * 
 * 
 * @author Dejan
 *
 * Service class for handling with FanZone
 * 
 *
 */

@Service
@Transactional
public class FanZoneService {

	
	private final Logger log = LoggerFactory.getLogger(FanZoneService.class);
	
	@Autowired
	private FanZoneRepository fanZoneRepository;
	
	@Autowired
	private ItemRepository itemRepository;
	
	
	
	
	/**
	 * 
	 * Showing all items in fan zone
	 * 
	 */
	
	public List<Item> showItems(String fanZoneID) {
		
		log.debug("Listing all items from fan zone with id " + fanZoneID);
		
		List<Item> items = itemRepository.findAllByFanZoneID(fanZoneID);
		
		log.debug("Listed all items from fan zone with id " + fanZoneID);
		
		return items;
		
		
		
		
	}
	
	
	
}
