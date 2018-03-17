package com.isa.instaticketapi.web.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.isa.instaticketapi.service.RepertoryService;
import com.isa.instaticketapi.service.dto.Repertory.RepertoryDTO;
import com.isa.instaticketapi.service.dto.places.EventDTO;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@RestController
@RequestMapping("/api/repertory")
public class RepertoryResource {

	@Autowired
	private RepertoryService repertoryService;

	/**
	 * 
	 * @param repertoryDTO
	 *            object for creating new repertory
	 * @param id
	 *            id of place for repertory
	 */
	@ApiOperation(value = "Creating new repertory", response = HttpStatus.class)
	@ApiResponses(value = { @ApiResponse(code = 201, message = "Succesfully created repertory"),
			@ApiResponse(code = 400, message = "Some attribute is already in use"),
			@ApiResponse(code = 401, message = "You are not authorized to view the resource"),
			@ApiResponse(code = 403, message = "Accessing the resource you were trying to reach is forbidden"),
			@ApiResponse(code = 404, message = "The resource you were trying to reach is not found"),
			@ApiResponse(code = 500, message = "Error on server side"),
			@ApiResponse(code = 503, message = "Server is unavilable or under maintance") })
	@PostMapping("/{id}/create-repertory")
	public void createRepertory(@RequestBody RepertoryDTO repertoryDTO, @PathVariable("id") Long id) {

		repertoryService.createRepertory(repertoryDTO, id);

	}

	/**
	 * 
	 * @param id
	 *            id of repertory
	 */
	@ApiOperation(value = "Deleting repertory.", response = HttpStatus.class)
	@ApiResponses(value = { @ApiResponse(code = 201, message = "Succesfully delete repertory"),
			@ApiResponse(code = 400, message = "Some attribute is already in use"),
			@ApiResponse(code = 401, message = "You are not authorized to view the resource"),
			@ApiResponse(code = 403, message = "Accessing the resource you were trying to reach is forbidden"),
			@ApiResponse(code = 404, message = "The resource you were trying to reach is not found"),
			@ApiResponse(code = 500, message = "Error on server side"),
			@ApiResponse(code = 503, message = "Server is unavilable or under maintance") })

	@DeleteMapping("delete-repertory/{id}")
	public void deleteRepertory(@PathVariable("id") Long id) {
		if (repertoryService.deleteRepertory(id) == null) {
			throw new IllegalArgumentException("Invalid id!");
		}
		repertoryService.deleteRepertory(id);
	}

}
