package com.gogogatchi.gogogatchi.util;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.widget.Toast;

import com.gogogatchi.gogogatchi.Exceptions.LocationException;

import static android.support.v4.app.ActivityCompat.requestPermissions;

public class MapUtil implements LocationListener {
    Context context;
    private LocationManager locationManager;
    private boolean isGpsEnabled=false;
    private boolean isNetworkEnabled=false;
    public MapUtil(Context context)
    {this.context=context;}

    public Location getLocation() throws LocationException {

        locationManager= (LocationManager) context.getSystemService(Context.LOCATION_SERVICE);
        isGpsEnabled=locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);
        isNetworkEnabled = locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER);
        if(ContextCompat.checkSelfPermission(context,android.Manifest.permission.ACCESS_FINE_LOCATION)!= PackageManager.PERMISSION_GRANTED)
        {
            throw new LocationException("Location not enabled!");

        }

        else if(isGpsEnabled)
        {
            locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER,6000,3218,this);
            return locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
        }
        else
        {
            throw new LocationException("Location not enabled!");

        }

    }
    @Override
    public void onLocationChanged(Location location) {

    }

    @Override
    public void onStatusChanged(String s, int i, Bundle bundle) {

    }

    @Override
    public void onProviderEnabled(String s) {

    }

    @Override
    public void onProviderDisabled(String s) {

    }
}
