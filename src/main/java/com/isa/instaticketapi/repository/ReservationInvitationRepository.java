package com.isa.instaticketapi.repository;

import java.util.ArrayList;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.isa.instaticketapi.domain.Reservation;
import com.isa.instaticketapi.domain.ReservationInvitation;

@Repository
public interface ReservationInvitationRepository extends JpaRepository<ReservationInvitation, Long> {
	ArrayList<ReservationInvitation> findAllByReservation (Reservation reservation);
}
