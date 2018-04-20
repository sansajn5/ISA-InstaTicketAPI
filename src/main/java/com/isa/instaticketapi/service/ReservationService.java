package com.isa.instaticketapi.service;

import com.isa.instaticketapi.config.Constants;
import com.isa.instaticketapi.domain.*;
import com.isa.instaticketapi.repository.*;
import com.isa.instaticketapi.security.SecurityUtils;
import com.isa.instaticketapi.service.dto.projection.SeatDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * Service class for managing reservation.
 *
 * @author sansajn
 */
@Service
public class ReservationService {

	@Autowired
	UserRepository userRepository;

	@Autowired
	ReservationStateRepository reservationStateRepository;

	@Autowired
	ReservationRepository reservationRepository;

	@Autowired
	ReservationInvitationRepository reservationInvitationRepository;

	@Autowired
	ProjectionRepository projectionRepository;

	@Autowired
	HallRepository hallRepository;

	@Autowired
	SeatRepository seatRepository;

	@Autowired
	TicketRepository ticketRepository;

	public void createReservation(List<String> invitations, List<SeatDTO> seats, Long projectionId) {
		Optional<User> logged = SecurityUtils.getCurrentUserLogin().flatMap(userRepository::findOneByUsername);
		invitations.forEach(email -> {
			User invitedUser = userRepository.findOneByEmailIgnoreCase(email).get();
			ReservationInvitation reservationInvitation = new ReservationInvitation();
			reservationInvitation.setToUser(invitedUser);
			reservationInvitation.setAccepted(false);
			reservationInvitation.setDeleted(false);
			reservationInvitation.setFromUser(logged.get());
			reservationInvitationRepository.save(reservationInvitation);
		});
		Reservation reservation = new Reservation();
		List<Ticket> tickets = new ArrayList<>();
		Projection projection = projectionRepository.findOneById(projectionId);
		if (projection == null)
			throw new IllegalArgumentException("Projection with provided id doesn't exsist");

		reservation.setProjection(projection);

		for (SeatDTO seat : seats) {
			Seat seatInHall = seatRepository.findOneByCordXAndCordYAndProjection(seat.getCordX(), seat.getCordY(), projection);

			Ticket ticket = createTicket(seatInHall, reservation);
			tickets.add(ticket);
			seatInHall.setReserved(true);
			seatInHall.setProjection(projection);
			seatRepository.save(seatInHall);

		}
		reservationRepository.save(reservation);
	}

	public Ticket createTicket(Seat seat, Reservation reservation) {
		Ticket ticket = new Ticket();
		if (seat.getSeatType() == Constants.VIP_SEAT) {
			ticket.setTickeyType(Constants.VIP_TICKET);
			// price
		} else if (seat.getSeatType() == Constants.BALCONY_TICKET) {
			ticket.setTickeyType(Constants.BALCONY_SEAT);
		} else if (seat.getSeatType() == Constants.REGULAR_SEAT) {
			ticket.setTickeyType(Constants.REGULAR_TICKET);
		}
		ticket.setSeat(seat);
		ticket.setReservation(reservation);
		return ticketRepository.save(ticket);
	}

	public ArrayList<ReservationState> getMyActiveReservations() {
		Optional<User> logged = SecurityUtils.getCurrentUserLogin().flatMap(userRepository::findOneByUsername);
		if (!logged.isPresent())
			throw new IllegalArgumentException("No current logged user");

		ArrayList<ReservationState> reservationStates = reservationStateRepository
				.findAllByUserIncludedInReservation(logged.get());
		ArrayList<ReservationState> returnList = new ArrayList<ReservationState>();

		for (int i = 0; i < reservationStates.size(); i++) {
			if ((reservationStates.get(i).isDropOut()) == false && (reservationStates.get(i).isUsed()) == false) {
				returnList.add(reservationStates.get(i));
			}
		}

		return returnList;
	}

	/**
	 * For statistic
	 */
	public ArrayList<ReservationState> getMyUsedReservation() {
		Optional<User> logged = SecurityUtils.getCurrentUserLogin().flatMap(userRepository::findOneByUsername);
		if (!logged.isPresent())
			throw new IllegalArgumentException("No current logged user");
		ArrayList<ReservationState> reservationStates = reservationStateRepository
				.findAllByUserIncludedInReservation(logged.get());
		ArrayList<ReservationState> returnList = new ArrayList<ReservationState>();

		for (int i = 0; i < reservationStates.size(); i++) {
			if ((reservationStates.get(i).isDropOut()) == false && (reservationStates.get(i).isUsed()) == true) {
				returnList.add(reservationStates.get(i));
			}
		}

		return returnList;
	}

}
