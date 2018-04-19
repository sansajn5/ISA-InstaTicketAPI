package com.isa.instaticketapi.web.rest;

import java.util.ArrayList;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.isa.instaticketapi.domain.ReservationState;
import com.isa.instaticketapi.service.ReservationService;
import com.isa.instaticketapi.web.rest.vm.ReservationsResponse;
import com.isa.instaticketapi.web.rest.vm.UserResource.FriendsResponse;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@RestController
@RequestMapping("api/reservation")
public class ReservationResource {

	@Autowired
	private ReservationService reservationService;

	@ApiOperation(value = "Get user reservations", response = FriendsResponse.class)
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Succesfully"),
			@ApiResponse(code = 401, message = "You are not authorized to view the resource"),
			@ApiResponse(code = 403, message = "Accessing the resource you were trying to reach is forbidden"),
			@ApiResponse(code = 404, message = "The resource you were trying to reach is not found"),
			@ApiResponse(code = 500, message = "Error on server side"),
			@ApiResponse(code = 503, message = "Server is unavilable or under maintance") })
	@GetMapping("/my-reservations")
	public ResponseEntity<ReservationsResponse> getUserReservation() {
		ArrayList<ReservationState> reservations = reservationService.getMyUsedReservation();
		return new ResponseEntity<>(new ReservationsResponse(reservations), HttpStatus.OK);
	}

	@ApiOperation(value = "Get user active reservations", response = FriendsResponse.class)
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Succesfully"),
			@ApiResponse(code = 401, message = "You are not authorized to view the resource"),
			@ApiResponse(code = 403, message = "Accessing the resource you were trying to reach is forbidden"),
			@ApiResponse(code = 404, message = "The resource you were trying to reach is not found"),
			@ApiResponse(code = 500, message = "Error on server side"),
			@ApiResponse(code = 503, message = "Server is unavilable or under maintance") })
	@GetMapping("/active/my-reservations")
	public ResponseEntity<ReservationsResponse> getUserActiveReservation() {
		ArrayList<ReservationState> reservations = reservationService.getMyActiveReservations();
		return new ResponseEntity<>(new ReservationsResponse(reservations), HttpStatus.OK);
	}
}
