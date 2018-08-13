package com.gogogatchi.gogogatchi.core;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

// This class represents a Json object returned from a query to google places api
public class GoogleQuery{

    @SerializedName("html_attributions")
    private ArrayList<String> html_attributions;

    @SerializedName("results")
    private ArrayList<LocationData> data;

    @SerializedName("status")
    private String status;

    @SerializedName("reviews")
    private ArrayList<Reviews> reviews;

    public ArrayList<Reviews> getReviews() { return reviews; }
    public List<LocationData> getData() { return data; }

    public String getStatus() { return status; }
    public GoogleQuery()
    {
        html_attributions = new ArrayList<>();
        data = new ArrayList<>();
        status = "DEFAULT";
    }
}
