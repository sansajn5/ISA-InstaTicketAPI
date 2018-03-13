package com.isa.instaticketapi.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
/**
 * 
 * @author Milica
 * Service for hall in theaters or cinemas.
 *
 */

import com.isa.instaticketapi.domain.Hall;
import com.isa.instaticketapi.repository.HallRepository;
import com.isa.instaticketapi.service.dto.places.HallDTO;

@Service
@Transactional
public class HallService {
	private final Logger log = LoggerFactory.getLogger(UserService.class);

	@Autowired
	private HallRepository hallRepository;

	public void createHall(HallDTO hallDTO) {
		Hall hall=new Hall();
		hall.setId(hallDTO.getId());
	}

}
