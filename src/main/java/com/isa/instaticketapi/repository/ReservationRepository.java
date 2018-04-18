package com.isa.instaticketapi.repository;

import com.isa.instaticketapi.domain.Projection;
import com.isa.instaticketapi.domain.Reservation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
public interface ReservationRepository extends JpaRepository<Reservation, Long> {

    Reservation findOneById(Long id);
    Reservation findOneByProjection(Projection projection);
    List<Reservation> findAll();
    ArrayList<Reservation> findAllByProjection(Projection projection);

}
