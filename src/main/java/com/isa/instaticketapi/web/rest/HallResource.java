package com.isa.instaticketapi.web.rest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
/**
 * 
 * @author milica
 *
 */

import com.isa.instaticketapi.repository.HallRepository;
import com.isa.instaticketapi.service.HallService;
import com.isa.instaticketapi.service.dto.places.HallDTO;
import com.isa.instaticketapi.service.dto.places.ProjectionDTO;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
@RestController
@RequestMapping("/api/hall")
public class HallResource {
	private final Logger log = LoggerFactory.getLogger(AccountResource.class);
	
	@Autowired
	private HallService hallService;
	
	@ApiOperation(value = "Creating new hall")
	@ApiResponses(value = { @ApiResponse(code = 201, message = "Succesfully created hall"),
			@ApiResponse(code = 400, message = "Some attribute is already in use"),
			@ApiResponse(code = 401, message = "You are not authorized to view the resource"),
			@ApiResponse(code = 403, message = "Accessing the resource you were trying to reach is forbidden"),
			@ApiResponse(code = 404, message = "The resource you were trying to reach is not found"),
			@ApiResponse(code = 500, message = "Error on server side"),
			@ApiResponse(code = 503, message = "Server is unavilable or under maintance") })
	@Transactional
	@PostMapping("/createHall/{id}")
	public void createHall(@RequestBody HallDTO hallDTO,@PathVariable("id") Long id) {
		log.debug("REST request to create Hall : {}", hallDTO);
		hallService.createHall(hallDTO,id);
	}

}
