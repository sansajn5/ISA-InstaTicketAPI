package com.isa.instaticketapi.service;

import java.sql.SQLException;
import java.util.List;

import javax.transaction.Transactional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.isa.instaticketapi.domain.Item;
import com.isa.instaticketapi.domain.Offer;
import com.isa.instaticketapi.domain.OfferRequest;
import com.isa.instaticketapi.repository.ItemRepository;
import com.isa.instaticketapi.repository.OfferRepository;
import com.isa.instaticketapi.repository.OfferRequestRepository;
import com.isa.instaticketapi.repository.UserRepository;
import com.isa.instaticketapi.service.dto.ChangeItemDTO;
import com.isa.instaticketapi.service.dto.ChangeOfferDTO;
import com.isa.instaticketapi.service.dto.ItemDTO;
import com.isa.instaticketapi.service.dto.OfferDTO;

@Service
@Transactional
public class FanZoneService {
	
	private final Logger log = LoggerFactory.getLogger(UserService.class);
	
	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private OfferRepository offerRepository;
	
	@Autowired
	private ItemRepository itemRepository;
	
	@Autowired
	private OfferRequestRepository offerRequestRepository;
	
	
	public List<Item> getItems() {
				
		return itemRepository.findAll();
	}

	
	
	public Item addNewItem(ItemDTO itemDTO) throws SQLException{
		
		Item item = new Item();
		
		/*
		User logged = SecurityUtils.getCurrentUserLogin().flatMap(userRepository::findOneByUsername).get();
		System.out.println("ULOGOVAN " + logged.getUsername()); */
		
		
		item.setCreatedBy("Dejan");
		
		item.setName(itemDTO.getName());
		item.setDescription(itemDTO.getDescription());
		item.setImage(itemDTO.getImage());
		
		itemRepository.save(item);
		
		return item;	
		
	}
	
	
	
	public Item deleteItem(Long id) {
		
		Item item = itemRepository.findOneById(id);
		
		if(item == null) {
			return null;
		}
		
		itemRepository.delete(item);
		
		return item;
	}
	
	
	public Item editItem(ChangeItemDTO itemDTO) throws SQLException {
		
		Item item = itemRepository.findOneById(itemDTO.getId());
		
		if(item == null) {
			return null;
		}
		
		
		item.setName(itemDTO.getName());
		item.setDescription(itemDTO.getDescription());
		item.setImage(itemDTO.getImage());
		
		itemRepository.save(item);
		
		return item;
		
	}
	
	
	public List<Offer> getOffers() {
		
		return offerRepository.findAll();
	}
	
	
	public Offer addNewOffer(OfferDTO offerDTO) throws SQLException{
		
		Offer offer = new Offer();
		
		/*
		User logged = SecurityUtils.getCurrentUserLogin().flatMap(userRepository::findOneByUsername).get();
		System.out.println("ULOGOVAN " + logged.getUsername()); */
		
		
		offer.setCreatedBy("Dejan");
		
		offer.setName(offerDTO.getName());
		offer.setDescription(offerDTO.getDescription());
		offer.setImage(offerDTO.getImage());
		
		offerRepository.save(offer);
		
		OfferRequest offerRequest = new OfferRequest();
		offerRequest.setOffer(offer);
		offerRequest.setCreatedBy("User");
		offerRequestRepository.save(offerRequest);
		
		
		
		return offer;	
		
	}
	
	
	public Offer deleteOffer(Long id) {
		
		Offer offer = offerRepository.findOneById(id);
		
		if(offer == null) {
			return null;
		}
		
		offerRepository.delete(offer);
		
		return offer;
	}
	
	
	
	public Offer editOffer(ChangeOfferDTO offerDTO) throws SQLException {
		
		Offer offer = offerRepository.findOneById(offerDTO.getId());
		
		if(offer == null) {
			return null;
		}
		
		
		offer.setName(offerDTO.getName());
		offer.setDescription(offerDTO.getDescription());
		offer.setImage(offerDTO.getImage());
		
		offerRepository.save(offer);
		
		return offer;
		
	}
	
	
}
