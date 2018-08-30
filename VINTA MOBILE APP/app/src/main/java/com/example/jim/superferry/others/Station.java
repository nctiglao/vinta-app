package com.example.jim.superferry.others;

/**
 * Created by jim on 16/08/2018.
 */

public class Station {

    private String originStation;
    private String destinationStation;

    public Station(){

    }

    public Station(String originStation, String destinationStation){
        this.originStation = originStation;
        this.destinationStation = destinationStation;
    }

    public String getOriginStation(){
        return originStation;
    }

    public void setOriginStation(String originStation){
        this.originStation = originStation;
    }

    public String getDestinationStation(){
        return destinationStation;
    }

    public void setDestinationStation(String destinationStation){
        this.destinationStation = destinationStation;
    }
}
