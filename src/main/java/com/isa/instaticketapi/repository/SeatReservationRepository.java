package com.isa.instaticketapi.repository;

import com.isa.instaticketapi.domain.Projection;
import com.isa.instaticketapi.domain.Seat;
import com.isa.instaticketapi.domain.SeatReservation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SeatReservationRepository extends JpaRepository<SeatReservation, Long> {

    List<SeatReservation> findAllByProjection(Projection projection);
    SeatReservation findOneByProjectionAndSeat(Projection projection,Seat seat);
}
