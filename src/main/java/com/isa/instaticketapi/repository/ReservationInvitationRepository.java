package com.isa.instaticketapi.repository;

import com.isa.instaticketapi.domain.ReservationInvitation;
import com.isa.instaticketapi.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ReservationInvitationRepository extends JpaRepository<ReservationInvitation, Long> {

    Optional<ReservationInvitation> findAllByToUser(User user);
    ReservationInvitation findOneById(Long id);
}
