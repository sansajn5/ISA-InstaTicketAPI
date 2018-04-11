package com.isa.instaticketapi.repository;

import com.isa.instaticketapi.domain.Hall;
import com.isa.instaticketapi.domain.Seat;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SeatRepository extends JpaRepository<Seat, Long> {

    Seat findOneById(Long id);
    List<Seat> findAllByHall(Hall hall);
    Seat findOneByHallAndAndCordXAndCordY(Hall hall,int x, int y);

}
