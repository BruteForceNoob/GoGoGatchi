package com.gogogatchi.gogogatchi.core;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Profile implements Parcelable {

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

    protected Profile(Parcel in) {
        destinationName = in.readString();
        imageUrl = in.readString();
        city = in.readString();
        distance = in.readDouble();
        description = in.readString();
        phone = in.readString();
        website = in.readString();
    }

    public static final Creator<Profile> CREATOR = new Creator<Profile>() {
        @Override
        public Profile createFromParcel(Parcel in) {
            return new Profile(in);
        }

        @Override
        public Profile[] newArray(int size) {
            return new Profile[size];
        }
    };

    public Profile() {
        destinationName = "Erick";
        imageUrl = "google.com";
        city = "Bolen";
        distance = 2.2;
        description = "NO";
        phone = "fdhfd";
        website = "sfa";
    }

    public String getDestinationName() { return destinationName; }
    public void setDestinationName(String str) { destinationName = str; }

    public String getImageUrl() { return imageUrl; }
    public void setImageUrl(String str) { imageUrl = str; }

    public String getCity() { return city; }
    public void setCity(String str) { city = str; }

    public String getDescription() { return description; }
    public void setDescription(String str) { description = str; }

    public double getDistance() { return distance; }

    public void setDistance(double d) { this.distance = d; }

    public String getPhone() { return phone; }
    public void setPhone(String str) { phone = str; }

    public String getWebsite() { return website; }
    public void setWebsite(String str) { website = str; }

    @Override
    public int describeContents() { return 0; }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(destinationName);
        parcel.writeString(imageUrl);
        parcel.writeString(city);
        parcel.writeDouble(distance);
        parcel.writeString(description);
        parcel.writeString(phone);
        parcel.writeString(website);
    }
}
