package com.example.jim.superferry.others;

import java.io.Serializable;

/**
 * Created by jim on 19/03/2018.
 */

public class Holder implements Serializable {
private String Status = null;
    private  String ID = null;

    public Holder(){

    }

    public void setStatus(String Status) { this.Status = Status; }

    public String getStatus() {
        return this.Status;
    }

    public void setID(String ID) { this.ID = ID; }

    public String getID() {
        return this.ID;
    }
}
