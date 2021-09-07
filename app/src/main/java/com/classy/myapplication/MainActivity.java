package com.classy.myapplication;


import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.core.app.ActivityCompat;


import android.Manifest;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.GridLayout;

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
    GridLayout gridLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        gridLayout=(GridLayout)findViewById(R.id.mainGrid);
        LocationPermission.checkAndRequestLocationPermission(this);
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this);

        getLocation();
        Log.d(TAG, "onCreate: ");



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
                            lastLocation=location;
                            setSingleEvent(gridLayout);
                            Log.d(TAG, "LOCATION onSuccess: ");


                        }
                        else {
                            LocationPermission.checkAndRequestLocationPermission(MainActivity.this);
                        }

                    }
                });








    }






    private void setSingleEvent(GridLayout gridLayout) {


        CardView cardView=(CardView)gridLayout.getChildAt(2);
        cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, DisplayNearbyKindergarten.class);
                intent.putExtra("EXTRA_LOCATION",lastLocation);
                startActivity(intent);
            }
        });
        CardView cardViewProduct=(CardView)gridLayout.getChildAt(1);
        cardViewProduct.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
        CardView cardViewWeather=(CardView)gridLayout.getChildAt(0);
        cardViewWeather.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });







    }




}