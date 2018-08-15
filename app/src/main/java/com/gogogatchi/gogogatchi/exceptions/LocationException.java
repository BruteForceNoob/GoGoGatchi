package com.gogogatchi.gogogatchi.exceptions;

public class LocationException extends Exception {
    private String message;

    public LocationException(String message) {
    this.message=message;
    }

    @Override
    public String getMessage() {
        return message;
    }




}
