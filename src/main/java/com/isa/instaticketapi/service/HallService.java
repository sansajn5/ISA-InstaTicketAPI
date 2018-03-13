package com.isa.instaticketapi.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
/**
 * 
 * @author Milica
 * Service for hall in theaters or cinemas.
 *
 */

import com.isa.instaticketapi.domain.Hall;
import com.isa.instaticketapi.domain.Place;
import com.isa.instaticketapi.repository.HallRepository;
import com.isa.instaticketapi.repository.PlaceRepository;
import com.isa.instaticketapi.service.dto.places.HallDTO;

@Service
@Transactional
public class HallService {
	private final Logger log = LoggerFactory.getLogger(UserService.class);

	@Autowired
	private HallRepository hallRepository;
	
	@Autowired
	private PlaceRepository placerepository;

	public void createHall(HallDTO hallDTO,Long id) {
		Hall hall=new Hall();
		Place place = placerepository.findOneById(id);
		
		hall.setId(hallDTO.getId());
		hall.setCreatedBy(hallDTO.getCreatedBy());
		hall.setCreatedDate(hallDTO.getCreatedDate());
		hall.setName(hallDTO.getName());
		hall.setCol(hallDTO.getCol());
		hall.setRow(hallDTO.getRow());
		hall.setPlace(place);
		
		hallRepository.save(hall);
	}

}
