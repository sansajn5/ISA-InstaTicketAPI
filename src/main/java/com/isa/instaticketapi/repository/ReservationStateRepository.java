package com.isa.instaticketapi.repository;

import com.isa.instaticketapi.domain.Reservation;
import com.isa.instaticketapi.domain.ReservationState;
import com.isa.instaticketapi.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Repository
public interface ReservationStateRepository extends JpaRepository<ReservationState, Long> {

	ReservationState findOneByUserIncludedInReservation(User user);

	ArrayList<ReservationState> findAllByUserIncludedInReservation(User user);

	ReservationState findOneByReservation(Reservation reservation);

	ArrayList<ReservationState> findAllByReservation(Reservation reservation);

}
