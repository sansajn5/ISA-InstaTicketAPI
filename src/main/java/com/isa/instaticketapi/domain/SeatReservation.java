package com.isa.instaticketapi.domain;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "Seat_Reservation")
public class SeatReservation implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    private Seat seat;

    @OneToOne
    private Projection projection;

    private boolean isReserved;

    public SeatReservation() {

    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Seat getSeat() {
        return seat;
    }

    public void setSeat(Seat seat) {
        this.seat = seat;
    }

    public Projection getProjection() {
        return projection;
    }

    public void setProjection(Projection projection) {
        this.projection = projection;
    }

    public boolean isReserved() {
        return isReserved;
    }

    public void setReserved(boolean reserved) {
        isReserved = reserved;
    }
}
