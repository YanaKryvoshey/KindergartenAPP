package com.classy.myapplication;


import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;


import android.Manifest;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Bundle;
import android.util.Log;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnSuccessListener;
import com.main.mylibr.CalendarPermission;
import com.main.mylibr.LocationPermission;
import com.main.mylibr.PhonePermission;


public class MainActivity extends AppCompatActivity {


    private static final String TAG = "MAIN";
    private static Location lastLocation;
    private FusedLocationProviderClient fusedLocationClient;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        LocationPermission.checkAndRequestLocationPermission(this);
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this);

        Log.d(TAG, "onCreate: ");
        getLocation();


    }


    @SuppressLint("MissingPermission")
    private void getLocation() {


        fusedLocationClient.getLastLocation()
                .addOnSuccessListener(this, new OnSuccessListener<Location>() {
                    @Override
                    public void onSuccess(Location location) {
                        Log.d(TAG, "///");
                        // Got last known location. In some rare situations this can be null.
                        if (location != null) {
                            Log.d(TAG, "LOCATION onSuccess: ");
                            Intent intent = new Intent(MainActivity.this, DisplayNearbyKindergarten.class);
                            intent.putExtra("EXTRA_LOCATION",location);
                            startActivity(intent);
                        }
                        else {
                            LocationPermission.checkAndRequestLocationPermission(MainActivity.this);
                        }

                    }
                });








    }










}