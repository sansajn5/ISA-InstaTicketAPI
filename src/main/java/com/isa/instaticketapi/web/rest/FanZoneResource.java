package com.isa.instaticketapi.web.rest;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
	
	private final Logger log = LoggerFactory.getLogger(AccountResource.class);
	
	private FanZoneRepository fanZoneRepository;
	
	private FanZoneService fanZoneService;

	private ItemRepository itemRepository;
	
	public ResponseEntity<ItemsResponse> getItems(@PathVariable("id") Long id) {

		List<Item> items = fanZoneService.getItems(id);
		return new ResponseEntity<>(new ItemsResponse(items), HttpStatus.OK);
		
		
	}
}
