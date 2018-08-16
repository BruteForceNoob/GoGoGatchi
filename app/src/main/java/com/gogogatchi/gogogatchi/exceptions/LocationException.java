package com.gogogatchi.gogogatchi.exceptions;

public class LocationException extends Exception {
    private String message;

    // explicit location exception tracking on get devece location
    public LocationException(String message) {
    this.message=message;
    }

    @Override
    public String getMessage() {
        return message;
    }




}
