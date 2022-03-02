package com.classy.myapplication.Fragment;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.provider.Settings;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridLayout;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import com.classy.myapplication.Activity.DisplayNearbyKindergarten;
import com.classy.myapplication.Activity.MainActivity;
import com.classy.myapplication.R;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.card.MaterialCardView;
import com.main.mylibr.LocationPermission;

import org.jetbrains.annotations.NotNull;

import java.util.concurrent.Executor;


public class MainFragment extends Fragment {

    MaterialCardView Main_card_Profile;
    MaterialCardView Main_card_Map;
    MaterialCardView Main_card_Chat;
    private static final String TAG = "MAIN";
    Location lastLocation;
    private FusedLocationProviderClient fusedLocationClient;

    public MainFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_main, container, false);
        findview(view);
        LocationPermission.checkAndRequestLocationPermission(getActivity());
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(getActivity());
        fetchLsatLocation();


        Main_card_Map.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(getActivity(), DisplayNearbyKindergarten.class);
                intent.putExtra("EXTRA_LOCATION",lastLocation);
                startActivity(intent);

            }
        });
        return view;
    }



    private void findview(View view) {

        Main_card_Profile = view.findViewById(R.id.Main_card_Profile);
        Main_card_Map = view.findViewById(R.id.Main_card_Map);
        Main_card_Chat = view.findViewById(R.id.Main_card_Chat);
    }

    //ask for permission to get location
    private void fetchLsatLocation() {
        if (ActivityCompat.checkSelfPermission(getContext(),Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(getContext(),Manifest.permission.ACCESS_COARSE_LOCATION)== PackageManager.PERMISSION_GRANTED){
            getCurrentLocation();
        }else{
            ActivityCompat.requestPermissions(getActivity(),
                    new String[]{Manifest.permission.ACCESS_FINE_LOCATION,Manifest.permission.ACCESS_COARSE_LOCATION},100);
        }

    }
    //print text if didnt give permission to take the place
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode == 100 && grantResults.length > 0 && (grantResults[0]+grantResults[1] == PackageManager.PERMISSION_GRANTED)){
            getCurrentLocation();
        }else {
            Toast.makeText(getContext(),"Premission denied.", Toast.LENGTH_SHORT).show();
        }
    }
    //get the current location
    @SuppressLint("MissingPermission")
    private void getCurrentLocation() {
        LocationManager locationManager = (LocationManager)getActivity().getSystemService(Context.LOCATION_SERVICE);

        if(locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER) || locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER)) {
            Criteria criteria = new Criteria();
            // Got last known location. In some rare situations this can be null.
            Location location = locationManager.getLastKnownLocation(locationManager.getBestProvider(criteria, false));
            if (location != null) {
                lastLocation = location;
            }
        }else {
            startActivity(new Intent(Settings.ACTION_LOCALE_SETTINGS).setFlags(Intent.FLAG_ACTIVITY_NEW_TASK));
        }
    }
}