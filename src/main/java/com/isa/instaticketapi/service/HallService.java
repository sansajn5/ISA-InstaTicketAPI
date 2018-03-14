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

import com.isa.instaticketapi.domain.User;
import com.isa.instaticketapi.repository.HallRepository;
import com.isa.instaticketapi.repository.PlaceRepository;
import com.isa.instaticketapi.repository.UserRepository;
import com.isa.instaticketapi.security.SecurityUtils;
import com.isa.instaticketapi.service.dto.places.HallDTO;

@Service
@Transactional
public class HallService {
	private final Logger log = LoggerFactory.getLogger(HallService.class);

	@Autowired
	private HallRepository hallRepository;

	@Autowired
	private PlaceRepository placerepository;

	@Autowired
	private UserRepository userRepository;

	/**
	 * 
	 * @param hallDTO
	 *            object providing information about new hall
	 * @param id
	 *            id of place
	 */
	public void createHall(HallDTO hallDTO, Long id) {
		Hall hall = new Hall();
		Place place = placerepository.findOneById(id);
		User logged = SecurityUtils.getCurrentUserLogin().flatMap(userRepository::findOneByUsername).get();
		hall.setCreatedBy(logged.getUsername());
		
		hall.setName(hallDTO.getName());
		hall.setCol(hallDTO.getCol());
		hall.setRow(hallDTO.getRow());
		hall.setPlace(place);

		hallRepository.save(hall);
	}

	/**
	 * 
	 * @param id
	 *            id of hall for delete
	 * @return
	 */

	public Hall deleteHall(Long id) {
		Hall hall = hallRepository.findOneById(id);
		if (hall == null) {
			return null;
		}
		hallRepository.delete(hall);
		log.debug("Deleted hall.");
		return hall;

	}
}
