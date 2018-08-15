package com.gogogatchi.gogogatchi.core;

import com.google.gson.annotations.SerializedName;

public class Reviews {

    @SerializedName("author_name")
    public String author_name;

    @SerializedName("author_url")
    public String author_url;

    @SerializedName("language")
    public String language;

    @SerializedName("profile_photo_url")
    public String profile_photo_url;

    @SerializedName("rating")
    public String rating;

    @SerializedName("relative_time_description")
    public String relative_time_description;

    @SerializedName("text")
    public String text;
}
