package com.isa.instaticketapi.service.dto.user;

public class ReservationInvitationDTO {

    private Long id;
    private boolean response;

    public ReservationInvitationDTO() {

    }

    public ReservationInvitationDTO(Long id, boolean response) {
        this.id = id;
        this.response = response;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public boolean isResponse() {
        return response;
    }

    public void setResponse(boolean response) {
        this.response = response;
    }
}
