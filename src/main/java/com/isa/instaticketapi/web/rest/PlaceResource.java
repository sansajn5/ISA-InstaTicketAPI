package com.isa.instaticketapi.web.rest;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.isa.instaticketapi.domain.Place;
import com.isa.instaticketapi.repository.PlaceRepository;
import com.isa.instaticketapi.service.PlaceService;
import com.isa.instaticketapi.web.rest.vm.PlaceResource.CinemaResponse;
import com.isa.instaticketapi.web.rest.vm.PlaceResource.TheaterResponse;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@RestController
@RequestMapping("/api/places")
public class PlaceResource {
	private final Logger log = LoggerFactory.getLogger(AccountResource.class);

	@Autowired
	private PlaceRepository placeRepository;

	@Autowired
	private PlaceService placeService;

	/**
	 * GET: /getCinemas: return list of all cinemas.
	 * 
	 * @return list of all cinemas
	 */
	@ApiOperation(value = "Activate the registered user", response = HttpStatus.class)
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Successfully"),
			@ApiResponse(code = 401, message = "You are not authorized to view the resource"),
			@ApiResponse(code = 403, message = "Accessing the resource you were trying to reach is forbidden"),
			@ApiResponse(code = 404, message = "The resource you were trying to reach is not found"),
			@ApiResponse(code = 500, message = "Error on server side"),
			@ApiResponse(code = 503, message = "Server is unavilable or under maintance") })

	@GetMapping("/getCinemas")
	public ResponseEntity<CinemaResponse> getCinemas() {
		List<Place> cinemas = placeService.getCinemas();
		return new ResponseEntity<>(new CinemaResponse(cinemas), HttpStatus.OK);
	}

	/**
	 * GET: /getCinemas: return list of all theaters.
	 * 
	 * @return list of all theaters
	 */
	@ApiOperation(value = "Activate the registered user", response = HttpStatus.class)
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Successfully"),
			@ApiResponse(code = 401, message = "You are not authorized to view the resource"),
			@ApiResponse(code = 403, message = "Accessing the resource you were trying to reach is forbidden"),
			@ApiResponse(code = 404, message = "The resource you were trying to reach is not found"),
			@ApiResponse(code = 500, message = "Error on server side"),
			@ApiResponse(code = 503, message = "Server is unavilable or under maintance") })

	@GetMapping("/getTheaters")
	public ResponseEntity<TheaterResponse> getTheaters() {
		List<Place> theaters = placeService.getTheaters();
		return new ResponseEntity<>(new TheaterResponse(theaters), HttpStatus.OK);
	}

}
