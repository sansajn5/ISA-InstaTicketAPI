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
import com.isa.instaticketapi.domain.Event;
import com.isa.instaticketapi.domain.FanZone;
import com.isa.instaticketapi.domain.Friends;
import com.isa.instaticketapi.domain.Hall;
import com.isa.instaticketapi.domain.Item;
import com.isa.instaticketapi.domain.Place;
import com.isa.instaticketapi.domain.Projection;
import com.isa.instaticketapi.domain.Repertory;
import com.isa.instaticketapi.domain.User;
import com.isa.instaticketapi.repository.AuthorityRepository;
import com.isa.instaticketapi.repository.EventRepository;
import com.isa.instaticketapi.repository.FanZoneRepository;
import com.isa.instaticketapi.repository.FriendsRepository;
import com.isa.instaticketapi.repository.HallRepository;
import com.isa.instaticketapi.repository.ItemRepository;
import com.isa.instaticketapi.repository.PlaceRepository;
import com.isa.instaticketapi.repository.ProjectionRepository;
import com.isa.instaticketapi.repository.RepertotyRepository;
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
	EventRepository eventRepository;

	@Autowired
	PlaceRepository placeRepository;

	@Autowired
	FanZoneRepository fanZoneRepository;

	@Autowired
	ItemRepository itemRepository;

	@Autowired
	FriendsRepository friendsRepository;

	@Autowired
	private HallRepository hallRepository;

	@Autowired
	private PasswordEncoder passwordEncoder;

	@Autowired
	private RepertotyRepository repertoryRepository;

	@Autowired
	private ProjectionRepository projectionRepository;

	private final Logger log = LoggerFactory.getLogger(DataLoader.class);

	@Override
	public void run(ApplicationArguments applicationArguments) throws Exception {
		seedAuthorities();
		seedUsers();
		seedCinema();
		seedTheater();
		seedEvent();
		seedFanZone();
		seedHall();
		seedFriends();
		seedRepertory();
		seedProjection();
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
		place.setAddress("Sutjeska 6");

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
		place.setAddress("Romanijska 2");

		try {

			placeRepository.save(place);

		} catch (Exception e) {
			log.debug("items (place) are already in database");
		}

		log.info("Seeds for place are completed");
	}

	/**
	 * Setting up event for common init database
	 */
	public void seedEvent() {
		log.info("Starting seed for event");

		Place place = new Place();

		place.setName("Cinestar4DX");
		place.setType("Bioskop");
		place.setCreatedBy("Milica");

		Event event1 = new Event();
		Event event2 = new Event();

		event1.setName("John Wick2");
		event1.setActors("aaaa");
		event1.setDescription("sssssss");
		event1.setActors("milica,micko,mudri");
		event1.setDirector("micko");
		event1.setType("action");
		event1.setDuration(170);
		event1.setCreatedBy("Mudri");
		event1.setImageUrl("johnWick2.jpg");
		event1.setPlace(place);

		event2.setName("Montevideo");
		event2.setActors("xxxxx");
		event2.setDescription("wwwww");
		event2.setActors("pera,laza");
		event2.setDirector("mudri");
		event2.setType("domaci");
		event2.setDuration(130);
		event2.setCreatedBy("Mudri");
		event2.setImageUrl("montevideo.jpg");
		event2.setPlace(place);

		try {
			placeRepository.save(place);
			eventRepository.save(event1);
			eventRepository.save(event2);

		} catch (Exception e) {
			log.debug("items (event) are already in database");
		}

		log.info("Seeds for event are completed");
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

		try {

			fanZoneRepository.save(fz1);
			fanZoneRepository.save(fz2);

			itemRepository.save(it1);
			itemRepository.save(it2);
			itemRepository.save(it3);

		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	public void seedFriends() {

		User u1 = userRepository.findOne(Long.parseLong("2"));
		User u2 = userRepository.findOne(Long.parseLong("3"));
		User u3 = userRepository.findOne(Long.parseLong("4"));

		Friends friends1 = new Friends();
		Friends friends2 = new Friends();
		Friends friends3 = new Friends();
		Friends friends4 = new Friends();
		Friends friends5 = new Friends();
		Friends friends6 = new Friends();

		friends1.setUser(u1);
		friends1.setFriend(u2);
		friends2.setUser(u2);
		friends2.setFriend(u1);

		friends3.setUser(u1);
		friends3.setFriend(u3);
		friends4.setUser(u3);
		friends4.setFriend(u1);

		friends5.setUser(u2);
		friends5.setFriend(u3);
		friends6.setUser(u3);
		friends6.setFriend(u2);

		try {

			friendsRepository.save(friends1);
			friendsRepository.save(friends2);
			friendsRepository.save(friends3);
			friendsRepository.save(friends4);
			friendsRepository.save(friends5);
			friendsRepository.save(friends6);

		} catch (Exception e) {

		}
	}

	public void seedHall() {

		Place place = new Place();

		place.setName("Cinestar");
		place.setType("Bioskop");
		place.setCreatedBy("Milica");
		place.setAddress("Bulevar Oslobodjenja 11");

		log.info("Starting seed for hall");
		Hall hall1 = new Hall();
		Hall hall2 = new Hall();

		hall1.setName("Sala 1");
		hall1.setCreatedBy("Milica");
		hall1.setCol(7);
		hall1.setRow(6);
		hall1.setPlace(place);

		hall2.setName("Sala 2");
		hall2.setCreatedBy("Milica");
		hall2.setCol(7);
		hall2.setRow(6);
		hall2.setPlace(place);

		try {
			placeRepository.save(place);
			hallRepository.save(hall1);
			hallRepository.save(hall2);

			log.info("Starting seed for hall");

		} catch (Exception e) {
			log.debug("items (hall) are already in database");
		}

		log.info("Seeds for hall are completed");
	}

	public void seedRepertory() {
		Repertory repertory = new Repertory();

		repertory.setCreatedBy("milica");
		repertory.setDate("2018-03-14");

		try {
			repertoryRepository.save(repertory);
			log.info("Starting seed for repertory");

		} catch (Exception e) {
			log.debug("items (repertory) are already in database");
		}

		log.info("Seeds for repertory are completed");
	}

	public void seedProjection() {

		Place place = new Place();

		place.setName("Cinema");
		place.setType("Bioskop");
		place.setCreatedBy("Milica");
		place.setAddress("Bulevar Oslobodjenja 20");

		log.info("Starting seed for hall");
		Hall hall1 = new Hall();

		hall1.setName("Sala 1");
		hall1.setCreatedBy("Milica");
		hall1.setCol(7);
		hall1.setRow(6);
		hall1.setPlace(place);

		Event event1 = new Event();

		event1.setName("John Wick1");
		event1.setActors("xxx");
		event1.setDescription("xxxxx");
		event1.setActors("milica,,mudri");
		event1.setDirector("micko");
		event1.setType("action");
		event1.setDuration(150);
		event1.setCreatedBy("Mudri");
		event1.setImageUrl("johnWick1.jpg");
		event1.setPlace(place);

		Repertory repertory = new Repertory();

		repertory.setCreatedBy("milica");
		repertory.setDate("2018-05-15");

		Projection projection = new Projection();
		projection.setCreatedBy("milica");
		projection.setDate("2018-05-15");
		projection.setStartTime("15:00");
		projection.setEndTime("17:00");
		projection.setHall(hall1);
		projection.setEvent(event1);
		projection.setReperotry(repertory);

		try {
			placeRepository.save(place);
			eventRepository.save(event1);
			hallRepository.save(hall1);
			repertoryRepository.save(repertory);
			projectionRepository.save(projection);

			log.info("Starting seed for projection");

		} catch (Exception e) {
			log.debug("items (projection) are already in database");
		}

		log.info("Seeds for projection are completed");

	}
}
