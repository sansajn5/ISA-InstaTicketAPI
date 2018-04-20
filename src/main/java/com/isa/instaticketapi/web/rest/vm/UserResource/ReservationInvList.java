package com.isa.instaticketapi.web.rest.vm.UserResource;

import java.util.ArrayList;
import java.util.List;

public class ReservationInvList {

    private List<ReservationInv> reservationInvList = new ArrayList<>();

    public ReservationInvList() {

    }

    public ReservationInvList(List<ReservationInv> reservationInvs) {
        this.reservationInvList = reservationInvs;
    }

    public List<ReservationInv> getReservationInvList() {
        return reservationInvList;
    }

    public void setReservationInvList(List<ReservationInv> reservationInvList) {
        this.reservationInvList = reservationInvList;
    }
}
