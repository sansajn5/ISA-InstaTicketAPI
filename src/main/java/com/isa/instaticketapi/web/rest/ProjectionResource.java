package com.isa.instaticketapi.web.rest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
/**
 * REST controller for managing the current projection.
 * @author Milica Kovacevic
 *
 */
@RestController
@RequestMapping("/api/projection")
public class ProjectionResource {
	private final Logger log = LoggerFactory.getLogger(AccountResource.class);
}
