package com.example.weatherupdates.model;

public class Sys {
    public int type;
    public int id;
    public String message;
    public String country;
    public long sunrise;
    public long sunset;

    public Sys(int type, int id, String message, String country, long sunrise, long sunset) {
        this.type = type;
        this.id = id;
        this.message = message;
        this.country = country;
        this.sunrise = sunrise;
        this.sunset = sunset;
    }
}
