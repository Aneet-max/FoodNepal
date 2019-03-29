package com.gaurav.foodnepal.model;

public class Trip {
    private int id;
    private String tripName;
    private String place;
    private String date;
    private String placeId;
    private String lat;
    private String lng;

    public Trip() {
    }

    public Trip(int id, String tripName, String place, String date, String placeId, String lat, String lng) {
        this.id = id;
        this.tripName = tripName;
        this.place = place;
        this.date = date;
        this.placeId = placeId;
        this.lat = lat;
        this.lng = lng;
    }


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTripName() {
        return tripName;
    }

    public void setTripName(String tripName) {
        this.tripName = tripName;
    }

    public String getPlace() {
        return place;
    }

    public void setPlace(String place) {
        this.place = place;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getPlaceId() {
        return placeId;
    }

    public void setPlaceId(String placeId) {
        this.placeId = placeId;
    }

    public String getLat() {
        return lat;
    }

    public void setLat(String lat) {
        this.lat = lat;
    }

    public String getLng() {
        return lng;
    }

    public void setLng(String lng) {
        this.lng = lng;
    }

    @Override
    public String toString() {
        return "Trip{" +
                "id=" + id +
                ", tripName='" + tripName + '\'' +
                ", place='" + place + '\'' +
                ", date='" + date + '\'' +
                ", userName='" + placeId + '\'' +
                ", lat='" + lat + '\'' +
                ", lng='" + lng + '\'' +
                '}';
    }
}
