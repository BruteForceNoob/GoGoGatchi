package com.gogogatchi.gogogatchi.core;


import android.os.Parcel;
import android.os.Parcelable;
import android.util.Log;

import com.gogogatchi.gogogatchi.BuildConfig;
import com.gogogatchi.gogogatchi.core.LocationAssets;

import com.google.gson.annotations.SerializedName;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

public class LocationData extends LocationAssets implements Parcelable{

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

    protected LocationData(Parcel in) {
        geometry = in.readParcelable(Geometry.class.getClassLoader());
        icon = in.readString();
        id = in.readString();
        name = in.readString();
        opening_hours = in.readParcelable(OpenState.class.getClassLoader());
        photos = in.createTypedArrayList(Photos.CREATOR);
        place_id = in.readString();
        plus_code = in.readParcelable(PlusCode.class.getClassLoader());
        rating = in.readDouble();
        reference = in.readString();
        scope = in.readString();
        types = in.createStringArrayList();
        vicinity = in.readString();
    }

    public static final Creator<LocationData> CREATOR = new Creator<LocationData>() {
        @Override
        public LocationData createFromParcel(Parcel in) {
            return new LocationData(in);
        }

        @Override
        public LocationData[] newArray(int size) {
            return new LocationData[size];
        }
    };

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

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeParcelable(geometry, i);
        parcel.writeString(icon);
        parcel.writeString(id);
        parcel.writeString(name);
        parcel.writeParcelable(opening_hours, i);
        parcel.writeTypedList(photos);
        parcel.writeString(place_id);
        parcel.writeParcelable(plus_code, i);
        parcel.writeDouble(rating);
        parcel.writeString(reference);
        parcel.writeString(scope);
        parcel.writeStringList(types);
        parcel.writeString(vicinity);
    }
}
