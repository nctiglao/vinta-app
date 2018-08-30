package com.example.jim.superferry.others;

/**
 * Created by jim on 11/05/2018.
 */

public class SeatAvail {
    private String vessel;
    private String seat_avail;

    public SeatAvail(){

    }
    public SeatAvail(String vessel, String seat_avail ) {
        this.vessel = vessel;
        this.seat_avail = seat_avail;

    }

    public String getVessel() {

        return vessel;
    }

    public void setVessel(String vessel) {
        this.vessel = vessel;
    }

    public String getSeat_avail() {

        return seat_avail;
    }

    public void setSeat_avail(String seat_avail) {
        this.seat_avail = seat_avail;
    }


}
