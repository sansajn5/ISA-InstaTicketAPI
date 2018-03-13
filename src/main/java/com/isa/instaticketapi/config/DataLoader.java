package com.isa.instaticketapi.config;

import java.util.HashSet;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import com.isa.instaticketapi.domain.Authority;
import com.isa.instaticketapi.domain.FanZone;
import com.isa.instaticketapi.domain.Item;
import com.isa.instaticketapi.domain.Place;
import com.isa.instaticketapi.domain.Projection;
import com.isa.instaticketapi.domain.User;
import com.isa.instaticketapi.repository.AuthorityRepository;
import com.isa.instaticketapi.repository.FanZoneRepository;
import com.isa.instaticketapi.repository.ItemRepository;
import com.isa.instaticketapi.repository.PlaceRepository;
import com.isa.instaticketapi.repository.ProjectionRepository;
import com.isa.instaticketapi.repository.UserRepository;
import com.isa.instaticketapi.security.AuthoritiesConstants;

/**
 * Class for making initial seed data
 *
 * @author sansajn
 */
@Component
public class DataLoader implements ApplicationRunner {

	@Autowired
	UserRepository userRepository;

	@Autowired
	AuthorityRepository authorityRepository;

	@Autowired
	ProjectionRepository projectionRepository;

	@Autowired
	PlaceRepository placeRepository;
	
	@Autowired
	FanZoneRepository fanZoneRepository;
	
	@Autowired
	ItemRepository itemRepository;
	
	
	
	
	///// TEST /////
	
	
	
	
	
	
	///////////////
	
	
	
	
	

	@Autowired
	private PasswordEncoder passwordEncoder;

	private final Logger log = LoggerFactory.getLogger(DataLoader.class);

	@Override
	public void run(ApplicationArguments applicationArguments) throws Exception {
		seedAuthorities();
		seedUsers();
		seedCinema();
		seedTheater();
		seedProjection();
		seedFanZone();
	}

	/**
	 * Setting up authorities for common init database
	 */
	public void seedAuthorities() {
		log.info("Starting seed for authority");
		Authority superAdmin = new Authority();
		Authority admin = new Authority();
		Authority user = new Authority();
		Authority guest = new Authority();

		superAdmin.setName(AuthoritiesConstants.SUPER_ADMIN);
		admin.setName(AuthoritiesConstants.ADMIN);
		user.setName(AuthoritiesConstants.USER);
		guest.setName(AuthoritiesConstants.ANONYMOUS);

		try {
			authorityRepository.save(superAdmin);
			authorityRepository.save(admin);
			authorityRepository.save(user);
			authorityRepository.save(guest);
		} catch (Exception e) {
			log.debug("items (authorities) are already in database");
		}

		log.info("Seeds for authority are completed");
	}

	/**
	 * Setting up users for common init database
	 */
	public void seedUsers() {
		log.info("Strting seed for users");
		Set<Authority> authoritiesSuperAdmin = new HashSet<>();
		Set<Authority> authoritiesAdmin = new HashSet<>();
		Set<Authority> authoritiesUser = new HashSet<>();
		Set<Authority> authoritiesGuest = new HashSet<>();

		Authority authoritySuperAdmin = authorityRepository.findOne(AuthoritiesConstants.SUPER_ADMIN);
		Authority authorityAdmin = authorityRepository.findOne(AuthoritiesConstants.ADMIN);
		Authority authorityUser = authorityRepository.findOne(AuthoritiesConstants.USER);
		Authority authorityGuest = authorityRepository.findOne(AuthoritiesConstants.ANONYMOUS);

		authoritiesSuperAdmin.add(authoritySuperAdmin);
		authoritiesSuperAdmin.add(authorityAdmin);
		authoritiesSuperAdmin.add(authorityAdmin);
		authoritiesSuperAdmin.add(authorityUser);
		authoritiesSuperAdmin.add(authorityGuest);

		authoritiesAdmin.add(authorityAdmin);
		authoritiesAdmin.add(authorityUser);
		authoritiesAdmin.add(authorityGuest);

		authoritiesUser.add(authorityUser);
		authoritiesUser.add(authorityGuest);

		User superAdmin = new User();
		User sansajn = new User();
		User milica = new User();
		User dejan = new User();

		superAdmin.setUsername("super");
		superAdmin.setPassword(passwordEncoder.encode("super"));
		superAdmin.setFirstName("SUPER_ADMIN");
		superAdmin.setLastName("SUPER");
		superAdmin.setAuthorities(authoritiesSuperAdmin);
		superAdmin.setActivated(true);
		superAdmin.setAuthorities(authoritiesSuperAdmin);
		superAdmin.setEmail("super@super.com");
		superAdmin.setCreatedBy("sansajn");

		sansajn.setUsername("sansajn");
		sansajn.setPassword(passwordEncoder.encode("sansajn"));
		sansajn.setFirstName("Nemanja");
		sansajn.setLastName("Mudrinic");
		sansajn.setAuthorities(authoritiesAdmin);
		sansajn.setActivated(true);
		sansajn.setEmail("sansajn@super.com");
		sansajn.setCreatedBy("sansajn");

		dejan.setUsername("dejan");
		dejan.setPassword(passwordEncoder.encode("dejan"));
		dejan.setFirstName("Dejan");
		dejan.setLastName("Mijic");
		dejan.setAuthorities(authoritiesAdmin);
		dejan.setActivated(true);
		dejan.setEmail("dejan@super.com");
		dejan.setCreatedBy("sansajn");

		milica.setUsername("milica");
		milica.setPassword(passwordEncoder.encode("milica"));
		milica.setFirstName("Milica");
		milica.setLastName("Kovacevic");
		milica.setAuthorities(authoritiesAdmin);
		milica.setActivated(true);
		milica.setEmail("milica@super.com");
		milica.setCreatedBy("sansajn");

		try {
			userRepository.save(superAdmin);
			userRepository.save(sansajn);
			userRepository.save(milica);
			userRepository.save(dejan);
		} catch (Exception e) {
			log.debug("items (users) are already in database");
		}

		log.info("Seeds for users are completed");

	}

