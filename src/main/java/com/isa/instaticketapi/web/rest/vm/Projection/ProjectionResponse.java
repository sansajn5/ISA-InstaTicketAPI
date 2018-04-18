package com.isa.instaticketapi.web.rest.vm.Projection;

import com.isa.instaticketapi.domain.Projection;
import com.isa.instaticketapi.service.dto.projection.SeatDTO;

import java.util.ArrayList;
import java.util.List;

public class ProjectionResponse {

	private Projection projection;

	private List<SeatDTO> seats = new ArrayList<>();

	public ProjectionResponse() {
		super();
		// TODO Auto-generated constructor stub
	}

	public ProjectionResponse(Projection projection,List<SeatDTO> seats) {
		super();
		this.seats = seats;
		this.projection = projection;
	}

	public Projection getProjection() {
		return projection;
	}

	public void setProjection(Projection projection) {
		this.projection = projection;
	}

	public List<SeatDTO> getSeats() {
		return seats;
	}

	public void setSeats(List<SeatDTO> seats) {
		this.seats = seats;
	}
}
