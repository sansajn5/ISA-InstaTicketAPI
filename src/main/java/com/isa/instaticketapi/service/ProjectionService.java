package com.isa.instaticketapi.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.isa.instaticketapi.domain.Place;
import com.isa.instaticketapi.domain.Projection;
import com.isa.instaticketapi.repository.ProjectionRepository;
import com.isa.instaticketapi.service.dto.ChangePlaceDTO;
import com.isa.instaticketapi.service.dto.ChangeProjectionDTO;
import com.isa.instaticketapi.service.dto.ProjectionDTO;

/**
 * Service for managing projection.
 * 
 * @author Milica Kovacevic
 *
 */
@Service
@Transactional
public class ProjectionService {
	private final Logger log = LoggerFactory.getLogger(UserService.class);

	@Autowired
	private ProjectionRepository projectionRepository;

	/**
	 * 
	 * @param projectionDTO
	 *            object providing information about new projection
	 */
	public void createProjection(ProjectionDTO projectionDTO) {
		Projection projection = new Projection();
		projection.setId(projectionDTO.getId());
		projection.setName(projectionDTO.getName());
		projection.setActors(projectionDTO.getActors());
		projection.setDirector(projectionDTO.getDirector());
		projection.setDuration(projectionDTO.getDuration());
		projection.setDescription(projectionDTO.getDescription());
		projection.setType(projectionDTO.getType());

		projectionRepository.save(projection);

	}

	/**
	 * 
	 * @param id
	 * @return object projection
	 */

	public Projection getProjection(Long id) {
		return projectionRepository.findOneById(id);
	}

	/**
	 * 
	 * @param ChangeProjectionDTO
	 *            object for editing
	 * @param id
	 *            id of object
	 */

	public void changeProjection(ChangeProjectionDTO changeProjectionDTO, long id) {
		Projection projection = projectionRepository.findOneById(id);
		projection.setName(changeProjectionDTO.getName());
		projection.setActors(changeProjectionDTO.getActors());
		projection.setDescription(changeProjectionDTO.getDescription());
		projection.setDirector(changeProjectionDTO.getDirector());
		projection.setDuration(changeProjectionDTO.getDuration());
		projection.setType(changeProjectionDTO.getType());

		projectionRepository.save(projection);
	}
}
