package com.isa.instaticketapi.service;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.isa.instaticketapi.config.Constants;
import com.isa.instaticketapi.domain.Event;
import com.isa.instaticketapi.domain.Hall;
import com.isa.instaticketapi.domain.Place;
import com.isa.instaticketapi.domain.Projection;
import com.isa.instaticketapi.domain.Repertory;
import com.isa.instaticketapi.domain.Reservation;
import com.isa.instaticketapi.domain.ReservationState;
import com.isa.instaticketapi.domain.Seat;
import com.isa.instaticketapi.domain.Ticket;
import com.isa.instaticketapi.domain.User;
import com.isa.instaticketapi.domain.VoteForPlace;
import com.isa.instaticketapi.repository.EventRepository;
import com.isa.instaticketapi.repository.HallRepository;
import com.isa.instaticketapi.repository.PlaceRepository;
import com.isa.instaticketapi.repository.ProjectionRepository;
import com.isa.instaticketapi.repository.RepertotyRepository;
import com.isa.instaticketapi.repository.ReservationRepository;
import com.isa.instaticketapi.repository.ReservationStateRepository;
import com.isa.instaticketapi.repository.SeatRepository;
import com.isa.instaticketapi.repository.TicketRepository;
import com.isa.instaticketapi.repository.UserRepository;
import com.isa.instaticketapi.repository.VoteForPlaceRepository;
import com.isa.instaticketapi.security.SecurityUtils;
import com.isa.instaticketapi.service.dto.ResponseStatistic;
import com.isa.instaticketapi.service.dto.StatisticDTO;
import com.isa.instaticketapi.service.dto.places.PlaceDTO;

/**
 * Service for managing cinema and theatres
 * 
 * @author Milica Kovacevic
 *
 */
@Service
@Transactional
public class PlaceService {
	private final Logger log = LoggerFactory.getLogger(PlaceService.class);

	@Autowired
	private PlaceRepository placeRepository;

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private EventRepository eventRepository;

	@Autowired
	private HallRepository hallRepository;

	@Autowired
	private ProjectionRepository projectionRepository;

	@Autowired
	private RepertotyRepository repertoryRepository;

	@Autowired
	private VoteForPlaceRepository voteForPlaceRepository;

	@Autowired
	private SeatRepository seatRepository;

	@Autowired
	private ReservationRepository reservationrepository;

	@Autowired
	private ReservationStateRepository reservationStaterepository;

	@Autowired
	private TicketRepository ticketRepository;

	@Autowired
	private ProjectionService projectionService;

	@Autowired
	private HallService hallService;

	@Autowired
	private RepertoryService repertoryService;

	@Autowired
	private EventService eventService;

	/**
	 * 
	 * @return list of all cinemas
	 */
	public List<Place> getCinemas() {
		return placeRepository.findAllByType(Constants.CINEMA);
	}

	/**
	 * 
	 * @return list of all theaters
	 */
	public List<Place> getTheaters() {
		return placeRepository.findAllByType(Constants.THEATER);
	}

	/**
	 * 
	 * @param id
	 * @return cinema or theater
	 */
	public Place getPlace(Long id) {
		return placeRepository.findOneById(id);
	}

	/**
	 * 
	 * @param changePlaceDTO
	 *            object for editing
	 * @param id
	 *            id of object
	 */

	public Place changePlace(PlaceDTO placeDTO, long id) {
		Place place = placeRepository.findOneById(id);
		if (place == null) {
			return null;
		}

		User logged = SecurityUtils.getCurrentUserLogin().flatMap(userRepository::findOneByUsername).get();
		place.setLastModifiedBy(logged.getUsername());
		place.setName(placeDTO.getName());
		place.setAddress(placeDTO.getAddress());
		place.setDescripton(placeDTO.getDescription());
		place.setType(placeDTO.getType());
		placeRepository.save(place);
		return place;

	}

	public void createPlace(PlaceDTO placeDTO) throws SQLException {

		Place place = new Place();

		place.setName(placeDTO.getName());
		place.setAddress(placeDTO.getAddress());
		User logged = SecurityUtils.getCurrentUserLogin().flatMap(userRepository::findOneByUsername).get();
		place.setCreatedBy(logged.getUsername());
		place.setDescripton(placeDTO.getDescription());
		place.setType(placeDTO.getType());

		placeRepository.save(place); // maybe error - unique name

	}

