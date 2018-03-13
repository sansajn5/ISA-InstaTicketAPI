package com.isa.instaticketapi.web.rest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
/**
 * REST controller for managing the current projection.
 * @author Milica Kovacevic
 *
 */

import com.isa.instaticketapi.domain.Projection;
import com.isa.instaticketapi.service.ProjectionService;
import com.isa.instaticketapi.service.dto.places.ChangeProjectionDTO;
import com.isa.instaticketapi.service.dto.places.ProjectionDTO;
import com.isa.instaticketapi.web.rest.vm.ProjectionResponse.ProjectionResponse;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@RestController
@RequestMapping("/api/projection")
public class ProjectionResource {
	private final Logger log = LoggerFactory.getLogger(AccountResource.class);

	@Autowired
	private ProjectionService projectionService;

	/**
	 * 
	 * @param projectionDTO
	 *            object providing information about new projection
	 */

	@ApiOperation(value = "Creating new projection", response = ProjectionDTO.class)
	@ApiResponses(value = { @ApiResponse(code = 201, message = "Succesfully created projection"),
			@ApiResponse(code = 400, message = "Some attribute is already in use"),
			@ApiResponse(code = 401, message = "You are not authorized to view the resource"),
			@ApiResponse(code = 403, message = "Accessing the resource you were trying to reach is forbidden"),
			@ApiResponse(code = 404, message = "The resource you were trying to reach is not found"),
			@ApiResponse(code = 500, message = "Error on server side"),
			@ApiResponse(code = 503, message = "Server is unavilable or under maintance") })
	@Transactional
	@PostMapping("/createProjection")
	public void createProjection(@RequestBody ProjectionDTO projectionDTO) {
		log.debug("REST request to create Projection : {}", projectionDTO);
		projectionService.createProjection(projectionDTO);
	}

	/**
	 * GET: /getProjection: return all data for concrete projection.
	 * 
	 * @param id
	 *            id of projection about whom I am looking for information
	 * @return object projection
	 */
	@ApiOperation(value = "All data for projection.", response = ProjectionResponse.class)
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Successfully"),
			@ApiResponse(code = 401, message = "You are not authorized to view the resource"),
			@ApiResponse(code = 403, message = "Accessing the resource you were trying to reach is forbidden"),
			@ApiResponse(code = 404, message = "The resource you were trying to reach is not found"),
			@ApiResponse(code = 500, message = "Error on server side"),
			@ApiResponse(code = 503, message = "Server is unavilable or under maintance") })

	@GetMapping("getProjection/{id}")
	public ResponseEntity<ProjectionResponse> getProjection(@PathVariable("id") Long id) {
		Projection projection = projectionService.getProjection(id);
		return new ResponseEntity<>(new ProjectionResponse(projection), HttpStatus.OK);
	}

	/**
	 * POST: editProjection/{id} : edit data about concrete projection
	 * 
	 * @param changeProjectionDTO
	 *            data for editing
	 * @param id
	 *            id from projection we want to change
	 */
	@ApiOperation(value = "Edit projection")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Successfully"),
			@ApiResponse(code = 401, message = "You are not authorized to view the resource"),
			@ApiResponse(code = 403, message = "Accessing the resource you were trying to reach is forbidden"),
			@ApiResponse(code = 404, message = "The resource you were trying to reach is not found"),
			@ApiResponse(code = 500, message = "Error on server side"),
			@ApiResponse(code = 503, message = "Server is unavilable or under maintance") })

	@PostMapping("/editProjection/{id}")
	public void editProjection(@RequestBody ChangeProjectionDTO changeProjectionDTO, @PathVariable("id") Long id) {
		projectionService.changeProjection(changeProjectionDTO, id);
	}

	/**
	 * POST: deleteProjection/{id} : delete data about concrete projection
	 * 
	 * @param id
	 *            id from projection we want to delete
	 */
	@ApiOperation(value = "Delete projection")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Successfully"),
			@ApiResponse(code = 401, message = "You are not authorized to view the resource"),
			@ApiResponse(code = 403, message = "Accessing the resource you were trying to reach is forbidden"),
			@ApiResponse(code = 404, message = "The resource you were trying to reach is not found"),
			@ApiResponse(code = 500, message = "Error on server side"),
			@ApiResponse(code = 503, message = "Server is unavilable or under maintance") })

	@PostMapping("/deleteProjection/{id}")
	public void deleteProjection(@PathVariable("id") Long id) {
		projectionService.deleteProj(id);
	}
}
