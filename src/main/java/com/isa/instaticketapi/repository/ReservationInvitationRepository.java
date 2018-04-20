package com.isa.instaticketapi.repository;


import java.util.ArrayList;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.isa.instaticketapi.domain.Reservation;
import com.isa.instaticketapi.domain.ReservationInvitation;
import com.isa.instaticketapi.domain.User;

@Repository
public interface ReservationInvitationRepository extends JpaRepository<ReservationInvitation, Long> {

    Optional<ReservationInvitation> findAllByToUser(User user);
    ReservationInvitation findOneById(Long id);
	ArrayList<ReservationInvitation> findAllByReservation(Reservation reservation);

}
