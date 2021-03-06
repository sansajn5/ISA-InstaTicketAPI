package com.isa.instaticketapi.service;

import java.util.ArrayList;

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
import com.isa.instaticketapi.domain.ReservationInvitation;
import com.isa.instaticketapi.domain.ReservationState;
import com.isa.instaticketapi.domain.Seat;
import com.isa.instaticketapi.domain.Ticket;
import com.isa.instaticketapi.domain.User;
import com.isa.instaticketapi.domain.VoteForEvent;
import com.isa.instaticketapi.repository.EventRepository;
import com.isa.instaticketapi.repository.HallRepository;
import com.isa.instaticketapi.repository.PlaceRepository;
import com.isa.instaticketapi.repository.ProjectionRepository;
import com.isa.instaticketapi.repository.RepertotyRepository;
import com.isa.instaticketapi.repository.ReservationInvitationRepository;
import com.isa.instaticketapi.repository.ReservationRepository;
import com.isa.instaticketapi.repository.ReservationStateRepository;
import com.isa.instaticketapi.repository.SeatRepository;
import com.isa.instaticketapi.repository.TicketRepository;
import com.isa.instaticketapi.repository.UserRepository;
import com.isa.instaticketapi.repository.VoteForEventRepository;
import com.isa.instaticketapi.security.SecurityUtils;
import com.isa.instaticketapi.service.dto.projection.ProjectionDTO;
import com.isa.instaticketapi.service.dto.projection.SeatDTO;

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

	@Autowired
	private SeatRepository seatRepository;

	@Autowired
	private VoteForEventRepository voteForEventRepository;

	@Autowired
	private ReservationRepository reservationRepository;

	@Autowired
	private ReservationStateRepository reservationStateRepository;

	@Autowired
	private ReservationInvitationRepository reservationInvitationRepository;

	@Autowired
	private TicketRepository ticketRepository;

	public void createProjection(ProjectionDTO projectionDTO, Long id) {
		Projection projection = new Projection();
		User logged = SecurityUtils.getCurrentUserLogin().flatMap(userRepository::findOneByUsername).get();
		Place place = placeRepository.findOneById(id);
		String hallName = projectionDTO.getHallName();
		String eventName = projectionDTO.getEventName();

		projection.setCreatedBy(logged.getUsername());
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
			reprtory1.setCreatedBy(
					SecurityUtils.getCurrentUserLogin().flatMap(userRepository::findOneByUsername).get().getUsername());
			reprtory1.setDate(datePares);

			repertoryRepository.save(reprtory1);
			projection.setReperotry(reprtory1);
		}

		// VALIDACIJA ZA POCETNO I KRAJNJE VREME

		String startTime = projectionDTO.getStartTime();
		String[] timeS = startTime.split(":");
		String startT = timeS[0] + timeS[1];
		int start = Integer.parseInt(startT);

		String endTime = projectionDTO.getEndTime();
		String[] timeE = endTime.split(":");
		String endT = timeE[0] + timeE[1];
		int end = Integer.parseInt(endT);

		ArrayList<Projection> projections = projectionRepository.findAllByHall(h);

		for (int i = 0; i < projections.size(); i++) {
			if ((projections.get(i).getDate()).equals(datePares)) {

				String startProjection = projections.get(i).getStartTime();
				String[] sss = startProjection.split(":");
				String startP = sss[0] + sss[1];
				int startProjectionTime = Integer.parseInt(startP);

				String endProjection = projections.get(i).getEndTime();
				String[] ss = endProjection.split(":");
				String endP = ss[0] + ss[1];
				int endProjectionTime = Integer.parseInt(endP);

				if (start >= startProjectionTime && start <= endProjectionTime) {

					throw new IllegalArgumentException("Hall is busy !!!");

				} else if (end >= startProjectionTime && end <= endProjectionTime) {

					throw new IllegalArgumentException("Hall is busy !!!");

				}
			}
		}

		projection.setRegularPrice(projectionDTO.getRegularPrice());
		projection.setVipPrice(projectionDTO.getVipPrice());
		projection.setBalconyPrice(projectionDTO.getBalconyPrice());
		projection.setQuickTicketPrice(projectionDTO.getRegularPrice()
				- ((projectionDTO.getRegularPrice() * projectionDTO.getSalePercentage()) / 100));
		projection.setSale(projectionDTO.getBalconyPrice());
		projectionRepository.save(projection);

		for (int i = 1; i <= h.getRow(); i++) {
			for (int j = 1; j <= h.getCol(); j++) {
				Seat seatForProjection = new Seat();
				seatForProjection.setCordX(i);
				seatForProjection.setCordY(j);
				seatForProjection.setProjection(projection);
				seatForProjection.setHall(h);
				seatForProjection.setReserved(false);
				seatForProjection.setSeat(true);
				seatForProjection.setSeatType(Constants.REGULAR_SEAT);
				seatRepository.save(seatForProjection);
			}
		}

		for (SeatDTO seat : projectionDTO.getSeatDTO()) {
			Seat tempSeat = seatRepository.findOneByCordXAndCordYAndProjection(seat.getCordX(), seat.getCordY(),
					projection);
			tempSeat.setSeatType(seat.getType());
			tempSeat.setSeat(seat.isSeat());
			seatRepository.save(tempSeat);
		}

	}

	/**
	 * 
	 * @param id
	 *            id of projection
	 */
	public void deleteProjection(Long id) {
		Projection projection = projectionRepository.findOneById(id);
		ArrayList<Seat> seats = seatRepository.findAllByProjection(projection);
		log.debug("xx");
		ArrayList<Reservation> reservations = reservationRepository.findAllByProjection(projection);
		log.debug("xx");
		for (int i = 0; i < reservations.size(); i++) {
			log.debug("xx");
			ArrayList<ReservationState> states = reservationStateRepository.findAllByReservation(reservations.get(i));
			reservationStateRepository.delete(states);
			log.debug("xx");
			ArrayList<ReservationInvitation> invitations = reservationInvitationRepository
					.findAllByReservation(reservations.get(i));
			reservationInvitationRepository.delete(invitations);
			log.debug("xx");
			ArrayList<Ticket> tickets = ticketRepository.findAllByReservation(reservations.get(i));
			ticketRepository.delete(tickets);
		}
		reservationRepository.delete(reservations);
		log.debug("xx");
		seatRepository.delete(seats);
		log.debug("xx");
		projectionRepository.delete(projection);

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

	public int getVoteForEvent(Long id) {

		Projection projection = projectionRepository.findOneById(id);
		Event event = projection.getEvent();
		int voteSum = 0;
		int vote = 0;
		ArrayList<VoteForEvent> votes = voteForEventRepository.findAllByEvent(event);
		for (int i = 0; i < votes.size(); i++) {
			voteSum += votes.get(i).getVote();
		}
		if (votes.isEmpty()) {
			return vote;
		}
		vote = voteSum / votes.size();
		return vote;
	}

	public void editProjection(ProjectionDTO projectionDTO, Long id, Long projectionId) {
		Projection projection = projectionRepository.findOneById(projectionId);
		User logged = SecurityUtils.getCurrentUserLogin().flatMap(userRepository::findOneByUsername).get();

		if (!calculateRange(projectionDTO))
			throw new IllegalArgumentException("Hall is used in that time");

		projection.setStartTime(projectionDTO.getStartTime());
		projection.setEndTime(projectionDTO.getEndTime());
		String date = projectionDTO.getDate();
		String[] dat = date.split("-");
		String datePares = dat[2] + '-' + dat[1] + '-' + dat[0];
		projection.setDate(datePares);

		ArrayList<Repertory> repertories = repertoryRepository.findALLByDate(datePares);

		if (repertories.isEmpty()) {
			Place place = placeRepository.findOneById(id);
			Repertory reprtory1 = new Repertory();
			reprtory1.setPlace(place);
			reprtory1.setCreatedBy(logged.getUsername());
			reprtory1.setDate(datePares);
			repertoryRepository.save(reprtory1);
			projection.setReperotry(reprtory1);
		}

		projection.setRegularPrice(projectionDTO.getRegularPrice());
		projection.setVipPrice(projectionDTO.getVipPrice());
		projection.setBalconyPrice(projectionDTO.getBalconyPrice());
		projection.setQuickTicketPrice(projectionDTO.getRegularPrice() * (projectionDTO.getSalePercentage() / 100));

		projectionRepository.save(projection);

		for (SeatDTO seat : projectionDTO.getSeatDTO()) {
			log.info("setting new seats");
			Seat tempSeat = seatRepository.findOneByCordXAndCordYAndProjection(seat.getCordX(), seat.getCordY(),
					projection);
			tempSeat.setSeatType(seat.getType());
			tempSeat.setSeat(seat.isSeat());
			seatRepository.save(tempSeat);
		}
	}

	public boolean calculateRange(ProjectionDTO projectionDTO) {
		// LOGIC FOR CHECKING TIME
		return true;
	}
}
