package com.isa.instaticketapi.repository;

import com.isa.instaticketapi.domain.Reservation;
import com.isa.instaticketapi.domain.Ticket;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.Optional;

@Repository
public interface TicketRepository extends JpaRepository<Ticket, Long> {

    Ticket findOneById(Long id);
    Ticket findOneByReservation(Reservation reservation);
    ArrayList<Ticket> findAllByReservation (Reservation reservation);
}
