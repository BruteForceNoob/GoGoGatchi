package com.gogogatchi.gogogatchi.core;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.io.Serializable;

public class User implements Serializable {
    private String username;
    private String password;
    private double lat;
    private double longitude;

    public Integer getDistance() {
        return distance;
    }

    public void setDistance(Integer distance) {
        this.distance = distance;
    }

    private Integer distance;


    public List<LocationData> getLikedLocations() {
        return likedLocations;
    }

    public void setLikedLocations(List<LocationData> likedLocations) {
        this.likedLocations = likedLocations;
    }

    private String email;
    private List<LocationData> likedLocations;

    public String getInterest() {
        return interest;
    }

    public void setInterest(String interest) {
        this.interest = interest;
    }

    private Map<String, Boolean> interests= new HashMap<>();

    public String getProfileImage() {
        return profileImage;
    }

    public void setProfileImage(String profileImage) {
        this.profileImage = profileImage;
    }

    private String profileImage;

    //Empty constructor
    public User() {

    }
    //

    //new (old push)
    private String userId;
    private String gender;
    private String age;
    private String interest;

    public User(User rightUser) {
        this.userId = rightUser.userId;
        this.username = rightUser.username;
        this.gender = rightUser.gender;
        this.age = rightUser.age;
        this.interests = rightUser.interests;
        this.profileImage=rightUser.profileImage;
        this.distance=rightUser.distance;
    }

    /* old
    public User(String userId, String username, String gender, String age, String interest,String profileImage) {
        this.userId = userId;
        this.username = username;
        this.gender = gender;
        this.age = age;
        this.interest = interest;
        this.profileImage=profileImage;
    }
    */

    //new user 8-6-18
    public User(String userId, String username, String gender, String age, String profileImage,Map<String,Boolean> interests, Integer distance) {
        this.userId = userId;
        this.username = username;
        this.gender = gender;
        this.age = age;
        this.interests = interests;
        this.profileImage=profileImage;
        this.distance=distance;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getAge() {
        return age;
    }

    public void setAge(String age) {
        this.age = age;
    }

    /*old singular interest
    public String getInterest() {
        return interest;
    }

    public void setInterest(String interest) {
        this.interest = interest;
    }
    */

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    //end new

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Map<String, Boolean> getInterests() {
        return interests;
    }

    public void setInterests(Map<String, Boolean> interests) {
        this.interests = interests;
    }

    public double getLongitude() {

        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public double getLat() {
        return lat;
    }

    public void setLat(double lat) {
        this.lat = lat;
    }

}
