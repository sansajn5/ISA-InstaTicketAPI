package com.isa.instaticketapi.service.dto.reservation;

import com.isa.instaticketapi.service.dto.projection.SeatDTO;

import java.util.ArrayList;
import java.util.List;

public class ReservationDTO {

    private List<String> invatations = new ArrayList<>();
    private List<SeatDTO> seats = new ArrayList<>();

    public ReservationDTO() {

    }

    public List<String> getInvatations() {
        return invatations;
    }

    public void setInvatations(List<String> invatations) {
        this.invatations = invatations;
    }

    public List<SeatDTO> getSeats() {
        return seats;
    }

    public void setSeats(List<SeatDTO> seats) {
        this.seats = seats;
    }
}
