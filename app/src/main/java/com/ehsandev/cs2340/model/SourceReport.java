package com.ehsandev.cs2340.model;


import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.io.Serializable;

public class SourceReport implements Serializable {
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getLat() {
        return lat;
    }

    public void setLat(double lat) {
        this.lat = lat;
    }

    public double getLon() {
        return lon;
    }

    public void setLon(double lon) {
        this.lon = lon;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getCondition() {
        return condition;
    }

    public void setCondition(String condition) {
        this.condition = condition;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    private String name;
    private double lat;
    private double lon;
    private String type;
    private String condition;
    private String id;
    private String date;

    public SourceReport(String name, double lat, double lon, String type, String condition) {
        this.name = name;
        this.lat = lat;
        this.lon = lon;
        this.type = type;
        this.condition = condition;
    }

    public SourceReport(String name, double lat, double lon, String type, String condition, String id, String date) {
        this.name = name;
        this.lat = lat;
        this.lon = lon;
        this.type = type;
        this.condition = condition;
        this.id = id;
        this.date = date;
    }
    public MarkerOptions getMarker(){
        return new MarkerOptions().position(new LatLng(lat,lon)).title(id);
    }
    @Override
    public String toString(){
         return "Submitted by: " + name + "; " +
                "Location: (" + lat + ", " + lon + "); " +
                "Type: " + type + "; " +
                "Condition: " + condition + "; " +
                "Report Id: " + id + "; " +
                "Date: " + date;
    }
}
