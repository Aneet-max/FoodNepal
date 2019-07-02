package com.aneet.foodnepal.model;

public class Restaurant {
    String description;
    String location;
    String phone;
    String website;

    public Restaurant() {
    }

    public Restaurant(String description, String location, String phone, String website) {
        this.description = description;

        this.location = location;
        this.phone = phone;
        this.website = website;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getWebsite() {
        return website;
    }

    public void setWebsite(String website) {
        this.website = website;
    }

    @Override
    public String toString() {
        return "Restaurant{" +
                "description='" + description + '\'' +
                ", location='" + location + '\'' +
                ", phone='" + phone + '\'' +
                ", website='" + website + '\'' +
                '}';
    }
}
