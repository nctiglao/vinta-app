package com.example.jim.superferry.others;

/**
 * Created by jim on 27/04/2018.
 */

public class VesselSced {

    private String vessel_no;
    private String departure;
    private String route;

    public VesselSced() {
    }

    public VesselSced(String route, String vessel_no, String departure ) {
        this.route = route;
        this.vessel_no = vessel_no;
        this.departure = departure;
    }

    public String getRoute() {

        return route;
    }

    public void setRoute(String route) {
        this.route = route;
    }


    public String getVessel_no() {

        return vessel_no;
    }

    public void setVessel_no(String vessel_no) {
        this.vessel_no = vessel_no;
    }

    public String getTime() {
        return departure;
    }

    public void setTime(String departure) {
        this.departure = departure;
    }
}
