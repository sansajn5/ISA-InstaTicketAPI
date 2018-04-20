package com.isa.instaticketapi.service;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.isa.instaticketapi.domain.Authority;
import com.isa.instaticketapi.domain.EmploymentPlace;
import com.isa.instaticketapi.domain.Place;
import com.isa.instaticketapi.domain.User;
import com.isa.instaticketapi.repository.AuthorityRepository;
import com.isa.instaticketapi.repository.EmploymentPlaceRepository;
import com.isa.instaticketapi.repository.PlaceRepository;
import com.isa.instaticketapi.repository.UserRepository;
import com.isa.instaticketapi.security.AuthoritiesConstants;
import com.isa.instaticketapi.security.SecurityUtils;
import com.isa.instaticketapi.service.dto.account.AdminRoleDTO;

@Service
public class AdminService {

	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private AuthorityRepository authorityRepository;
	
	@Autowired
	private PlaceRepository placeRepository;
	
	@Autowired
	private EmploymentPlaceRepository employmentPlaceRepository;
	

	
	public List<User> getUsers() {
		
		List<User> users = userRepository.findAll();
		
		
		
		
		return users;
		
	}
	
	
	
	
	public User setSystemAdminRole(Long id) {
		
		User user = userRepository.findOneById(id).get();
		
		Authority authoritySuperAdmin = authorityRepository.findOne(AuthoritiesConstants.SUPER_ADMIN);
	
		if(!user.getAuthorities().contains(authoritySuperAdmin)) {		
			user.getAuthorities().add(authoritySuperAdmin);
			
			userRepository.save(user);
		}
		
		
		return user;
		
	}
	
	
	
	public User deleteSystemAdminRole(Long id) {
		
		User user = userRepository.findOneById(id).get();
		
		Authority authoritySuperAdmin = authorityRepository.findOne(AuthoritiesConstants.SUPER_ADMIN);
	
		if(user.getAuthorities().contains(authoritySuperAdmin)) {		
			user.getAuthorities().remove(authoritySuperAdmin);
			
			userRepository.save(user);
		}
		
		return user;
		
	}
	
	
	
	public User setFanZoneAdminRole(Long id) throws SQLException {
		
		
		
		User user = userRepository.findOneById(id).get();
		
		Authority authorityFanZone = authorityRepository.findOne(AuthoritiesConstants.FANZONE_ADMIN);
	
		if(!user.getAuthorities().contains(authorityFanZone)) {		
			user.getAuthorities().add(authorityFanZone);
			
			userRepository.save(user);
		}
		
		
		return user;
	}
	
	
	
	public User deleteFanZoneAdminRole(Long id) throws SQLException {
		
		
		
		User user = userRepository.findOneById(id).get();
		
		Authority authorityFanZone = authorityRepository.findOne(AuthoritiesConstants.FANZONE_ADMIN);
	
		if(user.getAuthorities().contains(authorityFanZone)) {		
			user.getAuthorities().remove(authorityFanZone);
			
			userRepository.save(user);
		}
		
		
		return user;
	}
	
	
	
	
	
	public void removeRole(Long id) {
		
		User user = userRepository.findOneById(id).get();
		
		Authority authorityBasicUser = authorityRepository.findOne(AuthoritiesConstants.USER);
		
		user.getAuthorities().clear();
		user.getAuthorities().add(authorityBasicUser);
		
	}
	
	
	
	public List<User> getEmployed(Long id) {
		
		Place place = placeRepository.findOneById(id);
		
		List<EmploymentPlace> employments = employmentPlaceRepository.findAllByPlace(place);
		
		User user = userRepository.findOneById(id).get();
		
		List<User> placeAdmins = new ArrayList<User>();
		
		for(EmploymentPlace ep : employments) {
			placeAdmins.add(ep.getAdmin());
		}
		
		return placeAdmins;
	}
	
	
	public User setPlaceAdminRole(AdminRoleDTO adminRoleDTO, Long id) throws SQLException {
		
		
		
		Place place = placeRepository.findOneById(adminRoleDTO.getId());
		
		List<EmploymentPlace> employments = employmentPlaceRepository.findAllByPlace(place);
		
		User user = userRepository.findOneById(id).get();
		
		List<User> placeAdmins = new ArrayList<User>();
		
		for(EmploymentPlace ep : employments) {
			placeAdmins.add(ep.getAdmin());
		}
		 
		if(!placeAdmins.contains(user)) {
			
			EmploymentPlace ep = new EmploymentPlace(place,user);
			User logged = SecurityUtils.getCurrentUserLogin().flatMap(userRepository::findOneByUsername).get();
			ep.setCreatedBy(logged.getUsername());			
			employmentPlaceRepository.save(ep);
			
		}
		
		return user;
		
	}
	
	
	
	public User deletePlaceAdminRole(AdminRoleDTO adminRoleDTO, Long id) throws SQLException {
		
		
		
		Place place = placeRepository.findOneById(adminRoleDTO.getId());
		
		List<EmploymentPlace> employments = employmentPlaceRepository.findAllByPlace(place);
		
		User user = userRepository.findOneById(id).get();
		
		
		
		for(EmploymentPlace ep : employments) {
			
			if(ep.getAdmin().getId().equals(id)) {
				
				employmentPlaceRepository.delete(ep);
			}
		}
		 
		
		
		return user;
		
	}
	
	
	
	
	
	
	
	
	
}
