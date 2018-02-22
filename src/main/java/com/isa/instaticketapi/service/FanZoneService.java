package com.isa.instaticketapi.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.isa.instaticketapi.repository.FanZoneRepository;

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
public class FanZoneService {

	
	private final Logger log = LoggerFactory.getLogger(FanZoneService.class);
	
	private FanZoneRepository fanZoneRepository;
	
	
	/**
	 * 
	 * Showing all props in fan zone
	 * 
	 */
	
	public void showProps() {
		
		log.debug("Listing all props in fanzone of place");
		
	
	}
	
	
	
}
