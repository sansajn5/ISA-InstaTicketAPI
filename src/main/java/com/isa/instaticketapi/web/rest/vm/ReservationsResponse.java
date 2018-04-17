package com.isa.instaticketapi.web.rest.vm;

import java.util.ArrayList;

import com.isa.instaticketapi.domain.ReservationState;

public class ReservationsResponse {

	private ArrayList<ReservationState> reservations;

	public ReservationsResponse() {
		super();
		// TODO Auto-generated constructor stub
	}

	public ReservationsResponse(ArrayList<ReservationState> reservations) {
		super();
		this.reservations = reservations;
	}

	public ArrayList<ReservationState> getReservations() {
		return reservations;
	}

	public void setReservations(ArrayList<ReservationState> reservations) {
		this.reservations = reservations;
	}

}
