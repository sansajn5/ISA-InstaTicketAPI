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

@Service
@Transactional
public class FanZoneService {
	
	private final Logger log = LoggerFactory.getLogger(UserService.class);
	
	@Autowired
	private FanZoneRepository fanZoneRepository;
	
	@Autowired
	private ItemRepository itemRepository;
	
	
	public List<Item> getItems(Long id) {
		
		return itemRepository.findAllByFanZone(id);
	}

}
