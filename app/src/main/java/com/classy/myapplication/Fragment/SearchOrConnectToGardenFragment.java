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
import androidx.fragment.app.FragmentActivity;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import com.classy.myapplication.FindKindergarten;
import com.classy.myapplication.R;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.button.MaterialButton;

import java.util.ArrayList;


public class SearchOrConnectToGardenFragment extends Fragment {

    MaterialButton OR_BTN_connect;
    MaterialButton OR_BTN_search;
    MaterialButton OR_BTN_Teacher;
    ArrayList<String> names;
    FindKindergarten findKindergarten;
    Location lastLocation;
    FusedLocationProviderClient client;
    public SearchOrConnectToGardenFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_search_or_connect_to_garden, container, false);

        findviews(view);
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


        OR_BTN_connect.setOnClickListener(new View.OnClickListener() {
            @Override
            //the new User is parent that want to connect to hes garden
            public void onClick(View v) {
                Bundle bundle = new Bundle();
                bundle.putInt("USERNUM", 1);
                bundle.putStringArrayList("NAMES", names);
                NavController navController = Navigation.findNavController(view);
                navController.navigate(R.id.action_searchOrConnectToGardenFragment_to_choiceGardenNameFragment, bundle);
            }
        });

        OR_BTN_search.setOnClickListener(new View.OnClickListener() {
            @Override
            // the new User is Parent that search garden
            public void onClick(View v) {
                Bundle bundle2 = new Bundle();
                bundle2.putInt("USERNUM", 2);
                NavController navController2 = Navigation.findNavController(view);
                navController2.navigate(R.id.action_searchOrConnectToGardenFragment_to_uploadedIDPhotoActivity, bundle2);
            }
        });

        OR_BTN_Teacher.setOnClickListener(new View.OnClickListener() {
            @Override
            //the new User is Teacher that want to connect to hes garden
            public void onClick(View v) {
                Bundle bundle = new Bundle();
                bundle.putInt("USERNUM", 3);
                bundle.putStringArrayList("NAMES", names);
                NavController navController = Navigation.findNavController(view);
                navController.navigate(R.id.action_searchOrConnectToGardenFragment_to_choiceGardenNameFragment, bundle);
            }
        });
        return view;

    }

    private void findGardensName(FragmentActivity activity, Context context) {



            findKindergarten = new FindKindergarten(lastLocation.getLatitude(), lastLocation.getLongitude());
        if (findKindergarten != null){
            names = findKindergarten.getNames();
        }





    }

    private void findviews(View view) {
        OR_BTN_connect = view.findViewById(R.id.OR_BTN_connect);
        OR_BTN_search = view.findViewById(R.id.OR_BTN_search);
        OR_BTN_Teacher = view.findViewById(R.id.OR_BTN_Teacher);
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
                                findGardensName(getActivity(), getContext());
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
                                        findGardensName(getActivity(), getContext());
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
}