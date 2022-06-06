package com.classy.myapplication.Fragment;


import android.content.Intent;
import android.location.Location;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import com.classy.myapplication.Activity.ChatActivity;
import com.classy.myapplication.Activity.KindergartenProfile;
import com.classy.myapplication.Activity.UploadedChildrenPhotoActivity;
import com.classy.myapplication.FindKindergarten;
import com.classy.myapplication.FindLocation;
import com.classy.myapplication.Object.Kindergarten;
import com.classy.myapplication.Object.KindergartenUpdate;
import com.classy.myapplication.R;
import com.google.android.material.card.MaterialCardView;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;


public class ParentMainFragment extends Fragment {

    private MaterialCardView Main_card_Profile;
    private MaterialCardView Main_card_image;
    private MaterialCardView Main_card_Chat;
    private Location lastLocation;
    private Kindergarten kindergarten = new Kindergarten();
private KindergartenUpdate kindergartenUpdate = new KindergartenUpdate();
    private FindKindergarten findKindergarten;

    public ParentMainFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_parent_main, container, false);
        findview(view);
        FindLocation findLocation = new FindLocation(getContext(), getActivity());
        lastLocation = findLocation.getLastLocation();
        String garden = getArguments().getString("GARDEN_NAME");
        getDataFromMongoDB(garden);
        getDataFromFireBase(garden);


        Main_card_Profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (kindergarten != null) {
                    Intent intent1 = new Intent(getActivity(), KindergartenProfile.class);
                    intent1.putExtra("KINDERGARTEN", kindergarten);
                    startActivity(intent1);
                }
            }
        });

        Main_card_image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(getActivity(), UploadedChildrenPhotoActivity.class);
                startActivity(intent);

            }
        });

        Main_card_Chat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), ChatActivity.class);
                intent.putExtra("NAME",garden);
                startActivity(intent);
            }
        });
        return view;

    }

    private void getDataFromFireBase(String garden) {
        DatabaseReference mDatabaseRef = FirebaseDatabase.getInstance().getReference("kindergartenUpdate");
        mDatabaseRef.child(garden).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                kindergartenUpdate = dataSnapshot.getValue(KindergartenUpdate.class);


            }
            @Override
            public void onCancelled(DatabaseError databaseError) {
                Toast.makeText(getContext(), databaseError.getMessage(), Toast.LENGTH_SHORT).show();

            }
        });

    }



    private void getDataFromMongoDB(String garden) {
        findKindergarten = new FindKindergarten(lastLocation.getLatitude(), lastLocation.getLongitude(),garden);
        if(findKindergarten.isFindGardenByName()){
            kindergarten = findKindergarten.getKindergarten();
        }
    }

    private void findview(View view) {

        Main_card_Profile = view.findViewById(R.id.Main_card_Profile);
        Main_card_image = view.findViewById(R.id.Main_card_image);
        Main_card_Chat = view.findViewById(R.id.Main_card_Chat);
    }


}