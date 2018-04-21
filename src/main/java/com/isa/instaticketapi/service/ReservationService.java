package com.isa.instaticketapi.service;

import com.isa.instaticketapi.config.Constants;
import com.isa.instaticketapi.domain.*;
import com.isa.instaticketapi.repository.*;
import com.isa.instaticketapi.security.SecurityUtils;
import com.isa.instaticketapi.service.dto.projection.SeatDTO;
import com.isa.instaticketapi.web.rest.vm.UserResource.ReservationInv;
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

	public Reservation createReservation(List<String> invitations, List<SeatDTO> seats, Long projectionId) {
		Optional<User> logged = SecurityUtils.getCurrentUserLogin().flatMap(userRepository::findOneByUsername);

		Reservation reservation = new Reservation();
		List<Ticket> tickets = new ArrayList<>();
		Projection projection = projectionRepository.findOneById(projectionId);
		if (projection == null)
			throw new IllegalArgumentException("Projection with provided id doesn't exsist");

		reservation.setProjection(projection);

		boolean flag = false;
		reservationRepository.save(reservation);

		invitations.forEach(email -> {
			User invitedUser = userRepository.findOneByEmailIgnoreCase(email).get();
			ReservationInvitation reservationInvitation = new ReservationInvitation();
			reservationInvitation.setToUser(invitedUser);
			reservationInvitation.setAccepted(false);
			reservationInvitation.setDeleted(false);
			reservationInvitation.setFromUser(logged.get());
			reservationInvitation.setReservation(reservation);
			reservationInvitationRepository.save(reservationInvitation);
		});

		for (SeatDTO seat : seats) {
			Seat seatInHall = seatRepository.findOneByCordXAndCordYAndProjection(seat.getCordX(), seat.getCordY(), projection);
			if(!seatInHall.isReserved() && seat.getType().equals(Constants.RESERVED)) {
				Ticket ticket = createTicket(seatInHall, reservation);
				ReservationState reservationState =  createReservationState(reservation);

				if(!flag) {
					flag = true;
					reservationState.setUserIncludedInReservation(logged.get());
					reservationStateRepository.save(reservationState);
				}

				tickets.add(ticket);
				seatInHall.setReserved(true);
				seatInHall.setProjection(projection);
				seatRepository.save(seatInHall);
			}
		}
		return reservation;
	}

	public Ticket createTicket(Seat seat, Reservation reservation) {
		Ticket ticket = new Ticket();
		if (seat.getSeatType() == Constants.VIP_SEAT) {
			ticket.setTickeyType(Constants.VIP_TICKET);
		} else if (seat.getSeatType() == Constants.BALCONY_TICKET) {
			ticket.setTickeyType(Constants.BALCONY_SEAT);
		} else if (seat.getSeatType() == Constants.REGULAR_SEAT) {
			ticket.setTickeyType(Constants.REGULAR_TICKET);
		}
		ticket.setSeat(seat);
		ticket.setReservation(reservation);
		return ticketRepository.save(ticket);
	}

	public ReservationState createReservationState(Reservation reservation){
		ReservationState reservationState = new ReservationState();
		reservationState.setDropOut(false);
		reservationState.setUsed(false);
		reservationState.setReservation(reservation);
		reservationState.setUserIncludedInReservation(null);
		return reservationStateRepository.save(reservationState);
	}

	public ArrayList<ReservationState> getMyActiveReservations() {
		Optional<User> logged = SecurityUtils.getCurrentUserLogin().flatMap(userRepository::findOneByUsername);
		if (!logged.isPresent())
			throw new IllegalArgumentException("No current logged user");

		ArrayList<ReservationState> reservationStates = reservationStateRepository
				.findAllByUserIncludedInReservation(logged.get());
		ArrayList<ReservationState> returnList = new ArrayList<>();

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

	public Optional<ReservationInvitation> getMyInvitationForReservation() {
		User logged = SecurityUtils.getCurrentUserLogin().flatMap(userRepository::findOneByUsername).get();
		List<ReservationInvitation> res = reservationInvitationRepository.findAll();

		return reservationInvitationRepository.findAllByToUser(logged)
				.filter(el -> {
					System.out.println(el);
					return !el.getDeleted() && !el.getAccepted();
				});
	}

	public void acceptInvitation(Long id) {
		ReservationInvitation resInv = reservationInvitationRepository.findOneById(id);
		resInv.setAccepted(true);
		User u = resInv.getToUser();
		Reservation res = resInv.getReservation();
		List<ReservationState> resState = reservationStateRepository.findAllByReservation(res);
		for(int i=0; i< resState.size(); i++) {
			if (resState.get(i).getUserIncludedInReservation() == null) {
				resState.get(i).setUserIncludedInReservation(u);
				reservationStateRepository.save(resState);
				break;
			}
		}
	}

	public void declineInvitation(Long id) {
		ReservationInvitation resInv = reservationInvitationRepository.findOneById(id);
		resInv.setAccepted(false);
		resInv.setDeleted(true);
		reservationInvitationRepository.save(resInv);
	}

	public void dropOutReservation(Long id) {
		ReservationState rs = reservationStateRepository.findOne(id);
		rs.setDropOut(true);
		reservationStateRepository.save(rs);
		Ticket t = ticketRepository.findOneByReservation(rs.getReservation());
		Seat seat = t.getSeat();
		seat.setReserved(false);
		seatRepository.save(seat);
	}

}
