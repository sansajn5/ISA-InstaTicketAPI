package com.isa.instaticketapi.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.isa.instaticketapi.domain.Event;
import com.isa.instaticketapi.domain.User;
import com.isa.instaticketapi.repository.EventRepository;
import com.isa.instaticketapi.repository.UserRepository;
import com.isa.instaticketapi.security.SecurityUtils;
import com.isa.instaticketapi.service.dto.places.ChangeEventDTO;
import com.isa.instaticketapi.service.dto.places.EventDTO;

/**
 * Service for managing projection.
 * 
 * @author Milica Kovacevic
 *
 */
@Service
@Transactional
public class EventService {
	private final Logger log = LoggerFactory.getLogger(EventService.class);

	@Autowired
	private EventRepository projectionRepository;

	@Autowired
	private UserRepository userRepository;

	/**
	 * 
	 * @param projectionDTO
	 *            object providing information about new projection
	 */
	public void createProjection(EventDTO projectionDTO) {
		Event projection = new Event();
		projection.setName(projectionDTO.getName());
		projection.setActors(projectionDTO.getActors());
		projection.setDirector(projectionDTO.getDirector());
		projection.setDuration(projectionDTO.getDuration());
		projection.setDescription(projectionDTO.getDescription());
		projection.setType(projectionDTO.getType());
		projection.setImageUrl(projectionDTO.getImageUrl());
		User logged = SecurityUtils.getCurrentUserLogin().flatMap(userRepository::findOneByUsername).get();
		projection.setCreatedBy(logged.getUsername());

		projectionRepository.save(projection);

	}

	/**
	 * 
	 * @param id
	 * @return object projection
	 */

	public Event getProjection(Long id) {
		if (projectionRepository.findOneById(id) == null) {
			return null;
		}
		return projectionRepository.findOneById(id);
	}

	/**
	 * 
	 * @param ChangeEventDTO
	 *            object for editing
	 * @param id
	 *            id of object
	 */

	public Event changeProjection(ChangeEventDTO changeProjectionDTO, long id) {
		Event projection = projectionRepository.findOneById(id);
		if (projection == null) {
			return null;
		}
		projection.setName(changeProjectionDTO.getName());
		projection.setActors(changeProjectionDTO.getActors());
		projection.setDescription(changeProjectionDTO.getDescription());
		projection.setDirector(changeProjectionDTO.getDirector());
		projection.setDuration(changeProjectionDTO.getDuration());
		projection.setType(changeProjectionDTO.getType());
		projection.setImageUrl(changeProjectionDTO.getImageUrl());
		User logged = SecurityUtils.getCurrentUserLogin().flatMap(userRepository::findOneByUsername).get();
		projection.setLastModifiedBy(logged.getUsername());

		projectionRepository.save(projection);
		return projection;
	}

	/**
	 * Deleting projection from database
	 * 
	 * @param id
	 *            representing id of projection which will be deleted
	 */
	public Event deleteProj(Long id) {
		Event projection = projectionRepository.findOneById(id);
		if (projection == null) {
			return null;
		}
		projectionRepository.delete(projection);
		log.debug("Deleted projection.");
		return projection;

	}
}
