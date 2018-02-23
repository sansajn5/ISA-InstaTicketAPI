package com.isa.instaticketapi.web.rest.vm.ProjectionResponse;

import com.isa.instaticketapi.domain.Projection;

public class ProjectionResponse {
	Projection projection;

	public ProjectionResponse(Projection projection) {
		super();
		this.projection = projection;
	}

	public Projection getProjection() {
		return projection;
	}

	public void setProjection(Projection projection) {
		this.projection = projection;
	}

}
