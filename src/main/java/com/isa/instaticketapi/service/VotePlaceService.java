package com.isa.instaticketapi.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service for managing vote for place
 * 
 * @author Milica Kovacevic
 *
 */
@Service
@Transactional
public class VotePlaceService {
	private final Logger log = LoggerFactory.getLogger(VotePlaceService.class);
}
