package com.isa.instaticketapi.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.isa.instaticketapi.domain.Place;
import com.isa.instaticketapi.domain.User;
import com.isa.instaticketapi.domain.VoteForPlace;
import com.isa.instaticketapi.repository.PlaceRepository;
import com.isa.instaticketapi.repository.UserRepository;
import com.isa.instaticketapi.repository.VoteForPlaceRepository;
import com.isa.instaticketapi.security.SecurityUtils;
import com.isa.instaticketapi.service.dto.VoteForPlaceDTO;

/**
 * services for vote for place
 * 
 * @author Milica
 *
 */
@Service
@Transactional
public class VoteForPlaceService {

	@Autowired
	private PlaceRepository placeRepository;

	@Autowired
	private VoteForPlaceRepository voteForPlaceRepository;

	@Autowired
	private UserRepository userRepository;

	public void createVote(VoteForPlaceDTO voteForPlaceDTO) {
		VoteForPlace vote = new VoteForPlace();
		Place place = placeRepository.findOneByName(voteForPlaceDTO.getPlace());

		vote.setVote(voteForPlaceDTO.getVote());
		vote.setPlace(place);
		User logged = SecurityUtils.getCurrentUserLogin().flatMap(userRepository::findOneByUsername).get();
		vote.setUser(logged);

		voteForPlaceRepository.save(vote);

	}
	
	
}
