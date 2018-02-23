package com.isa.instaticketapi.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.isa.instaticketapi.domain.Projection;
import com.isa.instaticketapi.repository.ProjectionRepository;
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
	 * @param projectionDTO object providing information about new projection
	 */
	public void createProjection(ProjectionDTO projectionDTO) {
		Projection projection = new Projection();
		projection.setId(projectionDTO.getId());
		projection.setName(projectionDTO.getName());
		projection.setActors(projectionDTO.getActors());
		projection.setDirector(projectionDTO.getDirector());

		projection.setDescription(projectionDTO.getDescription());
		projection.setType(projectionDTO.getType());

		projectionRepository.save(projection);

	}
}
