package com.isa.instaticketapi.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.isa.instaticketapi.domain.Projection;
import com.isa.instaticketapi.repository.ProjectionRepository;

import com.isa.instaticketapi.service.dto.places.ChangeProjectionDTO;
import com.isa.instaticketapi.service.dto.places.ProjectionDTO;

/**
 * Service for managing projection.
 * 
 * @author Milica Kovacevic
 *
 */
@Service
@Transactional
public class ProjectionService {
	private final Logger log = LoggerFactory.getLogger(ProjectionService.class);

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
		projection.setImageUrl(projectionDTO.getImageUrl());
		projection.setCreatedBy(projectionDTO.getCreatedBy());
		projection.setCreatedDate(projectionDTO.getCreatedDate());

		projectionRepository.save(projection);

	}

	/**
	 * 
	 * @param id
	 * @return object projection
	 */

	public Projection getProjection(Long id) {
		if (projectionRepository.findOneById(id) == null) {
			return null;
		}
		return projectionRepository.findOneById(id);
	}

	/**
	 * 
	 * @param ChangeProjectionDTO
	 *            object for editing
	 * @param id
	 *            id of object
	 */

	public Projection changeProjection(ChangeProjectionDTO changeProjectionDTO, long id) {
		Projection projection = projectionRepository.findOneById(id);
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
		projection.setLastModifiedBy(changeProjectionDTO.getLastModifiedBy());
		projection.setLastModifiedDate(changeProjectionDTO.getLastModifiedDate());

		projectionRepository.save(projection);
		return projection;
	}

	/**
	 * Deleting projection from database
	 * 
	 * @param id
	 *            representing id of projection which will be deleted
	 */
	public Projection deleteProj(Long id) {
		Projection projection = projectionRepository.findOneById(id);
		if (projection == null) {
			return null;
		}
		projectionRepository.delete(projection);
		log.debug("Deleted projection.");
		return projection;
		

	}
}
