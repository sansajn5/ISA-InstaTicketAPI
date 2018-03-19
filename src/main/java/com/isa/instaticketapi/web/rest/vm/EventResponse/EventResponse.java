package com.isa.instaticketapi.web.rest.vm.EventResponse;

import com.isa.instaticketapi.domain.Event;

public class EventResponse {
	Event event;

	public EventResponse(Event projection) {
		super();
		this.event = projection;
	}

	public Event getProjection() {
		return event;
	}

	public void setProjection(Event projection) {
		this.event = projection;
	}

}
