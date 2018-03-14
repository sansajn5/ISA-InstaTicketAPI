package com.isa.instaticketapi.web.rest.vm.HallResponse;

import java.util.List;

import com.isa.instaticketapi.domain.Hall;

public class HallResponse {
	private List<Hall> halls;

	public List<Hall> getHalls() {
		return halls;
	}

	public void setHalls(List<Hall> halls) {
		this.halls = halls;
	}

	public HallResponse(List<Hall> halls) {
		super();
		this.halls = halls;
	}

}
