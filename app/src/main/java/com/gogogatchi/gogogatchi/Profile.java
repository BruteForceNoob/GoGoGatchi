package com.gogogatchi.gogogatchi;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Profile {

    @SerializedName("destination")
    @Expose
    private String destinationName;

    @SerializedName("url")
    @Expose
    private String imageUrl;

    @SerializedName("city")
    @Expose
    private String city;

    @SerializedName("description")
    @Expose
    private String description;

    @SerializedName("distance")
    @Expose
    private double distance;

    @SerializedName("phone")
    @Expose
    private String phone;

    @SerializedName("website")
    @Expose
    private String website;

    public String getDestinationName() { return destinationName; }
    public String getImageUrl() { return imageUrl; }
    public String getCity() { return city; }
    public String getDescription() { return description; }

    public double getDistance() {
        //Derive from location data
        return distance;
    }

    public void setDistance(double d) { this.distance = d; }
    public String getPhone() { return phone; }
    public String getWebsite() { return website; }
}
