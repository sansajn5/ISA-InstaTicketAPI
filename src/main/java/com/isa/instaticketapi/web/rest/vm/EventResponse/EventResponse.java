package com.isa.instaticketapi.web.rest.vm.EventResponse;

import com.isa.instaticketapi.domain.Event;

public class EventResponse {
	Event projection;

	public EventResponse(Event projection) {
		super();
		this.projection = projection;
	}

	public Event getProjection() {
		return projection;
	}

	public void setProjection(Event projection) {
		this.projection = projection;
	}

}