	/**
	 * Deleting place from database
	 * 
	 * @param id
	 *            representing id of place which will be deleted
	 */
	public Place deletePlace(Long id) {
		Place place = placeRepository.findOneById(id);
		if (place == null) {
			return null;
		}
		ArrayList<Hall> halls = hallRepository.findAllByPlace(place);
		for (int i = 0; i < halls.size(); i++) {
			ArrayList<Projection> projections = projectionRepository.findAllByHall(halls.get(i));
			for (int j = 0; j < projections.size(); j++) {
				projectionService.deleteProjection(projections.get(j).getId());
			}
			hallService.deleteHall(halls.get(i).getId());
		}
		ArrayList<Repertory> reprtories = repertoryRepository.findAllByPlace(place);
		for (int i = 0; i < reprtories.size(); i++) {
			repertoryService.deleteRepertory(reprtories.get(i).getId());
		}

		ArrayList<Event> events = eventRepository.findAllByPlace(place);
		for (int i = 0; i < events.size(); i++) {
			eventService.deleteEvent(events.get(i).getId());
		}

		ArrayList<VoteForPlace> votes = voteForPlaceRepository.findAllByPlace(place);
		voteForPlaceRepository.delete(votes);

		placeRepository.delete(place);
		log.debug("Deleted place.");
		return place;

	}

	/**
	 * 
	 * @param id
	 *            id of place
	 * @return list of event in place
	 */
	public ArrayList<Event> getEventsInPlace(Long id) {

		Place place = placeRepository.findOneById(id);
		if (place == null) {
			throw new IllegalArgumentException("Invalid id!");
		}

		ArrayList<Event> events = eventRepository.findAllByPlace(place);
		return events;
	}

	/**
	 * 
	 * @param id
	 *            id of place
	 * @return list of repertory(objects)
	 */
	public ArrayList<Repertory> getRepertoriesInPlace(Long id) {
		Place place = placeRepository.findOneById(id);
		return repertoryRepository.findAllByPlace(place);
	}

	/**
	 * 
	 * @param id
	 *            id of place
	 * @return vote for place
	 */
	public int getVoteForPlace(Long id) {

		int voteSum = 0;
		int vote = 0;
		ArrayList<VoteForPlace> votes = voteForPlaceRepository.findAllByPlace(placeRepository.findOneById(id));
		for (int i = 0; i < votes.size(); i++) {
			voteSum += votes.get(i).getVote();
		}
		if (votes.isEmpty()) {
			return vote;
		}
		vote = voteSum / votes.size();
		return vote;
	}

	/**
	 * 
	 * @param id
	 *            id of place
	 * @return list of seat object
	 */
	public ArrayList<Seat> getQuickSeats(Long id) {
		Place place = placeRepository.findOneById(id);
		ArrayList<Hall> halls = hallRepository.findAllByPlace(place);

		ArrayList<Seat> seats = new ArrayList<Seat>();
		for (int i = 0; i < halls.size(); i++) {
			ArrayList<Seat> allSeat = seatRepository.findAllByHall(halls.get(i));
			for (int j = 0; j < allSeat.size(); j++) {
				if ((allSeat.get(j).getSeatType()).equals(Constants.QUICK_SEAT) && !allSeat.get(j).isReserved()
						&& !allSeat.get(j).isSeat()) {
					seats.add(allSeat.get(j));
				}
			}
		}

		return seats;
	}

	/**
	 * 
	 * @param id
	 *            of seat for reservation
	 */
	public void reservation(Long id) {
		Seat seat = seatRepository.findOneById(id);
		seat.setReserved(true);

		Reservation reservation = new Reservation();
		reservation.setProjection(seat.getProjection());
		reservationrepository.save(reservation);

		ReservationState reservationState = new ReservationState();
		reservationState.setReservation(reservation);
		User logged = SecurityUtils.getCurrentUserLogin().flatMap(userRepository::findOneByUsername).get();
		reservationState.setUserIncludedInReservation(logged);
		reservationStaterepository.save(reservationState);

		Ticket ticket = new Ticket();
		ticket.setReservation(reservation);
		ticket.setSeat(seat);
		String typeTicket = "";
		if (seat.getSeatType().equals("QUICK")) {
			typeTicket = "Quick Ticket";
		} else if (seat.getSeatType().equals("VIP")) {
			typeTicket = "VIP";
		} else if (seat.getSeatType().equals("CLASSIC")) {
			typeTicket = "Regular";
		}

		ticket.setTickeyType(typeTicket);
		ticketRepository.save(ticket);

	}

