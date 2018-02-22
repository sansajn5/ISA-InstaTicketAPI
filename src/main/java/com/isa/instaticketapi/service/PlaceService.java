package com.isa.instaticketapi.service;

import java.util.List;

import com.isa.instaticketapi.config.Constants;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.isa.instaticketapi.domain.Place;
import com.isa.instaticketapi.repository.PlaceRepository;
import com.isa.instaticketapi.service.dto.ChangePasswordDTO;
import com.isa.instaticketapi.service.dto.ChangePlaceDTO;

/**
 * Service for managing cinema and theatres
 * 
 * @author Milica Kovacevic
 *
 */
@Service
@Transactional
public class PlaceService {
	private final Logger log = LoggerFactory.getLogger(UserService.class);

	@Autowired
	private PlaceRepository placeRepository;

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

	public void changePlace(ChangePlaceDTO changePlaceDTO, long id) {
		Place place = placeRepository.findOneById(id);
		place.setName(changePlaceDTO.getName());
		place.setAddress(changePlaceDTO.getAddress());
		log.info(" " + changePlaceDTO.getName());
		log.info(" " + changePlaceDTO.getDescription());
		place.setDescripton(changePlaceDTO.getDescription());

		placeRepository.save(place);
	}
}
