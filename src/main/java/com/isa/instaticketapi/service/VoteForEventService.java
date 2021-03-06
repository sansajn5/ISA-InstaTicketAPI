package com.isa.instaticketapi.service;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.isa.instaticketapi.domain.Event;
import com.isa.instaticketapi.domain.Place;
import com.isa.instaticketapi.domain.Projection;
import com.isa.instaticketapi.domain.User;
import com.isa.instaticketapi.domain.VoteForEvent;
import com.isa.instaticketapi.domain.VoteForPlace;
import com.isa.instaticketapi.repository.EventRepository;
import com.isa.instaticketapi.repository.PlaceRepository;
import com.isa.instaticketapi.repository.ProjectionRepository;
import com.isa.instaticketapi.repository.UserRepository;
import com.isa.instaticketapi.repository.VoteForEventRepository;
import com.isa.instaticketapi.security.SecurityUtils;
import com.isa.instaticketapi.service.dto.projection.VoteForEventDTO;

/**
 * services for vote for event
 * 
 * @author Milica
 *
 */
@Service
@Transactional
public class VoteForEventService {
	@Autowired
	private PlaceRepository placeRepository;

	@Autowired
	private VoteForEventRepository voteForEventRepository;

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private EventRepository eventrepository;

	@Autowired
	private ProjectionRepository projectionRepository;

	public void createVote(VoteForEventDTO voteForEventDTO) {
		VoteForEvent vote = new VoteForEvent();

		Place place = placeRepository.findOneByName(voteForEventDTO.getPlace());
		ArrayList<Event> events = eventrepository.findAllByPlace(place);
		Event e = new Event();
		for (int i = 0; i < events.size(); i++) {
			if ((events.get(i).getName()).equals(voteForEventDTO.getEvent())) {
				e = events.get(i);
			}
		}

		String v = voteForEventDTO.getVote();
		vote.setVote(Integer.parseInt(v));
		vote.setEvent(e);
		User logged = SecurityUtils.getCurrentUserLogin().flatMap(userRepository::findOneByUsername).get();
		vote.setUser(logged);

		voteForEventRepository.save(vote);

		int voteSum = 0;
		ArrayList<VoteForEvent> votes = voteForEventRepository.findAllByEvent(e);
		for (int i = 0; i < votes.size(); i++) {
			voteSum += votes.get(i).getVote();
		}

		int vote1 = voteSum / votes.size();
		e.setVote(vote1);
		eventrepository.save(e);

	}

	public boolean checkIfHeVoted(Long id) {
		User logged = SecurityUtils.getCurrentUserLogin().flatMap(userRepository::findOneByUsername).get();
		Event event = eventrepository.findOneById(id);
		ArrayList<VoteForEvent> votes = voteForEventRepository.findAllByEvent(event);
		boolean isVote = false;
		for (int i = 0; i < votes.size(); i++) {
			if (votes.get(i).getUser().equals(logged)) {
				isVote = true;
				break;
			}
		}
		return isVote;
	}
}
