package com.classy.myapplication;

import android.app.Activity;
import android.content.Context;
import android.location.Location;

import com.google.android.gms.location.FusedLocationProviderClient;

public class FindLastLocation {
    private Context context;
    private Activity activity;
    Location lastLocation;
    private FusedLocationProviderClient fusedLocationClient;

    public FindLastLocation(Context context, Activity activity) {
        this.context = context;
        this.activity = activity;
        //LocationPermission.checkAndRequestLocationPermission(activity);
      //  this.fusedLocationClient = LocationServices.getFusedLocationProviderClient(activity);
       // fetchLsatLocation();
        //getCurrentLocation();
      //  fusedLocationClient = LocationServices.getFusedLocationProviderClient(activity);



    }

    public Location getLastLocation() {
        return lastLocation;
    }

    public void setLastLocation(Location lastLocation) {
        this.lastLocation = lastLocation;
    }


}
//
//    }
//
//
//}