	public ArrayList<ResponseStatistic> getAttendence(Long id, StatisticDTO attendenceDTO) {
		ArrayList<ResponseStatistic> list = new ArrayList<ResponseStatistic>();

		String fromNoParse = attendenceDTO.getDateFrom();
		String toNoParse = attendenceDTO.getDateTo();
		// parsiranje u oblik dd-mm-yyy
		String[] datFrom = fromNoParse.split("-");
		String from = datFrom[2] + '-' + datFrom[1] + '-' + datFrom[0];

		int fromDay = Integer.parseInt(datFrom[2]);
		int fromMonth = Integer.parseInt(datFrom[1]);
		int fromYear = Integer.parseInt(datFrom[0]);

		Place place = placeRepository.findOneById(id);
		ArrayList<Repertory> repertories = repertoryRepository.findAllByPlace(place);

		if (toNoParse.equals("undefined-undefined-undefined")) {

			for (int i = 0; i < repertories.size(); i++) {
				if ((repertories.get(i).getDate()).equals(from)) {
					ResponseStatistic responseAttendece = new ResponseStatistic();
					int count = 0;

					ArrayList<Reservation> reservations = (ArrayList<Reservation>) reservationrepository.findAll();
					for (int j = 0; j < reservations.size(); j++) {
						if ((reservations.get(j).getProjection().getReperotry()).equals(repertories.get(i))
								&& (reservations.get(j).getProjection().getHall().getPlace()).equals(place)) {
							ReservationState reservationState = reservationStaterepository
									.findOneByReservation(reservations.get(j));
							if (reservationState.isUsed() == true) {
								count += 1;
							}
						}
					}

					responseAttendece.setDate(repertories.get(i).getDate());
					responseAttendece.setAttendence(count);
					list.add(responseAttendece);

				}
			}
			return list;
		} else {
			String[] datTo = toNoParse.split("-");
			String to = datTo[2] + '-' + datTo[1] + '-' + datTo[0];

			int toDay = Integer.parseInt(datTo[2]);
			int toMonth = Integer.parseInt(datTo[1]);
			int toYear = Integer.parseInt(datTo[0]);
			if (fromMonth == toMonth && fromYear == toYear) {
				for (int i = fromDay; i <= toDay; i++) {
					log.debug("AAAAAAAAAAAAAAAAAAAAAAAA {}", i);
					String date = "";
					date = Integer.toString(i) + "-" + Integer.toString(fromMonth) + "-" + Integer.toString(fromYear);
					ResponseStatistic responseAttendece = new ResponseStatistic();
					int count = 0;
					for (int j = 0; j < repertories.size(); j++) {
						if ((repertories.get(j).getDate()).equals(date)) {

							ArrayList<Reservation> reservations = (ArrayList<Reservation>) reservationrepository
									.findAll();
							for (int x = 0; x < reservations.size(); x++) {
								if ((reservations.get(x).getProjection().getReperotry()).equals(repertories.get(j))
										&& (reservations.get(x).getProjection().getHall().getPlace()).equals(place)) {
									ReservationState reservationState = reservationStaterepository
											.findOneByReservation(reservations.get(x));
									if (reservationState.isUsed() == true) {
										count += 1;
									}
								}
							}

						}

					}
					responseAttendece.setDate(date);
					responseAttendece.setAttendence(count);
					list.add(responseAttendece);
				}

			}

			return list;
		}

	}

