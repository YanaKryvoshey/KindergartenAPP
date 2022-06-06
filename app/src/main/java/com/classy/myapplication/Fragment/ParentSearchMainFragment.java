package com.classy.myapplication.Fragment;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.Looper;
import android.provider.Settings;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import com.classy.myapplication.Activity.DisplayNearbyKindergarten;
import com.classy.myapplication.R;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.card.MaterialCardView;


public class ParentSearchMainFragment extends Fragment {
private MaterialCardView ParentSearchMain_card_Map;
    Location lastLocation;
    FusedLocationProviderClient client;

    private FusedLocationProviderClient fusedLocationClient;
    
    public ParentSearchMainFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_parent_search_main, container, false);
        findView(view);
        client = LocationServices.getFusedLocationProviderClient(getActivity());


        // check condition
        if (ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED && ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            // When permission is granted
            // Call method
            getCurrentLocation();
        } else {
            // When permission is not granted
            // Call method
            requestPermissions(new String[]{Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION}, 100);
        }
        ParentSearchMain_card_Map.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("Pttt", "onCreate: "+lastLocation.getLongitude() + " , " +lastLocation.getLatitude());
                Intent intent = new Intent(getActivity(), DisplayNearbyKindergarten.class);
                intent.putExtra("EXTRA_LOCATION",lastLocation);
                startActivity(intent);

            }
        });
        return view;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        // Check condition
        if (requestCode == 100 && (grantResults.length > 0) && (grantResults[0] + grantResults[1] == PackageManager.PERMISSION_GRANTED)) {
            // When permission are granted
            // Call  method
            getCurrentLocation();
        } else {
            // When permission are denied
            // Display toast
            Toast.makeText(getActivity(), "Permission denied", Toast.LENGTH_SHORT).show();
        }
    }

    @SuppressLint("MissingPermission")
    private void getCurrentLocation() {
        // Initialize Location manager
        LocationManager locationManager = (LocationManager) getActivity().getSystemService(Context.LOCATION_SERVICE);
        // Check condition
        if (locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER) || locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER)) {
            // When location service is enabled
            // Get last location
            client.getLastLocation().addOnCompleteListener(
                    new OnCompleteListener<Location>() {
                        @Override
                        public void onComplete(@NonNull Task<Location> task) {

                            // Initialize location
                            Location location = task.getResult();
                            // Check condition
                            if (location != null) {
                                // When location result is not
                                // null set latitude
                                Log.d("Ptt", "getLatitude() = " + location.getLatitude());
                                Log.d("Ptt", "getLongitude() = " + location.getLongitude());
                                lastLocation  = location;

                            } else {
                                // When location result is null
                                // initialize location request
                                LocationRequest locationRequest = new LocationRequest().setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY).setInterval(10000).setFastestInterval(1000).setNumUpdates(1);

                                // Initialize location call back
                                LocationCallback locationCallback = new LocationCallback() {
                                    @Override
                                    public void
                                    onLocationResult(LocationResult locationResult) {
                                        // Initialize
                                        // location
                                        Location location1 = locationResult.getLastLocation();
                                        // Set latitude
                                        Log.d("Ptt", "getLatitude() = " + location1.getLatitude());
                                        Log.d("Ptt", "getLongitude() = " + location1.getLongitude());
                                        lastLocation = location1;

                                    }
                                };

                                // Request location updates
                                client.requestLocationUpdates(locationRequest, locationCallback, Looper.myLooper());
                            }
                        }
                    });
        } else {
            // When location service is not enabled
            // open location setting
            startActivity(new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS).setFlags(Intent.FLAG_ACTIVITY_NEW_TASK));
        }
    }

    private void findView(View view) {
        ParentSearchMain_card_Map = view.findViewById(R.id.ParentSearchMain_card_Map);
    }
}

/*
<com.google.android.material.card.MaterialCardView
            android:id="@+id/ParentSearchMain_card_Profile"
            android:clickable="true"
            android:layout_width="0dp"
            android:layout_height="0dp"
            android:layout_rowWeight="1"
            android:layout_columnWeight="1"
            android:layout_marginLeft="16dp"
            android:layout_marginRight="16dp"
            android:layout_marginBottom="16dp"
            app:cardCornerRadius="8dp"
            app:cardElevation="8dp">

            <LinearLayout
                android:layout_width="wrap_content"
                android:layout_height="wrap_content"
                android:layout_gravity="center_horizontal|center_vertical"
                android:layout_margin="16dp"
                android:orientation="vertical">

                <TextView
                    android:layout_width="match_parent"
                    android:layout_height="42dp"
                    android:fontFamily="casual"
                    android:text="Profile"
                    android:textColor="#2196F3"
                    android:textSize="15sp"
                    android:textStyle="bold" />

                <ImageView
                    android:layout_width="wrap_content"
                    android:layout_height="match_parent"
                    android:layout_gravity="center_horizontal"
                    android:src="@drawable/user" />



            </LinearLayout>

        </com.google.android.material.card.MaterialCardView>



        <com.google.android.material.card.MaterialCardView
            android:id="@+id/ParentSearchMain_card_Chat"
            android:clickable="true"
            android:layout_width="0dp"
            android:layout_height="0dp"
            android:layout_rowWeight="1"
            android:layout_columnWeight="1"
            android:layout_marginLeft="16dp"
            android:layout_marginRight="16dp"
            android:layout_marginBottom="16dp"
            app:cardCornerRadius="8dp"
            app:cardElevation="8dp">

            <LinearLayout
                android:layout_width="wrap_content"
                android:layout_height="wrap_content"
                android:layout_gravity="center_horizontal|center_vertical"
                android:layout_margin="16dp"
                android:orientation="vertical">
                <TextView
                    android:layout_width="match_parent"
                    android:layout_height="42dp"
                    android:fontFamily="casual"
                    android:text="CHAT"
                    android:textColor="#2196F3"
                    android:textSize="15sp"
                    android:textStyle="bold" />

                <ImageView
                    android:layout_width="wrap_content"
                    android:layout_height="wrap_content"
                    android:layout_gravity="center_horizontal"
                    android:src="@drawable/chat" />



            </LinearLayout>


        </com.google.android.material.card.MaterialCardView>

 */