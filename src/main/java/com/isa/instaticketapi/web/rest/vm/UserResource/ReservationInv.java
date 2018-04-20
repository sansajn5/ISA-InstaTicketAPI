package com.isa.instaticketapi.web.rest.vm.UserResource;

public class ReservationInv {

    private Long id;
    private String fromUser;
    private String projection;
    private String time;

    public ReservationInv() {

    }

    public ReservationInv(Long id, String fromUser, String projection, String time) {
        this.id = id;
        this.fromUser = fromUser;
        this.projection = projection;
        this.time = time;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFromUser() {
        return fromUser;
    }

    public void setFromUser(String fromUser) {
        this.fromUser = fromUser;
    }

    public String getProjection() {
        return projection;
    }

    public void setProjection(String projection) {
        this.projection = projection;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }
}