	public ArrayList<ResponseStatistic> getInCome(Long id, StatisticDTO attendenceDTO) {
		ArrayList<ResponseStatistic> list = new ArrayList<ResponseStatistic>();

		String fromNoParse = attendenceDTO.getDateFrom();
		String toNoParse = attendenceDTO.getDateTo();
		// parsiranje u oblik dd-mm-yyy
		String[] datFrom = fromNoParse.split("-");
		String from = datFrom[2] + '-' + datFrom[1] + '-' + datFrom[0];

		int fromDay = Integer.parseInt(datFrom[2]);
		int fromMonth = Integer.parseInt(datFrom[1]);
		int fromYear = Integer.parseInt(datFrom[0]);

		Place place = placeRepository.findOneById(id);
		ArrayList<Repertory> repertories = repertoryRepository.findAllByPlace(place);

		if (toNoParse.equals("undefined-undefined-undefined")) {

			for (int i = 0; i < repertories.size(); i++) {
				if ((repertories.get(i).getDate()).equals(from)) {
					ResponseStatistic responseAttendece = new ResponseStatistic();
					int count = 0;

					ArrayList<Reservation> reservations = (ArrayList<Reservation>) reservationrepository.findAll();

					for (int j = 0; j < reservations.size(); j++) {
						if ((reservations.get(j).getProjection().getReperotry()).equals(repertories.get(i))
								&& (reservations.get(j).getProjection().getHall().getPlace()).equals(place)) {

							ReservationState reservationState = reservationStaterepository
									.findOneByReservation(reservations.get(j));
							if (reservationState.isUsed() == true) {
								Ticket ticket = ticketRepository.findOneByReservation(reservations.get(j));
								String typeSeat = ticket.getTickeyType();

								log.debug("AAAAAAAAAAAAA {}", typeSeat);

								if (typeSeat.equals("Balcony Ticket")) {
									count += reservations.get(j).getProjection().getBalconyPrice();
								} else if (typeSeat.equals("Quick Ticket")) {
									count += reservations.get(j).getProjection().getQuickTicketPrice();
								} else if (typeSeat.equals("Regular")) {
									count += reservations.get(j).getProjection().getRegularPrice();
								} else {
									count += reservations.get(j).getProjection().getVipPrice();

								}
							}
						}
					}

					responseAttendece.setDate(repertories.get(i).getDate());
					responseAttendece.setAttendence(count);
					list.add(responseAttendece);

				}
			}
			return list;
		} else {
			String[] datTo = toNoParse.split("-");
			String to = datTo[2] + '-' + datTo[1] + '-' + datTo[0];

			int toDay = Integer.parseInt(datTo[2]);
			int toMonth = Integer.parseInt(datTo[1]);
			int toYear = Integer.parseInt(datTo[0]);
			if (fromMonth == toMonth && fromYear == toYear) {
				for (int i = fromDay; i <= toDay; i++) {
					log.debug("AAAAAAAAAAAAAAAAAAAAAAAA {}", i);
					String date = "";
					date = Integer.toString(i) + "-" + Integer.toString(fromMonth) + "-" + Integer.toString(fromYear);
					ResponseStatistic responseAttendece = new ResponseStatistic();
					int count = 0;
					for (int j = 0; j < repertories.size(); j++) {
						if ((repertories.get(j).getDate()).equals(date)) {

							ArrayList<Reservation> reservations = (ArrayList<Reservation>) reservationrepository
									.findAll();
							for (int x = 0; x < reservations.size(); x++) {
								if ((reservations.get(x).getProjection().getReperotry()).equals(repertories.get(j))
										&& (reservations.get(x).getProjection().getHall().getPlace()).equals(place)) {

									ReservationState reservationState = reservationStaterepository
											.findOneByReservation(reservations.get(x));
									if (reservationState.isUsed() == true) {
										Ticket ticket = ticketRepository.findOneByReservation(reservations.get(x));
										String typeSeat = ticket.getTickeyType();

										log.debug("AAAAAAAAAAAAA {}", typeSeat);

										if (typeSeat.equals("Balcony Ticket")) {
											count += reservations.get(x).getProjection().getBalconyPrice();
										} else if (typeSeat.equals("Quick Ticket")) {
											count += reservations.get(x).getProjection().getQuickTicketPrice();
										} else if (typeSeat.equals("Regular")) {
											count += reservations.get(x).getProjection().getRegularPrice();
										} else if (typeSeat.equals("VIP")) {
											count += reservations.get(x).getProjection().getVipPrice();
										} else {

										}

									}

								}
							}

						}

					}
					responseAttendece.setDate(date);
					responseAttendece.setAttendence(count);
					list.add(responseAttendece);
				}

			}

			return list;

		}

	}

}
