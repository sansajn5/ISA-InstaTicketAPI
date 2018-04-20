package com.isa.instaticketapi.config;

import java.sql.SQLException;
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
import com.isa.instaticketapi.domain.Friends;
import com.isa.instaticketapi.domain.Hall;
import com.isa.instaticketapi.domain.Item;
import com.isa.instaticketapi.domain.Place;
import com.isa.instaticketapi.domain.Projection;
import com.isa.instaticketapi.domain.Repertory;
import com.isa.instaticketapi.domain.Reservation;
import com.isa.instaticketapi.domain.ReservationState;
import com.isa.instaticketapi.domain.Seat;
import com.isa.instaticketapi.domain.Ticket;
import com.isa.instaticketapi.domain.User;
import com.isa.instaticketapi.domain.VoteForEvent;
import com.isa.instaticketapi.domain.VoteForPlace;
import com.isa.instaticketapi.domain.identity.FriendsIdentity;
import com.isa.instaticketapi.repository.AuthorityRepository;
import com.isa.instaticketapi.repository.EventRepository;
import com.isa.instaticketapi.repository.FriendsRepository;
import com.isa.instaticketapi.repository.HallRepository;
import com.isa.instaticketapi.repository.ItemRepository;
import com.isa.instaticketapi.repository.PlaceRepository;
import com.isa.instaticketapi.repository.ProjectionRepository;
import com.isa.instaticketapi.repository.RepertotyRepository;
import com.isa.instaticketapi.repository.ReservationRepository;
import com.isa.instaticketapi.repository.ReservationStateRepository;
import com.isa.instaticketapi.repository.SeatRepository;
import com.isa.instaticketapi.repository.TicketRepository;
import com.isa.instaticketapi.repository.UserRepository;
import com.isa.instaticketapi.repository.VoteForEventRepository;
import com.isa.instaticketapi.repository.VoteForPlaceRepository;
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

	@Autowired
	private VoteForPlaceRepository voteForPlaceRepository;

	@Autowired
	private SeatRepository seatRepository;

	@Autowired
	private VoteForEventRepository voteForProjectionRepository;

	@Autowired
	private ReservationRepository reservationRepository;

	@Autowired
	private ReservationStateRepository reservationStateRepository;

	@Autowired
	private TicketRepository ticketRepository;

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
		Authority fanZoneAdmin = new Authority();

		superAdmin.setName(AuthoritiesConstants.SUPER_ADMIN);
		admin.setName(AuthoritiesConstants.ADMIN);
		user.setName(AuthoritiesConstants.USER);
		guest.setName(AuthoritiesConstants.ANONYMOUS);
		fanZoneAdmin.setName(AuthoritiesConstants.FANZONE_ADMIN);

		try {
			authorityRepository.save(superAdmin);
			authorityRepository.save(admin);
			authorityRepository.save(user);
			authorityRepository.save(guest);
			authorityRepository.save(fanZoneAdmin);
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
		Set<Authority> authoritiesFanZoneAdmin = new HashSet();

		Authority authoritySuperAdmin = authorityRepository.findOne(AuthoritiesConstants.SUPER_ADMIN);
		Authority authorityAdmin = authorityRepository.findOne(AuthoritiesConstants.ADMIN);
		Authority authorityUser = authorityRepository.findOne(AuthoritiesConstants.USER);
		Authority authorityGuest = authorityRepository.findOne(AuthoritiesConstants.ANONYMOUS);
		Authority authorityFanZoneAdmin = authorityRepository.findOne(AuthoritiesConstants.FANZONE_ADMIN);

		authoritiesSuperAdmin.add(authoritySuperAdmin);
		authoritiesSuperAdmin.add(authorityAdmin);
		authoritiesSuperAdmin.add(authorityAdmin);
		authoritiesSuperAdmin.add(authorityUser);
		authoritiesSuperAdmin.add(authorityGuest);

		authoritiesAdmin.add(authorityAdmin);
		authoritiesAdmin.add(authorityUser);
		authoritiesAdmin.add(authorityGuest);

		authoritiesFanZoneAdmin.add(authorityFanZoneAdmin);
		authoritiesFanZoneAdmin.add(authorityUser);
		authoritiesFanZoneAdmin.add(authorityUser);

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
		superAdmin.setEmail("super@super.com");
		superAdmin.setCreatedBy("sansajn");
		superAdmin.setCity("Novi Sad");
		superAdmin.setAddress("petrovaradin");
		superAdmin.setNumber("2131231333");

		sansajn.setUsername("sansajn");
		sansajn.setPassword(passwordEncoder.encode("sansajn"));
		sansajn.setFirstName("Nemanja");
		sansajn.setLastName("Mudrinic");
		sansajn.setAuthorities(authoritiesAdmin);
		sansajn.setActivated(true);
		sansajn.setEmail("sansajn@super.com");
		sansajn.setCreatedBy("sansajn");
		sansajn.setCity("Novi Sad");
		sansajn.setAddress("petrovaradin");
		sansajn.setNumber("2131231333");

		dejan.setUsername("dejan");
		dejan.setPassword(passwordEncoder.encode("dejan"));
		dejan.setFirstName("Dejan");
		dejan.setLastName("Mijic");
		dejan.setAuthorities(authoritiesAdmin);
		dejan.setActivated(true);
		dejan.setEmail("dejan@super.com");
		dejan.setCreatedBy("sansajn");
		dejan.setCity("Novi Sad");
		dejan.setAddress("petrovaradin");
		dejan.setNumber("2131231333");

		milica.setUsername("milica");
		milica.setPassword(passwordEncoder.encode("milica"));
		milica.setFirstName("Milica");
		milica.setLastName("Kovacevic");
		milica.setAuthorities(authoritiesAdmin);
		milica.setActivated(true);
		milica.setEmail("milica12345@super.com");
		milica.setCreatedBy("sansajn");
		milica.setCity("Novi Sad");
		milica.setAddress("petrovaradin");
		milica.setNumber("2131231333");

		User jelena = new User();

		jelena.setUsername("jelena");
		jelena.setPassword(passwordEncoder.encode("jelena123"));
		jelena.setFirstName("Jelena");
		jelena.setLastName("Jankovic");
		jelena.setAuthorities(authoritiesUser);
		jelena.setActivated(true);
		jelena.setEmail("jelena12345@gmail.com");
		jelena.setCreatedBy("sansajn");
		jelena.setCity("Novi Sad");
		jelena.setAddress("telep");
		jelena.setNumber("2131231333");

		User marko = new User();

		marko.setUsername("marko");
		marko.setPassword(passwordEncoder.encode("marko123"));
		marko.setFirstName("Marko");
		marko.setLastName("Markovic");
		marko.setAuthorities(authoritiesUser);
		marko.setActivated(true);
		marko.setEmail("marko12345@gmail.com");
		marko.setCreatedBy("sansajn");
		marko.setCity("Novi Sad");
		marko.setAddress("Novo Naselje");
		marko.setNumber("2131231333");

		User petar = new User();

		petar.setUsername("petar");
		petar.setPassword(passwordEncoder.encode("petar123"));
		petar.setFirstName("Petar");
		petar.setLastName("Petrovic");
		petar.setAuthorities(authoritiesUser);
		petar.setActivated(true);
		petar.setEmail("petar12345@gmail.com");
		petar.setCreatedBy("sansajn");
		petar.setCity("Novi Sad");
		petar.setAddress("Novo Naselje");
		petar.setNumber("2131231333");

		try {
			userRepository.save(superAdmin);
			userRepository.save(sansajn);
			userRepository.save(milica);
			userRepository.save(dejan);

			userRepository.save(jelena);
			userRepository.save(marko);
			userRepository.save(petar);
		} catch (Exception e) {
			log.debug("items (users) are already in database");
		}

		log.info("Seeds for users are completed");

	}

	/**
	 * Setting up cinemas for common init database
	 */
	public void seedCinema() {

		try {

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
		Place place1 = new Place();

		place.setName("Srpsko narodno pozoriste");
		place.setType("Pozoriste");
		place.setCreatedBy("Milica");
		place.setAddress("Pozorisni trg 1");
		place1.setName("Pozoriste mladih");
		place1.setType("Pozoriste");
		place1.setCreatedBy("Milica");
		place1.setAddress("Ignjata Pavlasa 4");
		place1.setDescripton(
				"Pozoriste mladih osnovano je 1932. godine kao Lutkarsko pozoriste, pri Sokolskom drustvu u Novom Sadu. Pozoriste je nastalo iz Sokolske sekcije lutkara, koja je formirana 1930. godine, uz veliku podrsku staresina Sokola, dr Vladimira Belajcica i dr Ignjata Pavlasa.");

		try {

			placeRepository.save(place);
			placeRepository.save(place1);

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

		try {

		} catch (Exception e) {
			log.debug("items (event) are already in database");
		}

		log.info("Seeds for event are completed");
	}

	public void seedFanZone() {

		Item it1 = new Item();
		it1.setName("Rekvizit 1");
		it1.setCreatedBy("Dejan");
		it1.setDescription("Opis opis");
		it1.setPrice("500");

		Item it2 = new Item();
		it2.setName("Rekvizit 2");
		it2.setCreatedBy("Dejan");
		it2.setDescription("Opis 2 bla bla");
		it2.setPrice("1200");

		try {

			itemRepository.save(it1);
			itemRepository.save(it2);

		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	public void seedFriends() {

		User u1 = userRepository.findOne(Long.parseLong("2"));
		User u2 = userRepository.findOne(Long.parseLong("3"));
		User u3 = userRepository.findOne(Long.parseLong("4"));

		Friends friends1 = new Friends(new FriendsIdentity(u1.getId().toString(), u2.getId().toString()), u1, u2);
		Friends friends2 = new Friends(new FriendsIdentity(u2.getId().toString(), u1.getId().toString()), u2, u1);
		Friends friends3 = new Friends(new FriendsIdentity(u1.getId().toString(), u3.getId().toString()), u1, u3);
		Friends friends4 = new Friends(new FriendsIdentity(u3.getId().toString(), u1.getId().toString()), u3, u1);
		Friends friends5 = new Friends(new FriendsIdentity(u2.getId().toString(), u3.getId().toString()), u2, u3);
		Friends friends6 = new Friends(new FriendsIdentity(u3.getId().toString(), u2.getId().toString()), u3, u2);

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

		log.info("Starting seed for hall");

		try {

			log.info("Starting seed for hall");

		} catch (Exception e) {
			log.debug("items (hall) are already in database");
		}

		log.info("Seeds for hall are completed");
	}

	public void seedRepertory() {

		/*
		 * Repertory repertory = new Repertory();
		 * repertory.setCreatedBy("milica"); repertory.setDate("2018-03-14");
		 * repertory.setPlace(place);
		 * 
		 * Repertory repertory1 = new Repertory();
		 * repertory1.setCreatedBy("milica"); repertory1.setDate("2018-03-14");
		 * repertory1.setPlace(place1);
		 * 
		 * try { repertoryRepository.save(repertory);
		 * repertoryRepository.save(repertory1); log.info(
		 * "Starting seed for repertory");
		 * 
		 * } catch (Exception e) { log.debug(
		 * "items (repertory) are already in database"); }
		 * 
		 * log.info("Seeds for repertory are completed");
		 */
	}

	public void seedProjection() {
		Authority authorityUser = authorityRepository.findOne(AuthoritiesConstants.USER);
		Authority authorityGuest = authorityRepository.findOne(AuthoritiesConstants.ANONYMOUS);

		Set<Authority> authoritiesUser = new HashSet<>();
		Set<Authority> authoritiesGuest = new HashSet<>();

		authoritiesUser.add(authorityUser);
		authoritiesUser.add(authorityGuest);

		User comi = new User();

		comi.setUsername("comi");
		comi.setPassword(passwordEncoder.encode("comi123"));
		comi.setFirstName("Comi");
		comi.setLastName("Kovacevic");
		comi.setAuthorities(authoritiesUser);
		comi.setActivated(true);
		comi.setEmail("kovacevicmilica544@gmail.com");
		comi.setCreatedBy("sansajn");
		comi.setCity("Novi Sad");
		comi.setAddress("petrovaradin");
		comi.setNumber("2131231333");

		Place place = new Place();
		Place place1 = new Place();
		Place place2 = new Place();

		place.setName("Arena Cineplex");
		place.setType("Bioskop");
		place.setCreatedBy("Milica");
		place.setAddress("Bul. Mihajla pupina 3");
		place.setDescripton(
				"Arena Cineplex je kompletno renovirana 2010. godine u skladu sa najnovijim svetskim standardima. Rekonstrukcijom i adaptacijom starog bioskopskog prostora dobijen je prvi multipleks u Vojvodini, sa sest vrhunski opremljenih sala, ukupnog kapaciteta od skoro 1.000 mesta, kao i dva ugostiteljska objekta -"
						+ " Cinema i The End cafe.Pored redovnog filmskog repertoara, u Areni Cineplex se organizuju svecane premijere domacih filmova, kao i festivali FEST, Cinema City, Cinemania i Kids Fest. Vazan segment nase ponude cine i mogucnost kupovine koncesija (kokice, nacosi i razna bezalkoholna pica) koje publika moze da konzumira tokom trajanja projekcije."
						+ "");
		place1.setName("Cinestar Pancevo");
		place1.setType("Bioskop");
		place1.setCreatedBy("Milica");
		place1.setAddress("Milosa Obrenovica 12");
		place1.setDescripton(
				"Brend CineStar razvio se iz bioskopa kompanije Kieft & Kieft Filmtheater GmbH, koja je, posle vise od cetiri decenije rada kao operater klasicnog bioskopa, jos 1993. otvorila svoj prvi multipleks u Nemackoj, pa brend od tada predstavlja velicanstveni spoj jedinstvene arhitekture, vrhunskog komfora, sofisticiranog enterijera, kao i inovativnih tehnologija zvuka i slike – sa više od 100 multipleksa - i to ne samo u Nemackoj, nego u vise evropskih zemalja (Ceska, Svajcarska, Hrvatska, Bosna i Hercegovina i dr.)");

		place2.setName("Cinestar NS");
		place2.setType("Bioskop");
		place2.setCreatedBy("Milica");
		place2.setAddress("Big Soping Centar");
		place2.setDescripton(
				"Multipleksi CineStar potpuno su digitalizovani bioskopi opremljeni prema najsavremenijim standardima koji ukljucuju wall-to-wall platna najrenomiranijih svetskih proizvodjaca (Harkness Screens) i najbolju svetsku audio-vizuelnu tehnologiju, koja ukljucuje Barco Series-II digitalne projektore, Doremi i Barco ICMP Alchemy servere, RealD 3D tehnologiju, Dolby zvucne procesore, Crown audio pojacala i JBL zvucnike. ");

		Event event1 = new Event();
		Event event2 = new Event();
		Event event3 = new Event();
		Event event4 = new Event();
		Event event5 = new Event();
		Event event6 = new Event();
		Event event7 = new Event();
		Event event8 = new Event();
		Event event9 = new Event();

		event1.setName("Tomb Raider");
		event1.setActors("Alicia Vikander, Walton Goggins");
		event1.setDescription(
				"Lara Kroft je strastvena, samostalna devojka i cerka ekscentricnog pustolova koji je nestao dok je ona još bila tinejdžerka. Sada, nakon sedam godina, Lara nema nikakav stvaran cilj ili svrhun ona odbija da preuzme uzde carstva svog oca jednako kao sto odbija da prizna da je stvarno otisao.");
		event1.setDirector("Roar Uthaug");
		event1.setType("Akcioni, Avantura");
		event1.setDuration(118);
		event1.setCreatedBy("Milica");
		event1.setImageUrl("x");
		event1.setPlace(place);
		event1.setVote(5);

		event3.setName("Tomb Raider");
		event3.setActors("Alicia Vikander, Walton Goggins");
		event3.setDescription(
				"Lara Kroft je strastvena, samostalna devojka i cerka ekscentricnog pustolova koji je nestao dok je ona još bila tinejdžerka. Sada, nakon sedam godina, Lara nema nikakav stvaran cilj ili svrhun ona odbija da preuzme uzde carstva svog oca jednako kao sto odbija da prizna da je stvarno otisao.");
		event3.setDirector("Roar Uthaug");
		event3.setType("Akcioni, Avantura");
		event3.setDuration(118);
		event3.setCreatedBy("Milica");
		event3.setImageUrl("x");
		event3.setPlace(place1);

		event4.setName("Tomb Raider");
		event4.setActors("Alicia Vikander, Walton Goggins");
		event4.setDescription(
				"Lara Kroft je strastvena, samostalna devojka i cerka ekscentricnog pustolova koji je nestao dok je ona još bila tinejdžerka. Sada, nakon sedam godina, Lara nema nikakav stvaran cilj ili svrhun ona odbija da preuzme uzde carstva svog oca jednako kao sto odbija da prizna da je stvarno otisao.");
		event4.setDirector("Roar Uthaug");
		event4.setType("Akcioni, Avantura");
		event4.setDuration(118);
		event4.setCreatedBy("Milica");
		event4.setImageUrl("x");
		event4.setPlace(place2);
		event4.setVote(4);

		event2.setName("Deadpool 2");
		event2.setActors("Ryan Reynolds, Josh Brolin");
		event2.setDescription(
				"Ne bi li nekako sebi zabiberio zivot, a u potrazi za kondenzatorom fluksa, Vejd mora da se bori protiv nindzi, jakuza i copora seksualno agresivnih pasa, putujuci po svetu i otkrivajuci vaznost porodice, prijatelja i ukusa.");
		event2.setDirector("David Leitch");
		event2.setType("Akcioni, Komedija, Avantura");
		event2.setDuration(130);
		event2.setCreatedBy("Milica");
		event2.setImageUrl("x");
		event2.setPlace(place);

		event5.setName("Deadpool 2");
		event5.setActors("Ryan Reynolds, Josh Brolin");
		event5.setDescription(
				"Ne bi li nekako sebi zabiberio zivot, a u potrazi za kondenzatorom fluksa, Vejd mora da se bori protiv nindzi, jakuza i copora seksualno agresivnih pasa, putujuci po svetu i otkrivajuci vaznost porodice, prijatelja i ukusa.");
		event5.setDirector("David Leitch");
		event5.setType("Akcioni, Komedija, Avantura");
		event5.setDuration(130);
		event5.setCreatedBy("Milica");
		event5.setImageUrl("x");
		event5.setPlace(place1);

		event6.setName("The Bride");
		event6.setActors("Viktoriya Agalakova, Vyacheslav Chepurchenko");
		event6.setDescription(
				"U davnoj proslosti, jedan fotograf je odrzavao tradiciju fotografisanja umrlih clanova svoje porodice. Kada mu je zena umrla,sahranio je pored tela device kako bi oziveo duh svoje zene u telu mlade devojke.");
		event6.setDirector(" Svyatoslav Podgaevskiy");
		event6.setType("Horor, Triler");
		event6.setDuration(93);
		event6.setCreatedBy("Milica");
		event6.setImageUrl("x");
		event6.setPlace(place2);
		event6.setVote(4);

		event7.setName("The Bride");
		event7.setActors("Viktoriya Agalakova, Vyacheslav Chepurchenko");
		event7.setDescription(
				"U davnoj proslosti, jedan fotograf je odrzavao tradiciju fotografisanja umrlih clanova svoje porodice. Kada mu je zena umrla,sahranio je pored tela device kako bi oziveo duh svoje zene u telu mlade devojke.");
		event7.setDirector(" Svyatoslav Podgaevskiy");
		event7.setType("Horor, Triler");
		event7.setDuration(93);
		event7.setCreatedBy("Milica");
		event7.setImageUrl("x");
		event7.setPlace(place);
		event7.setVote(2);

		event8.setName("A Quiet Place");
		event8.setActors("Emily Blunt, John Krasinski");
		event8.setDescription(
				"U modernom hororu i trileru TIHO MESTO, cetvoroclana porodica mora da vodi zivot u apsolutnoj tisini, nakon sto njihov opstanak ugroze misteriozna bica koja love onog ko ispusti i najmanji zvuk. Ako te cuju, ulovice te!");
		event8.setDirector("John Krasinski");
		event8.setType("Horor, Triler");
		event8.setDuration(90);
		event8.setCreatedBy("Milica");
		event8.setImageUrl("x");
		event8.setPlace(place2);

		event9.setName("A Quiet Place");
		event9.setActors("Emily Blunt, John Krasinski");
		event9.setDescription(
				"U modernom hororu i trileru TIHO MESTO, cetvoroclana porodica mora da vodi zivot u apsolutnoj tisini, nakon sto njihov opstanak ugroze misteriozna bica koja love onog ko ispusti i najmanji zvuk. Ako te cuju, ulovice te!");
		event9.setDirector("John Krasinski");
		event9.setType("Horor, Triler");
		event9.setDuration(90);
		event9.setCreatedBy("Milica");
		event9.setImageUrl("x");
		event9.setPlace(place);
		event9.setVote(2);

		Hall hall1 = new Hall();
		Hall hall2 = new Hall();
		Hall hall3 = new Hall();
		Hall hall4 = new Hall();
		Hall hall5 = new Hall();
		Hall hall6 = new Hall();
		Hall hall7 = new Hall();
		Hall hall8 = new Hall();

		hall1.setName("Sala 1.");
		hall1.setCreatedBy("Milica");
		hall1.setCol(3);
		hall1.setRow(3);
		hall1.setPlace(place);

		hall2.setName("Sala 2.");
		hall2.setCreatedBy("Milica");
		hall2.setCol(2);
		hall2.setRow(2);
		hall2.setPlace(place);

		hall3.setName("Sala 3.");
		hall3.setCreatedBy("Milica");
		hall3.setCol(10);
		hall3.setRow(6);
		hall3.setPlace(place);

		hall4.setName("Sala-1");
		hall4.setCreatedBy("Milica");
		hall4.setCol(7);
		hall4.setRow(7);
		hall4.setPlace(place1);

		hall5.setName("Sala-2");
		hall5.setCreatedBy("Milica");
		hall5.setCol(8);
		hall5.setRow(8);
		hall5.setPlace(place1);

		hall6.setName("Sala-3");
		hall6.setCreatedBy("Milica");
		hall6.setCol(10);
		hall6.setRow(10);
		hall6.setPlace(place1);

		hall7.setName("Sala 1");
		hall7.setCreatedBy("Milica");
		hall7.setCol(8);
		hall7.setRow(8);
		hall7.setPlace(place2);

		hall8.setName("Sala 2");
		hall8.setCreatedBy("Milica");
		hall8.setCol(10);
		hall8.setRow(10);
		hall8.setPlace(place2);

		Repertory repertory = new Repertory();
		repertory.setCreatedBy("milica");
		repertory.setDate("18-4-2018");
		repertory.setPlace(place);

		Repertory repertory1 = new Repertory();
		repertory1.setCreatedBy("milica");
		repertory1.setDate("20-4-2018");
		repertory1.setPlace(place);

		Repertory repertory2 = new Repertory();
		repertory2.setCreatedBy("milica");
		repertory2.setDate("23-4-2018");
		repertory2.setPlace(place1);

		Repertory repertory3 = new Repertory();
		repertory3.setCreatedBy("milica");
		repertory3.setDate("24-4-2018");
		repertory3.setPlace(place2);

		Repertory repertory4 = new Repertory();
		repertory4.setCreatedBy("milica");
		repertory4.setDate("20-4-2018");
		repertory4.setPlace(place2);

		Projection projection = new Projection();
		projection.setCreatedBy("milica");
		projection.setReperotry(repertory);
		projection.setStartTime("15:00");
		projection.setEndTime("17:10");
		projection.setHall(hall1);
		projection.setEvent(event2);
		projection.setDate(repertory.getDate());
		projection.setSale(20);
		projection.setRegularPrice(470);
		projection.setBalconyPrice(300);
		projection.setQuickTicketPrice(350);
		projection.setVipPrice(500);

		Projection projection2 = new Projection();
		projection2.setCreatedBy("milica");
		projection2.setReperotry(repertory1);
		projection2.setStartTime("12:00");
		projection2.setEndTime("14:00");
		projection2.setHall(hall2);
		projection2.setEvent(event1);
		projection2.setDate(repertory1.getDate());
		projection2.setSale(20);
		projection2.setRegularPrice(350);
		projection2.setBalconyPrice(250);
		projection2.setQuickTicketPrice(200);
		projection2.setVipPrice(600);

		Projection projection3 = new Projection();
		projection3.setCreatedBy("milica");
		projection3.setReperotry(repertory1);
		projection3.setStartTime("12:00");
		projection3.setEndTime("14:00");
		projection3.setHall(hall2);
		projection3.setEvent(event1);
		projection3.setDate(repertory1.getDate());
		projection3.setRegularPrice(350);
		projection3.setBalconyPrice(300);
		projection3.setQuickTicketPrice(200);
		projection3.setVipPrice(500);

		Projection projection4 = new Projection();
		projection4.setCreatedBy("milica");
		projection4.setReperotry(repertory1);
		projection4.setStartTime("21:00");
		projection4.setEndTime("22:35");
		projection4.setHall(hall3);
		projection4.setEvent(event5);
		projection4.setDate(repertory1.getDate());
		projection4.setSale(20);
		projection4.setRegularPrice(500);
		projection4.setBalconyPrice(300);
		projection4.setQuickTicketPrice(350);
		projection4.setVipPrice(600);

		Projection projection5 = new Projection();
		projection5.setCreatedBy("milica");
		projection5.setReperotry(repertory2);
		projection5.setStartTime("16:00");
		projection5.setEndTime("18:00");
		projection5.setHall(hall4);
		projection5.setEvent(event3);
		projection5.setDate(repertory2.getDate());
		projection5.setSale(20);
		projection5.setRegularPrice(500);

		Projection projection6 = new Projection();
		projection6.setCreatedBy("milica");
		projection6.setReperotry(repertory3);
		projection6.setStartTime("21:00");
		projection6.setEndTime("23:00");
		projection6.setHall(hall7);
		projection6.setEvent(event4);
		projection6.setDate(repertory3.getDate());

		Projection projection7 = new Projection();
		projection7.setCreatedBy("milica");
		projection7.setReperotry(repertory3);
		projection7.setStartTime("21:00");
		projection7.setEndTime("22:35");
		projection7.setHall(hall8);
		projection7.setEvent(event6);
		projection7.setDate(repertory3.getDate());

		Projection projection8 = new Projection();
		projection8.setCreatedBy("milica");
		projection8.setReperotry(repertory3);
		projection8.setStartTime("18:00");
		projection8.setEndTime("19:30");
		projection8.setHall(hall8);
		projection8.setEvent(event8);
		projection8.setDate(repertory3.getDate());

		Projection projection9 = new Projection();
		projection9.setCreatedBy("milica");
		projection9.setReperotry(repertory3);
		projection9.setStartTime("18:00");
		projection9.setEndTime("19:30");
		projection9.setHall(hall8);
		projection9.setEvent(event8);
		projection9.setDate(repertory3.getDate());

		VoteForPlace vote1 = new VoteForPlace();
		vote1.setUser(comi);
		vote1.setPlace(place1);
		vote1.setVote(4);

		VoteForPlace vote2 = new VoteForPlace();
		vote2.setUser(comi);
		vote2.setPlace(place1);
		vote2.setVote(2);

		VoteForEvent v1 = new VoteForEvent();
		v1.setUser(comi);
		v1.setEvent(event1);
		v1.setVote(3);

		Seat seat1 = new Seat();
		seat1.setHall(hall1);
		seat1.setCordX(1);
		seat1.setCordY(1);
		seat1.setProjection(projection);
		seat1.setReserved(true);
		seat1.setSeat(false);
		seat1.setSeatType("VIP");

		Seat seat2 = new Seat();
		seat2.setHall(hall1);
		seat2.setCordX(1);
		seat2.setCordY(2);
		seat2.setProjection(projection);
		seat2.setReserved(false);
		seat2.setSeat(false);
		seat2.setSeatType("VIP");

		Seat seat3 = new Seat();
		seat3.setHall(hall1);
		seat3.setCordX(1);
		seat3.setCordY(3);
		seat3.setProjection(projection);
		seat3.setReserved(false);
		seat3.setSeat(false);
		seat3.setSeatType("VIP");

		Seat seat4 = new Seat();
		seat4.setHall(hall1);
		seat4.setCordX(2);
		seat4.setCordY(1);
		seat4.setProjection(projection);
		seat4.setReserved(false);
		seat4.setSeat(false);
		seat4.setSeatType("QUICK");

		Seat seat5 = new Seat();
		seat5.setHall(hall1);
		seat5.setCordX(2);
		seat5.setCordY(2);
		seat5.setProjection(projection);
		seat5.setReserved(false);
		seat5.setSeat(false);
		seat5.setSeatType("QUICK");

		Seat seat6 = new Seat();
		seat6.setHall(hall1);
		seat6.setCordX(2);
		seat6.setCordY(3);
		seat6.setProjection(projection);
		seat6.setReserved(false);
		seat6.setSeat(false);
		seat6.setSeatType("QUICK");

		Seat seat7 = new Seat();
		seat7.setHall(hall1);
		seat7.setCordX(3);
		seat7.setCordY(1);
		seat7.setProjection(projection);
		seat7.setReserved(true);
		seat7.setSeat(false);
		seat7.setSeatType("CLASSIC");

		Seat seat8 = new Seat();
		seat8.setHall(hall1);
		seat8.setCordX(3);
		seat8.setCordY(2);
		seat8.setProjection(projection);
		seat8.setReserved(false);
		seat8.setSeat(false);
		seat8.setSeatType("CLASSIC");

		Seat seat9 = new Seat();
		seat9.setHall(hall1);
		seat9.setCordX(3);
		seat9.setCordY(3);
		seat9.setProjection(projection);
		seat9.setReserved(false);
		seat9.setSeat(false);
		seat9.setSeatType("CLASSIC");

		Seat seat21 = new Seat();
		seat21.setHall(hall2);
		seat21.setCordX(1);
		seat21.setCordY(1);
		seat21.setProjection(projection2);
		seat21.setReserved(true);
		seat21.setSeat(false);
		seat21.setSeatType("CLASSIC");

		Seat seat22 = new Seat();
		seat22.setHall(hall2);
		seat22.setCordX(1);
		seat22.setCordY(2);
		seat22.setProjection(projection2);
		seat22.setReserved(true);
		seat22.setSeat(false);
		seat22.setSeatType("CLASSIC");

		Seat seat23 = new Seat();
		seat23.setHall(hall2);
		seat23.setCordX(2);
		seat23.setCordY(1);
		seat23.setProjection(projection2);
		seat23.setReserved(false);
		seat23.setSeat(false);
		seat23.setSeatType("CLASSIC");

		Seat seat24 = new Seat();
		seat24.setHall(hall2);
		seat24.setCordX(2);
		seat24.setCordY(2);
		seat24.setProjection(projection2);
		seat24.setReserved(true);
		seat24.setSeat(false);
		seat24.setSeatType("QUICK");

		Reservation reservation1 = new Reservation();
		reservation1.setProjection(projection);

		Reservation reservation2 = new Reservation();
		reservation2.setProjection(projection);

		Reservation reservation21 = new Reservation();
		reservation21.setProjection(projection2);

		Reservation reservation22 = new Reservation();
		reservation22.setProjection(projection2);

		Reservation reservation23 = new Reservation();
		reservation23.setProjection(projection2);

		ReservationState reservationState1 = new ReservationState();
		reservationState1.setDropOut(false);
		reservationState1.setReservation(reservation1);
		reservationState1.setUsed(true);
		reservationState1.setUserIncludedInReservation(comi);

		ReservationState reservationState2 = new ReservationState();
		reservationState2.setDropOut(false);
		reservationState2.setReservation(reservation2);
		reservationState2.setUsed(true);
		reservationState2.setUserIncludedInReservation(comi);

		ReservationState reservationState21 = new ReservationState();
		reservationState21.setDropOut(false);
		reservationState21.setReservation(reservation21);
		reservationState21.setUsed(true);
		reservationState21.setUserIncludedInReservation(comi);

		ReservationState reservationState22 = new ReservationState();
		reservationState22.setDropOut(false);
		reservationState22.setReservation(reservation22);
		reservationState22.setUsed(true);
		reservationState22.setUserIncludedInReservation(comi);

		ReservationState reservationState23 = new ReservationState();
		reservationState23.setDropOut(false);
		reservationState23.setReservation(reservation23);
		reservationState23.setUsed(true);
		reservationState23.setUserIncludedInReservation(comi);

		Ticket ticket1 = new Ticket();
		ticket1.setReservation(reservation1);
		ticket1.setSeat(seat1);
		ticket1.setTickeyType("VIP");

		Ticket ticket2 = new Ticket();
		ticket2.setReservation(reservation2);
		ticket2.setSeat(seat7);
		ticket2.setTickeyType("Regular");

		Ticket ticket21 = new Ticket();
		ticket21.setReservation(reservation21);
		ticket21.setSeat(seat21);
		ticket21.setTickeyType("Regular");

		Ticket ticket22 = new Ticket();
		ticket22.setReservation(reservation22);
		ticket22.setSeat(seat22);
		ticket22.setTickeyType("Regular");

		Ticket ticket23 = new Ticket();
		ticket23.setReservation(reservation23);
		ticket23.setSeat(seat23);
		ticket23.setTickeyType("Regular");

		try {

			placeRepository.save(place);
			placeRepository.save(place1);
			placeRepository.save(place2);

			eventRepository.save(event1);
			eventRepository.save(event2);
			eventRepository.save(event3);
			eventRepository.save(event4);
			eventRepository.save(event5);
			eventRepository.save(event6);
			eventRepository.save(event7);
			eventRepository.save(event8);
			eventRepository.save(event9);

			hallRepository.save(hall1);
			hallRepository.save(hall2);
			hallRepository.save(hall3);
			hallRepository.save(hall4);
			hallRepository.save(hall5);
			hallRepository.save(hall6);
			hallRepository.save(hall7);
			hallRepository.save(hall8);

			repertoryRepository.save(repertory);
			repertoryRepository.save(repertory1);
			repertoryRepository.save(repertory2);
			repertoryRepository.save(repertory3);
			repertoryRepository.save(repertory4);

			projectionRepository.save(projection);
			projectionRepository.save(projection2);
			projectionRepository.save(projection3);
			projectionRepository.save(projection4);
			projectionRepository.save(projection5);
			projectionRepository.save(projection6);
			projectionRepository.save(projection7);
			projectionRepository.save(projection8);
			projectionRepository.save(projection9);

			userRepository.save(comi);

			voteForProjectionRepository.save(v1);

			seatRepository.save(seat1);
			seatRepository.save(seat2);
			seatRepository.save(seat3);
			seatRepository.save(seat4);
			seatRepository.save(seat5);
			seatRepository.save(seat6);
			seatRepository.save(seat7);
			seatRepository.save(seat8);
			seatRepository.save(seat9);
			seatRepository.save(seat21);
			seatRepository.save(seat22);
			seatRepository.save(seat23);
			seatRepository.save(seat24);

			reservationRepository.save(reservation1);
			reservationRepository.save(reservation2);
			reservationRepository.save(reservation21);
			reservationRepository.save(reservation22);
			reservationRepository.save(reservation23);

			reservationStateRepository.save(reservationState1);
			reservationStateRepository.save(reservationState2);
			reservationStateRepository.save(reservationState21);
			reservationStateRepository.save(reservationState22);
			reservationStateRepository.save(reservationState23);

			ticketRepository.save(ticket1);
			ticketRepository.save(ticket2);
			ticketRepository.save(ticket21);
			ticketRepository.save(ticket22);
			ticketRepository.save(ticket23);

			log.info("Starting seed for projection");

		} catch (Exception e) {
			log.debug("items (projection) are already in database");
		}

		log.info("Seeds for projection are completed");

	}

}
