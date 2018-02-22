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
import com.isa.instaticketapi.web.rest.vm.AccountResource.JWTTokenResponse;
import com.isa.instaticketapi.web.rest.vm.PlaceResource.CinemaResponse;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;


@RestController
@RequestMapping("/places")
public class PlaceResource {
	private final Logger log = LoggerFactory.getLogger(AccountResource.class);
	
	@Autowired
	private PlaceRepository placeRepository;

	@Autowired
	private PlaceService placeService;
	
	
	@GetMapping("/getCinemas")
	public ResponseEntity<CinemaResponse> getCinemas(){
		List<Place> cinemas = placeService.getCinemas();
		return new ResponseEntity<>(new CinemaResponse(cinemas), HttpStatus.OK);
	}
}
