package com.classy.myapplication.Fragment;

import android.location.Location;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;

import androidx.fragment.app.Fragment;

import com.classy.myapplication.Object.Kindergarten;
import com.classy.myapplication.Dialog.KindergartenDetailsDialog;
import com.classy.myapplication.R;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.ArrayList;
import java.util.HashMap;

public class FragmentMap extends Fragment {
    private static final String TAG = "sss";
    GoogleMap map;
    private ArrayList<Kindergarten> kindergartens;
    private Location lastLocation;
    private static final float DEFAULT_ZOOM = 15f;
    HashMap<Marker, Kindergarten> hashMap = new HashMap<Marker, Kindergarten>();

    public FragmentMap(ArrayList<Kindergarten> kindergartens) {
        Log.d(TAG, "FragmentMap: ");
        this.kindergartens = kindergartens;
        if (kindergartens!= null) {
            Log.d(TAG, "/n" + kindergartens.toString());
        }

    }

    public FragmentMap() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        Log.d("pttt", "onCreateView - Fragment_List");
        View view = inflater.inflate(R.layout.fragment_map, container, false);
        SupportMapFragment supportMapFragment = (SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.google_map);
        supportMapFragment.getMapAsync(new OnMapReadyCallback() {
            @Override
            public void onMapReady(GoogleMap googleMap) {
                map = googleMap;

                for (Kindergarten kindergarten : kindergartens) {

                    LatLng latLng = new LatLng(kindergarten.lat, kindergarten.lng);
                    Log.d(TAG, "onMapReady: " + latLng);
                    Marker marker = map.addMarker(new MarkerOptions().position(latLng)
                            .title(kindergarten.getName())
                            .snippet("Rating:" )

                    );
                    hashMap.put(marker, kindergarten);

                }
                map.setOnInfoWindowClickListener(new GoogleMap.OnInfoWindowClickListener() {
                    @Override
                    public void onInfoWindowClick(Marker marker) {
                        Kindergarten kindergarten = hashMap.get(marker);
                        if (kindergarten != null) {
                            KindergartenDetailsDialog kindergartenDetailsDialog = new KindergartenDetailsDialog(getContext(), kindergarten);
                            createNewPlaceDetailsDialog(kindergartenDetailsDialog);
                        }
                    }
                });

                LatLng latLng = new LatLng(kindergartens.get(1).getLat(), kindergartens.get(1).getLng());
                map.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng, DEFAULT_ZOOM));
            }
        });
        return view;
    }


    private void createNewPlaceDetailsDialog(KindergartenDetailsDialog kindergartenDetailsDialog) {
        Log.d(TAG, "createNewPlaceDetailsDialog: Creating new places dialog");
        kindergartenDetailsDialog.show();
        kindergartenDetailsDialog.getWindow().setLayout(WindowManager.LayoutParams.WRAP_CONTENT
                , WindowManager.LayoutParams.WRAP_CONTENT);
        kindergartenDetailsDialog.getWindow().setGravity(Gravity.CENTER_HORIZONTAL | Gravity.CENTER_VERTICAL);
        kindergartenDetailsDialog.getWindow().setDimAmount(0.9f);
    }

}