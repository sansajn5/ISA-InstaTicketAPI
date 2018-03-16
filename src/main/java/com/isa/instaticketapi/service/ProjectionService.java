package com.isa.instaticketapi.service;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.isa.instaticketapi.domain.Event;
import com.isa.instaticketapi.domain.Hall;
import com.isa.instaticketapi.domain.Place;
import com.isa.instaticketapi.domain.Projection;
import com.isa.instaticketapi.domain.Repertory;
import com.isa.instaticketapi.domain.User;
import com.isa.instaticketapi.repository.EventRepository;
import com.isa.instaticketapi.repository.HallRepository;
import com.isa.instaticketapi.repository.ProjectionRepository;
import com.isa.instaticketapi.repository.RepertotyRepository;
import com.isa.instaticketapi.repository.UserRepository;
import com.isa.instaticketapi.security.SecurityUtils;
import com.isa.instaticketapi.service.dto.projection.ProjectionDTO;

/**
 * Service for managing projection
 * 
 * @author Milica Kovacevic
 *
 */
@Service
@Transactional
public class ProjectionService {

	@Autowired
	private HallRepository hallRepository;

	@Autowired
	private EventRepository eventRepository;

	@Autowired
	private RepertotyRepository repertoryRepository;

	@Autowired
	private ProjectionRepository projectionRepository;
	
	@Autowired
	private UserRepository userRepository;

	public void createProjection(ProjectionDTO projectionDTO, Long id) {
		Projection projection = new Projection();

		Hall hall = hallRepository.findOneById(id);
		String eventName = projectionDTO.getEvent();
		Event event = eventRepository.findOneByName(eventName);
		Repertory repertory = repertoryRepository.findOneByDate(projectionDTO.getDate());

		ArrayList<Projection> projections = projectionRepository.findAll();

		for (int i = 0; i < projections.size(); i++) {
			if (((projections.get(i).getHall().getId()).equals(id))
					&& ((projections.get(i).getEvent().getId()).equals(event.getId()))
					&& ((projections.get(i).getStartTime()).equals(projectionDTO.getStartTime()))
					&& ((projections.get(i).getEndTime()).equals(projectionDTO.getEndTime()))) {
				throw new IllegalArgumentException("Projection already exist !");
			}
		}

		// User logged =
		// SecurityUtils.getCurrentUserLogin().flatMap(userRepository::findOneByUsername).get();
		// projection.setCreatedBy(logged.getUsername());
		projection.setCreatedBy("milica");
		projection.setHall(hall);

		projection.setEvent(event);

		if (repertory == null) {
			Repertory reprtory1 = new Repertory();
			reprtory1.setDate(projectionDTO.getDate());
			User logged = SecurityUtils.getCurrentUserLogin().flatMap(userRepository::findOneByUsername).get();
			projection.setCreatedBy(logged.getUsername());
			
			Place place=hall.getPlace();
			reprtory1.setPlace(place);
			
			repertoryRepository.save(reprtory1);
			projection.setReperotry(reprtory1);

		} else {
			projection.setReperotry(repertory);
		}

		// VALIDACIJA ZA POCETNO I KRAJNJE VREME
		projection.setDate(projectionDTO.getDate());
		projection.setStartTime(projectionDTO.getStartTime());
		projection.setEndTime(projectionDTO.getEndTime());
		projectionRepository.save(projection);

	}
}
