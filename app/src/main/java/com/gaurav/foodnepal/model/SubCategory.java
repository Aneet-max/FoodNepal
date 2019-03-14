package com.gaurav.foodnepal.model;

public class SubCategory {

    private String name;
    private int imagePath;
    private String type;

    public SubCategory() {
    }

    public SubCategory(String name, int imagePath, String type) {
        this.name = name;
        this.imagePath = imagePath;
        this.type = type;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getImagePath() {
        return imagePath;
    }

    public void setImagePath(int imagePath) {
        this.imagePath = imagePath;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    @Override
    public String toString() {
        return "SubCategory{" +
                "name='" + name + '\'' +
                ", imagePath=" + imagePath +
                ", type='" + type + '\'' +
                '}';
    }
}
