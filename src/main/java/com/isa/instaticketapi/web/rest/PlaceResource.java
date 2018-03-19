package com.isa.instaticketapi.web.rest;

import java.sql.SQLException;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.isa.instaticketapi.domain.Place;
import com.isa.instaticketapi.repository.PlaceRepository;
import com.isa.instaticketapi.service.PlaceService;
import com.isa.instaticketapi.service.dto.places.ChangePlaceDTO;
import com.isa.instaticketapi.service.dto.places.EventDTO;
import com.isa.instaticketapi.service.dto.places.PlaceDTO;
import com.isa.instaticketapi.web.rest.vm.PlaceResource.CinemaResponse;
import com.isa.instaticketapi.web.rest.vm.PlaceResource.PlaceResponse;
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
	@ApiOperation(value = "List of all cinemas", response = CinemaResponse.class)
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Successfully"),
			@ApiResponse(code = 401, message = "You are not authorized to view the resource"),
			@ApiResponse(code = 403, message = "Accessing the resource you were trying to reach is forbidden"),
			@ApiResponse(code = 404, message = "The resource you were trying to reach is not found"),
			@ApiResponse(code = 500, message = "Error on server side"),
			@ApiResponse(code = 503, message = "Server is unavilable or under maintance") })

	@GetMapping("/Cinemas")
	public ResponseEntity<CinemaResponse> getCinemas() {
		List<Place> cinemas = placeService.getCinemas();
		return new ResponseEntity<>(new CinemaResponse(cinemas), HttpStatus.OK);
	}

	/**
	 * GET: /getCinemas: return list of all theaters.
	 * 
	 * @return list of all theaters
	 */
	@ApiOperation(value = "List of all theaters.", response = TheaterResponse.class)
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Successfully"),
			@ApiResponse(code = 401, message = "You are not authorized to view the resource"),
			@ApiResponse(code = 403, message = "Accessing the resource you were trying to reach is forbidden"),
			@ApiResponse(code = 404, message = "The resource you were trying to reach is not found"),
			@ApiResponse(code = 500, message = "Error on server side"),
			@ApiResponse(code = 503, message = "Server is unavilable or under maintance") })

	@GetMapping("/Theaters")
	public ResponseEntity<TheaterResponse> getTheaters() {
		List<Place> theaters = placeService.getTheaters();
		return new ResponseEntity<>(new TheaterResponse(theaters), HttpStatus.OK);
	}

	/**
	 * GET: /getPlace: return all data for concrete place.
	 * 
	 * @param id
	 *            id of place about whom I am looking for information
	 * @return place(theater or cinema)
	 */
	@ApiOperation(value = "All data for place.", response = PlaceResponse.class)
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Successfully"),
			@ApiResponse(code = 401, message = "You are not authorized to view the resource"),
			@ApiResponse(code = 403, message = "Accessing the resource you were trying to reach is forbidden"),
			@ApiResponse(code = 404, message = "The resource you were trying to reach is not found"),
			@ApiResponse(code = 500, message = "Error on server side"),
			@ApiResponse(code = 503, message = "Server is unavilable or under maintance") })

	@GetMapping("/Place/{id}")
	public ResponseEntity<PlaceResponse> getCinema(@PathVariable("id") Long id) {
		Place place = placeService.getPlace(id);
		if (place == null) {
			throw new IllegalArgumentException("Invalid id!");
		}
		return new ResponseEntity<>(new PlaceResponse(place), HttpStatus.OK);
	}

	/**
	 * POST: editPlace/{id} : edit data about concrete place
	 * 
	 * @param changePlaceDTO
	 *            data for editing
	 * @param id
	 *            id from place we want to change
	 * 
	 */
	@ApiOperation(value = "Edit place")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Successfully"),
			@ApiResponse(code = 401, message = "You are not authorized to view the resource"),
			@ApiResponse(code = 403, message = "Accessing the resource you were trying to reach is forbidden"),
			@ApiResponse(code = 404, message = "The resource you were trying to reach is not found"),
			@ApiResponse(code = 500, message = "Error on server side"),
			@ApiResponse(code = 503, message = "Server is unavilable or under maintance") })

	@PutMapping("/Place/{id}")
	public void editPlace(@RequestBody ChangePlaceDTO changePlaceDTO, @PathVariable("id") Long id) {
		if (placeService.changePlace(changePlaceDTO, id) == null) {
			throw new IllegalArgumentException("Invalid id!");
		}
		placeService.changePlace(changePlaceDTO, id);
	}
	
	
	
	/**
	 * POST: create-place : create new place by super-admin
	 * @throws SQLException 
	 * 
	 */
	@ApiOperation(value = "Creating new projection", response = EventDTO.class)
	@ApiResponses(value = { @ApiResponse(code = 201, message = "Succesfully created projection"),
			@ApiResponse(code = 400, message = "Some attribute is already in use"),
			@ApiResponse(code = 401, message = "You are not authorized to view the resource"),
			@ApiResponse(code = 403, message = "Accessing the resource you were trying to reach is forbidden"),
			@ApiResponse(code = 404, message = "The resource you were trying to reach is not found"),
			@ApiResponse(code = 500, message = "Error on server side"),
			@ApiResponse(code = 503, message = "Server is unavilable or under maintance") })
	@Transactional
	@PostMapping("/Place")
	public void createPlace(@RequestBody PlaceDTO placeDTO) throws SQLException{
		
		
		log.debug("Rest request to create new Place : {}");
		
		placeService.createPlace(placeDTO);
		
		
	}
	
	
	
	/**
	 * POST: deletePlace/{id} : delete data about concrete place
	 * 
	 * @param id
	 *            id from place we want to delete
	 */
	@ApiOperation(value = "Delete place")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Successfully"),
			@ApiResponse(code = 401, message = "You are not authorized to view the resource"),
			@ApiResponse(code = 403, message = "Accessing the resource you were trying to reach is forbidden"),
			@ApiResponse(code = 404, message = "The resource you were trying to reach is not found"),
			@ApiResponse(code = 500, message = "Error on server side"),
			@ApiResponse(code = 503, message = "Server is unavilable or under maintance") })

	@DeleteMapping("/Place/{id}")
	public void deleteProjection(@PathVariable("id") Long id) {
		if (placeService.deletePlace(id) == null) {
			throw new IllegalArgumentException("Invalid id!");
		}
		placeService.deletePlace(id);
	}
	
	
	
	
}
