package com.gogogatchi.gogogatchi.core;


import android.os.Parcel;
import android.util.Log;

import com.gogogatchi.gogogatchi.BuildConfig;
import com.gogogatchi.gogogatchi.BuildConfig.*;

import com.google.gson.annotations.SerializedName;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

public class LocationData{
    private class GPSCoordinates{
        @SerializedName("lat")
        private double lattitude;

        @SerializedName("lon")
        private double longitutde;

        public double getLattitude() { return lattitude; }
        public double getLongitutde() { return longitutde; }

        GPSCoordinates() {
            lattitude = 0.0;
            longitutde = 0.0;
        }
    }

    private class ViewPort{
        @SerializedName("northeast")
        private GPSCoordinates northeast;

        @SerializedName("southwest")
        private GPSCoordinates southwest;

        public GPSCoordinates getNortheast() { return northeast; }
        public GPSCoordinates getSouthwest() { return southwest; }

        ViewPort() {
            northeast = new GPSCoordinates();
            southwest = new GPSCoordinates();
        }
    }

    private class Geometry{
        @SerializedName("location")
        private GPSCoordinates location;

        @SerializedName("viewport")
        private ViewPort viewport;

        protected Geometry(Parcel in) {

            location = in.readParcelable(GPSCoordinates.class.getClassLoader());
            viewport = in.readParcelable(ViewPort.class.getClassLoader());
        }

        public GPSCoordinates getLocation() { return location; }
        public ViewPort getViewport() { return viewport; }

        Geometry() {
            location = new GPSCoordinates();
            viewport = new ViewPort();
        }
    }

    private class OpenState{

        @SerializedName("open_now")
        private boolean open_now;

        public boolean getOpenNow() { return open_now; }
        OpenState() {
            open_now = false;
        }
    }

    private class Photos{

        @SerializedName("height")
        private int height;

        @SerializedName("html_attributions")
        private ArrayList<String> html_attributions;

        @SerializedName("width")
        private int width;

        @SerializedName("photo_reference")
        private String photo_reference;

        public int getHeight() { return height; }
        public int getWidth() { return width; }
        public String getPhotoReference() { return photo_reference; }
        public ArrayList<String> getHtmlAttributions() { return html_attributions; }

        Photos() {
            height = 100;
            width = 100;
            photo_reference = "DEFAULT";
            html_attributions = new ArrayList<>();
        }
    }

    private class PlusCode{

        @SerializedName("compound_code")
        private String compound_code;

        @SerializedName("global_code")
        private String global_code;

        public String getCompound_code() { return compound_code; }
        public String getGlobal_code() { return global_code; }

        PlusCode() {
            compound_code = "DEFAULT";
            global_code = "DEFAULT";
        }
    }

    @SerializedName("geometry")
    private Geometry geometry;

    @SerializedName("icon")
    private String icon;

    @SerializedName("id")
    private String id; // deprecated, not unique

    @SerializedName("name")
    private String name;

    @SerializedName("opening_hours")
    private OpenState opening_hours;

    @SerializedName("photos")
    private ArrayList<Photos> photos;

    @SerializedName("place_id")
    private String place_id; // unique identifier

    @SerializedName("plus_code")
    private PlusCode plus_code;

    @SerializedName("rating")
    private double rating;

    @SerializedName("reference")
    private String reference;

    @SerializedName("scope")
    private String scope;

    @SerializedName("types")
    ArrayList<String> types;

    @SerializedName("vicinity")
    private String vicinity;

    public Geometry getGeometry() {
        return geometry;
    }

    public String getLocationIcon() {
        return icon;
    }

    public String getID() {
        return id;
    }

    public String getLocationName() {
        return name;
    }

    public OpenState getOpenState() {
        return opening_hours;
    }

    public ArrayList<Photos> getPhoto() {
        return photos;
    }

    public String getPlaceID() {
        return place_id;
    }

    public PlusCode getPlusCode() {
        return plus_code;
    }

    public double getRating() {
        return rating;
    }

    public String getLocationReference() {
        return reference;
    }

    public String getScope() {
        return scope;
    }

    public ArrayList<String> getTypes() {
        return types;
    }

    public String getVicinity() {
        return vicinity;
    }

    // Query by placeid
    // https://maps.googleapis.com/maps/api/place/details/json?key=[YOUR API KEY]&placeid=ChIJTydCFXdnHTERB3oVT1UZDRI

    public URL getImageUrl() throws MalformedURLException {
        String maxWidth = "400";

        if (photos.isEmpty() != true) {
            String imgURL = "https://maps.googleapis.com/maps/api/place/photo?";
            imgURL += "&photo_reference=" + photos.get(0).photo_reference;
            imgURL += "&sensor=false&maxwidth=" + maxWidth;
            imgURL += "&key=" + BuildConfig.ApiKey;

            return new URL(imgURL);
        }
        else return null;
    }

    public LocationData()
    {
        geometry = new Geometry();
        icon = "DEFAULT";
        id = "DEFAULT";
        name = "DEFAULT";
        opening_hours = new OpenState();
        photos = new ArrayList<>();
        place_id = "#DEFAULT";
        plus_code = new PlusCode();
        rating = 0.0;
        reference = "DEFAULT";
        scope = "DEFAULT";
        types = new ArrayList<>();
        vicinity = "DEFAULT";
    }
}
