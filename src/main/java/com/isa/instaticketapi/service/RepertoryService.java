package com.isa.instaticketapi.service;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.isa.instaticketapi.domain.Place;
import com.isa.instaticketapi.domain.Repertory;
import com.isa.instaticketapi.domain.User;
import com.isa.instaticketapi.repository.PlaceRepository;
import com.isa.instaticketapi.repository.RepertotyRepository;
import com.isa.instaticketapi.repository.UserRepository;
import com.isa.instaticketapi.security.SecurityUtils;
import com.isa.instaticketapi.service.dto.Repertory.RepertoryDTO;

/**
 * Service for managing repertory
 * 
 * @author Milica Kovacevic
 *
 */
@Service
@Transactional
public class RepertoryService {

	@Autowired
	private RepertotyRepository repertoryRepository;

	@Autowired
	private PlaceRepository placeRepository;

	@Autowired
	private UserRepository userRepository;

	public void createRepertory(RepertoryDTO repertoryDTO, Long id) {
		Place place = placeRepository.findOneById(id);
		if (place == null) {
			throw new IllegalArgumentException("Invalid id of place!");
		}
		Repertory repertory = new Repertory();
		User logged = SecurityUtils.getCurrentUserLogin().flatMap(userRepository::findOneByUsername).get();
		repertory.setCreatedBy(logged.getUsername());

		repertory.setPlace(place);
		ArrayList<Repertory> repertories = repertoryRepository.findAllByPlace(place);

		for (int i = 0; i < repertories.size(); i++) {
			if ((repertories.get(i).getDate()).equals(repertoryDTO.getDate())) {
				throw new IllegalArgumentException("Repertory already exist!");
			}
		}
		repertory.setDate(repertoryDTO.getDate());

		repertoryRepository.save(repertory);

	}
}
