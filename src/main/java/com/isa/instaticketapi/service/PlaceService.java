package com.isa.instaticketapi.service;

import java.util.List;

import com.isa.instaticketapi.config.Constants;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.isa.instaticketapi.domain.Place;
import com.isa.instaticketapi.domain.User;
import com.isa.instaticketapi.repository.PlaceRepository;
import com.isa.instaticketapi.repository.UserRepository;
import com.isa.instaticketapi.security.SecurityUtils;
import com.isa.instaticketapi.service.dto.places.ChangePlaceDTO;

/**
 * Service for managing cinema and theatres
 * 
 * @author Milica Kovacevic
 *
 */
@Service
@Transactional
public class PlaceService {
	private final Logger log = LoggerFactory.getLogger(PlaceService.class);

	@Autowired
	private PlaceRepository placeRepository;

	@Autowired
	private UserRepository userRepository;

	/**
	 * 
	 * @return list of all cinemas
	 */
	public List<Place> getCinemas() {
		return placeRepository.findAllByType(Constants.CINEMA);
	}

	/**
	 * 
	 * @return list of all theaters
	 */
	public List<Place> getTheaters() {
		return placeRepository.findAllByType(Constants.THEATER);
	}

	/**
	 * 
	 * @param id
	 * @return cinema or theater
	 */
	public Place getPlace(Long id) {
		return placeRepository.findOneById(id);
	}

	/**
	 * 
	 * @param changePlaceDTO
	 *            object for editing
	 * @param id
	 *            id of object
	 */

	public Place changePlace(ChangePlaceDTO changePlaceDTO, long id) {
		Place place = placeRepository.findOneById(id);
		if (place == null) {
			return null;
		}

		User logged = SecurityUtils.getCurrentUserLogin().flatMap(userRepository::findOneByUsername).get();
		place.setLastModifiedBy(logged.getUsername());
		place.setName(changePlaceDTO.getName());
		place.setAddress(changePlaceDTO.getAddress());
		place.setDescripton(changePlaceDTO.getDescripton());
		place.setType(changePlaceDTO.getType());
		placeRepository.save(place);
		return place;

	}
}
