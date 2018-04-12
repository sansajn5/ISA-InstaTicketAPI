package com.isa.instaticketapi.service;

import com.isa.instaticketapi.config.Constants;
import com.isa.instaticketapi.domain.*;
import com.isa.instaticketapi.repository.*;
import com.isa.instaticketapi.security.SecurityUtils;
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
    SeatReservationRepository seatReservationRepository;

    @Autowired
    TicketRepository ticketRepository;

    public void createReservation(List<String> invitations, List<Integer[]> seats,Long projectionId) {
        Optional<User> logged = SecurityUtils.getCurrentUserLogin().flatMap(userRepository::findOneByUsername);
        invitations.forEach( email -> {
            User invitedUser = userRepository.findOneByEmailIgnoreCase(email).get();
            ReservationInvitation reservationInvitation = new ReservationInvitation();
            reservationInvitation.setToUser(invitedUser);
            reservationInvitation.setFromUser(logged.get());
            reservationInvitationRepository.save(reservationInvitation);
        });
        Reservation reservation = new Reservation();
        List<Ticket> tickets = new ArrayList<>();
        Projection projection = projectionRepository.findOneById(projectionId);
        if(projection == null)
            throw new IllegalArgumentException("Projection with provided id doesn't exsist");

        reservation.setProjection(projection);
        Hall hall = projection.getHall();
        List<Seat> seatsInHall  = seatRepository.findAllByHall(hall);

        for (Integer[] seat : seats) {
            for (Seat seatInHall : seatsInHall) {
                if (seatInHall.getCordX() == seat[0] && seatInHall.getCordY() == seat[1]) {
                    if (!seatInHall.isSeat())
                        throw new IllegalArgumentException("Seat doesn't exist at provided position");

                    SeatReservation seatState = seatReservationRepository.findOneByProjectionAndSeat(projection,seatInHall);

                    if(seatState.isReserved())
                        throw new IllegalArgumentException("Seat is already reserved");

                    Ticket ticket = createTicket(seatInHall,reservation);
                    tickets.add(ticket);
                    seatState.setReserved(true);
                    seatState.setProjection(projection);
                    seatReservationRepository.save(seatState);
                }
            }
        }
        reservationRepository.save(reservation);
    }

    public Ticket createTicket(Seat seat, Reservation reservation) {
        Ticket ticket = new Ticket();
        if(seat.getSeatType() == Constants.VIP_SEAT)
            ticket.setTickeyType(Constants.VIP_TICKET);
        ticket.setTickeyType(Constants.REGULAR_TICKET);
        ticket.setSeat(seat);
        ticket.setReservation(reservation);
        return ticketRepository.save(ticket);
    }

    public Optional<ReservationState> getMyActiveReservations() {
        Optional<User> logged = SecurityUtils.getCurrentUserLogin().flatMap(userRepository::findOneByUsername);
        if(!logged.isPresent())
            throw new IllegalArgumentException("No current logged user");
        Optional<ReservationState> reservationStates = reservationStateRepository.findAllByUserIncludedInReservation(logged.get());
        List<Reservation> returnList = new ArrayList<>();
        return reservationStates.filter(el -> !el.isUsed() && !el.isDropOut());
    }


    /**
     * For statistic
     */
    public Optional<ReservationState> getMyUsedReservation(){
        Optional<User> logged = SecurityUtils.getCurrentUserLogin().flatMap(userRepository::findOneByUsername);
        if(!logged.isPresent())
            throw new IllegalArgumentException("No current logged user");
        Optional<ReservationState> reservationStates = reservationStateRepository.findAllByUserIncludedInReservation(logged.get());
        List<Reservation> returnList = new ArrayList<>();
        return reservationStates.filter(el -> el.isUsed() && el.isDropOut());
    }






}
