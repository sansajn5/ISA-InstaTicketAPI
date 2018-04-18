package com.isa.instaticketapi.service;

import javax.transaction.Transactional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.isa.instaticketapi.domain.Item;
import com.isa.instaticketapi.domain.ItemReservation;
import com.isa.instaticketapi.domain.User;
import com.isa.instaticketapi.repository.BidRepository;
import com.isa.instaticketapi.repository.ItemRepository;
import com.isa.instaticketapi.repository.ItemReservationRepository;
import com.isa.instaticketapi.repository.OfferRepository;
import com.isa.instaticketapi.repository.OfferRequestRepository;
import com.isa.instaticketapi.repository.UserRepository;
import com.isa.instaticketapi.security.SecurityUtils;
import com.isa.instaticketapi.service.dto.ItemReservationDTO;

@Service
@Transactional
public class ItemReservationService {
	
	private final Logger log = LoggerFactory.getLogger(UserService.class);
	
	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private OfferRepository offerRepository;
	
	@Autowired
	private ItemRepository itemRepository;
	
	@Autowired
	private OfferRequestRepository offerRequestRepository;
	
	@Autowired
	private BidRepository bidRepository;
	
	@Autowired
	private MailService mailService;
	
	
	@Autowired
	private ItemReservationRepository itemReservationRepository;
	
	
	// setovati parametre transakcije ?!
	
	@Transactional
	public Item itemReservation(Long id) {
		
		Item item = itemRepository.findOneById(id);
		
		return item;
	}
	
	@Transactional
	public ItemReservation confirmReservation(ItemReservationDTO itemReservationDTO) {
		
		User logged = SecurityUtils.getCurrentUserLogin().flatMap(userRepository::findOneByUsername).get();
		
		ItemReservation ir = new ItemReservation();
		ir.setCreatedBy(logged.getUsername());
		
		Long id = itemReservationDTO.getId();
		Item item = itemRepository.findOneById(id);
		
		ir.setItem(item);
		ir.setPlace(itemReservationDTO.getPlace());
		ir.setUser(itemReservationDTO.getUser());
		
		itemReservationRepository.save(ir);
		
		item.setSold(true);
		itemRepository.save(item);
		
		return ir;
	}
	

}
