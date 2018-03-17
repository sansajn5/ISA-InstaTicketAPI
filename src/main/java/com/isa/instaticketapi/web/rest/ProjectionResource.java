package com.isa.instaticketapi.web.rest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.isa.instaticketapi.repository.ProjectionRepository;
import com.isa.instaticketapi.service.ProjectionService;
import com.isa.instaticketapi.service.dto.projection.ProjectionDTO;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@RestController
@RequestMapping("/api/projection")
public class ProjectionResource {

	private final Logger log = LoggerFactory.getLogger(ProjectionResource.class);
	@Autowired
	private ProjectionService projectionService;

	@Autowired
	private ProjectionRepository projectionRepository;

	@ApiOperation(value = "Creating new projection", response = HttpStatus.class)
	@ApiResponses(value = { @ApiResponse(code = 201, message = "Succesfully created projection"),
			@ApiResponse(code = 400, message = "Some attribute is already in use"),
			@ApiResponse(code = 401, message = "You are not authorized to view the resource"),
			@ApiResponse(code = 403, message = "Accessing the resource you were trying to reach is forbidden"),
			@ApiResponse(code = 404, message = "The resource you were trying to reach is not found"),
			@ApiResponse(code = 500, message = "Error on server side"),
			@ApiResponse(code = 503, message = "Server is unavilable or under maintance") })

	@PostMapping("{id}/create-projection")
	public void createProjection(@RequestBody ProjectionDTO projectionDTO, @PathVariable("id") Long id) {
		log.debug("REST request to create Projection : {}", projectionDTO);
		projectionService.createProjection(projectionDTO, id);
	}

	/**
	 * 
	 * @param id
	 *            id of projection for deleting
	 */

	@ApiOperation(value = "Delete projection", response = HttpStatus.class)
	@ApiResponses(value = { @ApiResponse(code = 201, message = "Succesfully deleted projection"),
			@ApiResponse(code = 400, message = "Some attribute is already in use"),
			@ApiResponse(code = 401, message = "You are not authorized to view the resource"),
			@ApiResponse(code = 403, message = "Accessing the resource you were trying to reach is forbidden"),
			@ApiResponse(code = 404, message = "The resource you were trying to reach is not found"),
			@ApiResponse(code = 500, message = "Error on server side"),
			@ApiResponse(code = 503, message = "Server is unavilable or under maintance") })

	@DeleteMapping("delete-projection/{id}")
	public void deleteProjection(@PathVariable("id") Long id) {
		if (projectionRepository.findOneById(id) == null) {
			throw new IllegalArgumentException("Invalid id!");
		}
		projectionService.deleteProjection(id);
	}
}
