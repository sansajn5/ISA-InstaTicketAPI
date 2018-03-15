package com.isa.instaticketapi.web.rest;

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
/**
 * REST controller for managing the current projection.
 * @author Milica Kovacevic
 *
 */

import com.isa.instaticketapi.domain.Event;
import com.isa.instaticketapi.service.EventService;
import com.isa.instaticketapi.service.dto.places.ChangeEventDTO;
import com.isa.instaticketapi.service.dto.places.EventDTO;
import com.isa.instaticketapi.web.rest.vm.EventResponse.EventResponse;

import ch.qos.logback.classic.spi.ThrowableProxyVO;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@RestController
@RequestMapping("/api/projection")
public class EventResource {
	private final Logger log = LoggerFactory.getLogger(AccountResource.class);

	@Autowired
	private EventService eventService;

	/**
	 * 
	 * @param EventDTO
	 *            object providing information about new projection
	 */

	@ApiOperation(value = "Creating new event", response = EventDTO.class)
	@ApiResponses(value = { @ApiResponse(code = 201, message = "Succesfully created event"),
			@ApiResponse(code = 400, message = "Some attribute is already in use"),
			@ApiResponse(code = 401, message = "You are not authorized to view the resource"),
			@ApiResponse(code = 403, message = "Accessing the resource you were trying to reach is forbidden"),
			@ApiResponse(code = 404, message = "The resource you were trying to reach is not found"),
			@ApiResponse(code = 500, message = "Error on server side"),
			@ApiResponse(code = 503, message = "Server is unavilable or under maintance") })
	@Transactional
	@PostMapping("/Event")
	public void createEvent(@RequestBody EventDTO eventDTO) {
		log.debug("REST request to create Event : {}", eventDTO);
		eventService.createEvent(eventDTO);
	}

	/**
	 * GET: /getProjection: return all data for concrete projection.
	 * 
	 * @param id
	 *            id of projection about whom I am looking for information
	 * @return object projection
	 */
	@ApiOperation(value = "All data for event.", response = EventResponse.class)
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Successfully"),
			@ApiResponse(code = 401, message = "You are not authorized to view the resource"),
			@ApiResponse(code = 403, message = "Accessing the resource you were trying to reach is forbidden"),
			@ApiResponse(code = 404, message = "The resource you were trying to reach is not found"),
			@ApiResponse(code = 500, message = "Error on server side"),
			@ApiResponse(code = 503, message = "Server is unavilable or under maintance") })

	@GetMapping("Event/{id}")
	public ResponseEntity<EventResponse> Event(@PathVariable("id") Long id) {
		if (eventService.getProjection(id) == null) {
			throw new IllegalArgumentException("Invalid id!");
		}
		Event event = eventService.getProjection(id);
		return new ResponseEntity<>(new EventResponse(event), HttpStatus.OK);
	}

	/**
	 * POST: editProjection/{id} : edit data about concrete projection
	 * 
	 * @param changeEventDTO
	 *            data for editing
	 * @param id
	 *            id from projection we want to change
	 */
	@ApiOperation(value = "Edit event")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Successfully"),
			@ApiResponse(code = 401, message = "You are not authorized to view the resource"),
			@ApiResponse(code = 403, message = "Accessing the resource you were trying to reach is forbidden"),
			@ApiResponse(code = 404, message = "The resource you were trying to reach is not found"),
			@ApiResponse(code = 500, message = "Error on server side"),
			@ApiResponse(code = 503, message = "Server is unavilable or under maintance") })

	@PutMapping("/Event/{id}")
	public void editProjection(@RequestBody ChangeEventDTO changeEventDTO, @PathVariable("id") Long id) {
		if (eventService.changeProjection(changeEventDTO, id) == null) {
			throw new IllegalArgumentException("Invalid id!");
		}
		eventService.changeProjection(changeEventDTO, id);
	}

	/**
	 * POST: deleteEvent/{id} : delete data about concrete event
	 * 
	 * @param id
	 *            id from event we want to delete
	 */
	@ApiOperation(value = "Delete Event")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Successfully"),
			@ApiResponse(code = 401, message = "You are not authorized to view the resource"),
			@ApiResponse(code = 403, message = "Accessing the resource you were trying to reach is forbidden"),
			@ApiResponse(code = 404, message = "The resource you were trying to reach is not found"),
			@ApiResponse(code = 500, message = "Error on server side"),
			@ApiResponse(code = 503, message = "Server is unavilable or under maintance") })

	@DeleteMapping("/Event/{id}")
	public void deleteEvent(@PathVariable("id") Long id) {
		if (eventService.deleteProj(id) == null) {
			throw new IllegalArgumentException("Invalid id!");
		}
		eventService.deleteProj(id);
	}
}
