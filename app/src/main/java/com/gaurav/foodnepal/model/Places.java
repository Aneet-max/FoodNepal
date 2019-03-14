package com.gaurav.foodnepal.model;

public class Places {
    double lat;
    double lng;
    String id;
    String name;
    String placeId;
    double rating;
    String photoRefrence;
    String vicinity;

    public Places() {
    }

    public Places(String name, double rating) {
        this.name = name;
        this.rating = rating;
    }

    public Places(double lat, double lng, String id, String name, String placeId, double rating, String photoRefrence, String vicinity) {
        this.lat = lat;
        this.lng = lng;
        this.id = id;
        this.name = name;
        this.placeId = placeId;
        this.rating = rating;
        this.photoRefrence = photoRefrence;
        this.vicinity = vicinity;
    }


    public double getLat() {
        return lat;
    }

    public void setLat(double lat) {
        this.lat = lat;
    }

    public double getLng() {
        return lng;
    }

    public void setLng(double lng) {
        this.lng = lng;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPlaceId() {
        return placeId;
    }

    public void setPlaceId(String placeId) {
        this.placeId = placeId;
    }

    public double getRating() {
        return rating;
    }

    public void setRating(double rating) {
        this.rating = rating;
    }

    public String getPhotoRefrence() {
        return photoRefrence;
    }

    public void setPhotoRefrence(String photoRefrence) {
        this.photoRefrence = photoRefrence;
    }

    public String getVicinity() {
        return vicinity;
    }

    public void setVicinity(String vicinity) {
        this.vicinity = vicinity;
    }

    @Override
    public String toString() {
        return "Places{" +
                "lat=" + lat +
                ", lng=" + lng +
                ", id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", placeId='" + placeId + '\'' +
                ", rating=" + rating +
                ", photoRefrence='" + photoRefrence + '\'' +
                ", vicinity='" + vicinity + '\'' +
                '}';
    }
}
