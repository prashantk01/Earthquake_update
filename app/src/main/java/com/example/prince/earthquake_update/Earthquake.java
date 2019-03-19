package com.example.prince.earthquake_update;
public class Earthquake {
    private String location,murl;
    private long date;
    Double magnitude;

    public Earthquake(Double magnitude, String location, long date,String murl) {
        this.magnitude = magnitude;
        this.location = location;
        this.date = date;
        this.murl=murl;
    }

    public Double getMagnitude() {
        return magnitude;
    }

    public String getLocation() {
        return location;
    }

    public long getDate() {
        return date;
    }

    public String getMurl() {
        return murl;
    }
}