	/**
	 * Setting up cinemas for common init database
	 */
	public void seedCinema() {
		log.info("Starting seed for cinemas");
		Place place = new Place();

		place.setName("Arena Cineplex");
		place.setType("Bioskop");
		place.setCreatedBy("Milica");
		place.setAddress("npk");

		try {

			placeRepository.save(place);

		} catch (Exception e) {
			log.debug("items (place) are already in database");
		}

		log.info("Seeds for place are completed");
	}

	/**
	 * Setting up theaters for common init database
	 */
	public void seedTheater() {
		log.info("Starting seed for theater");
		Place place = new Place();

		place.setName("Srpsko narodno pozoriste");
		place.setType("Pozoriste");
		place.setCreatedBy("Milica");
		place.setAddress("blah");

		try {

			placeRepository.save(place);

		} catch (Exception e) {
			log.debug("items (place) are already in database");
		}

		log.info("Seeds for place are completed");
	}

	/**
	 * Setting up projection for common init database
	 */
	public void seedProjection() {
		log.info("Starting seed for projection");
		Projection projection1 = new Projection();
		Projection projection2 = new Projection();

		projection1.setName("John Wick2");
		projection1.setActors("aaaa");
		projection1.setDescription("sssssss");
		projection1.setActors("milica,micko,mudri");
		projection1.setDirector("micko");
		projection1.setType("action");
		projection1.setDuration(170);
		projection1.setCreatedBy("Mudri");
		projection1.setImageUrl("johnWick2.jpg");

		projection2.setName("Montevideo");
		projection2.setActors("xxxxx");
		projection2.setDescription("wwwww");
		projection2.setActors("pera,laza");
		projection2.setDirector("mudri");
		projection2.setType("domaci");
		projection2.setDuration(130);
		projection2.setCreatedBy("Mudri");
		projection2.setImageUrl("montevideo.jpg");

		try {

			projectionRepository.save(projection1);
			projectionRepository.save(projection2);

		} catch (Exception e) {
			log.debug("items (projection) are already in database");
		}

		log.info("Seeds for projection are completed");
	}
	
	
	public void seedFanZone() {
		
		FanZone fz1 = new FanZone();
		fz1.setName("zona 1");
		fz1.setCreatedBy("Dejan");
		
		FanZone fz2 = new FanZone();
		fz2.setName("zona 2");
		fz2.setCreatedBy("Dejan");
		
		
		Item it1 = new Item();
		it1.setName("item 1");
		it1.setCreatedBy("Dejan");
		it1.setFanZone(fz1);
		
		
		Item it2 = new Item();
		it2.setName("item 2");
		it2.setCreatedBy("Dejan");
		it2.setFanZone(fz2);
		
		
		Item it3 = new Item();
		it3.setName("item 3");
		it3.setCreatedBy("Dejan");
		it3.setFanZone(fz1);
		
		
		try{
			
			fanZoneRepository.save(fz1);
			fanZoneRepository.save(fz2);
			
			
			itemRepository.save(it1);
			itemRepository.save(it2);
			itemRepository.save(it3);
			
		
		}catch(Exception e){
			e.printStackTrace();
		}
		
		
	}
	
	

	

}
