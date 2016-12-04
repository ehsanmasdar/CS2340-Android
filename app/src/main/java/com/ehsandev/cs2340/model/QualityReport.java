package com.ehsandev.cs2340.model;

import java.io.Serializable;

public class QualityReport implements Serializable {
    private String name;
    private double lat;
    private double lon;
    private String id;
    private String date;
    private String condition;
    private int virus;
    private int contaminant;

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

    public String getCondition() {
        return condition;
    }

    public void setCondition(String condition) {
        this.condition = condition;
    }

    public int getVirus() {
        return virus;
    }

    public void setVirus(int virus) {
        this.virus = virus;
    }

    public int getContaminant() {
        return contaminant;
    }

    public void setContaminant(int contaminant) {
        this.contaminant = contaminant;
    }

    public QualityReport(String name, double lat, double lon, String condition, int virus, int contaminant, String id, String date) {
        this.name = name;
        this.lat = lat;
        this.lon = lon;
        this.id = id;
        this.date = date;
        this.condition = condition;
        this.virus = virus;
        this.contaminant = contaminant;
    }

    public QualityReport(String name, double lat, double lon, String condition, int virus, int contaminant) {
        this.name = name;
        this.lat = lat;
        this.lon = lon;
        this.condition = condition;
        this.virus = virus;
        this.contaminant = contaminant;
    }
    @Override
    public String toString(){
        return "Submitted by: " + name + "; " +
                "Location: (" + lat + ", " + lon + "); " +
                "Condition: " + condition + "; " +
                "Virus PPM: " + virus + "; " +
                "Contaminant PPM: " + contaminant + "; " +
                "Report Id: " + id + "; " +
                "Date: " + date;
    }

}
