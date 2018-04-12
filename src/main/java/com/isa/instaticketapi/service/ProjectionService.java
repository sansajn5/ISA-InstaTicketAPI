package com.isa.instaticketapi.service;

import java.util.ArrayList;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
import com.isa.instaticketapi.repository.PlaceRepository;
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

	private final Logger log = LoggerFactory.getLogger(ProjectionService.class);

	@Autowired
	private HallRepository hallRepository;

	@Autowired
	private EventRepository eventRepository;

	@Autowired
	private RepertotyRepository repertoryRepository;

	@Autowired
	private ProjectionRepository projectionRepository;

	@Autowired
	private PlaceRepository placeRepository;

	@Autowired
	private UserRepository userRepository;

	public void createProjection(ProjectionDTO projectionDTO, Long id) {
		Projection projection = new Projection();

		Place place = placeRepository.findOneById(id);
		String hallName = projectionDTO.getHallName();
		String eventName = projectionDTO.getEventName();

		projection.setCreatedBy("milica");
		projection.setStartTime(projectionDTO.getStartTime());
		projection.setEndTime(projectionDTO.getEndTime());
		String date = projectionDTO.getDate();
		String[] dat = date.split("-");
		String datePares = dat[2] + '-' + dat[1] + '-' + dat[0];
		projection.setDate(datePares);

		ArrayList<Event> events = eventRepository.findAllByPlace(place);
		ArrayList<Hall> halls = hallRepository.findAllByPlace(place);
		// ako postoji vise sala sa istim nazivom u okviru razlicitih bioskopa
		Event e = new Event();
		for (int i = 0; i < events.size(); i++) {
			if ((events.get(i).getName()).equals(eventName)) {
				Event event = events.get(i);
				e = events.get(i);
				projection.setEvent(event);
			}
		}
		Hall h = new Hall();
		for (int i = 0; i < halls.size(); i++) {
			if ((halls.get(i).getName()).equals(hallName)) {
				Hall hall = halls.get(i);
				h = halls.get(i);
				projection.setHall(hall);
			}
		}

		// treba da nadje sve repertoare sa istim date-om pa da proveri da li su
		// u istom placu
		ArrayList<Repertory> repertories = repertoryRepository.findALLByDate(datePares);
		// ako nadje repertoare sa istim datumom da proveri da li je isti place
		// od sale i reprtoara
		log.debug("RRRRR {}", repertories);

		Repertory r = new Repertory();
		for (int i = 0; i < repertories.size(); i++) {
			Place placeRepertory = (repertories.get(i)).getPlace();
			if (placeRepertory.equals(place)) {
				r = repertories.get(i);
				projection.setReperotry(repertories.get(i));
				log.debug("AAAA {}", repertories.get(i));
				projection.setReperotry(r);
				break;
			}
		}
		// ako ne postoji repertoar sa unetim danom za projekciju da kreira
		// repertoar
		if (repertories.isEmpty()) {
			Repertory reprtory1 = new Repertory();
			reprtory1.setPlace(place);
			reprtory1.setCreatedBy("milica");
			reprtory1.setDate(datePares);

			repertoryRepository.save(reprtory1);
			projection.setReperotry(reprtory1);
		}

		// VALIDACIJA ZA POCETNO I KRAJNJE VREME
		// User logged
		// =SecurityUtils.getCurrentUserLogin().flatMap(userRepository::findOneByUsername).get();
		// projection.setCreatedBy(logged.getUsername());

		projectionRepository.save(projection);

	}

	/**
	 * 
	 * @param id
	 *            id of projection
	 */
	public void deleteProjection(Long id) {
		projectionRepository.delete(projectionRepository.findOneById(id));
	}

	/**
	 * 
	 * @param id
	 *            id of projection for edit
	 * @return Projection object
	 */
	public Projection getProjection(Long id) {
		return projectionRepository.findOneById(id);
	}
}
