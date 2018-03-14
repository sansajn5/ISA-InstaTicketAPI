package com.isa.instaticketapi.web.rest;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.isa.instaticketapi.domain.Item;
import com.isa.instaticketapi.repository.FanZoneRepository;
import com.isa.instaticketapi.repository.ItemRepository;
import com.isa.instaticketapi.service.FanZoneService;
import com.isa.instaticketapi.web.rest.vm.FanZoneResource.ItemsResponse;

@RestController
@RequestMapping("api/fanzone")
public class FanZoneResource {
	
	private final Logger log = LoggerFactory.getLogger(FanZoneResource.class);
	
	@Autowired
	private FanZoneRepository fanZoneRepository;
	
	@Autowired
	private FanZoneService fanZoneService;

	@Autowired
	private ItemRepository itemRepository;
	
	
	@GetMapping("getItems/{id}")
	public ResponseEntity<ItemsResponse> getItems(@PathVariable("id") Long id) {

		log.debug("Broj {}",id);
		List<Item> items = fanZoneService.getItems(id);
		log.debug("TEST {}", items);
		return new ResponseEntity<>(new ItemsResponse(items), HttpStatus.OK);
		
		
	}
}
