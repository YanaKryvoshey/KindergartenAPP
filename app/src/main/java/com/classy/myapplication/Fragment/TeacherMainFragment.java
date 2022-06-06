package com.classy.myapplication.Fragment;

import android.content.Intent;
import android.location.Location;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import com.classy.myapplication.Activity.KindergartenProfile;
import com.classy.myapplication.FindKindergarten;
import com.classy.myapplication.FindLocation;
import com.classy.myapplication.Object.Kindergarten;
import com.classy.myapplication.R;
import com.google.android.material.card.MaterialCardView;

public class TeacherMainFragment extends Fragment {

    private MaterialCardView TeacherMain_card_Profile;
    private MaterialCardView MainTeacher_card_Upgrade;
    private Location lastLocation;
    private Kindergarten kindergarten = new Kindergarten();
    private FindKindergarten findKindergarten;
    public TeacherMainFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_teacher_main, container, false);
        findView(view);
        FindLocation findLocation = new FindLocation(getContext(),getActivity());
        lastLocation = findLocation.getLastLocation();
        String garden = getArguments().getString("GARDEN_NAME");
        findKindergarten = new FindKindergarten(lastLocation.getLatitude(), lastLocation.getLongitude(),garden);
//        if (findKindergarten.getKindergarten() != null){
//            kindergarten = findKindergarten.getKindergarten();
//        }


        TeacherMain_card_Profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(findKindergarten.getKindergarten().name != null){
                    showGardenProfile();
                }

            }
        });
        MainTeacher_card_Upgrade.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                findgarden(garden,view);

            }
        });
        return view;
    }

    private void showGardenProfile() {
        Intent intent1 = new Intent(getActivity(), KindergartenProfile.class);
        intent1.putExtra("KINDERGARTEN", findKindergarten.getKindergarten());
        startActivity(intent1);
    }

    private void findgarden(String garden,View view) {
        kindergarten = findKindergarten.getKindergarten();
        if (kindergarten != null){
            Log.d("Pttt","findgarden " + kindergarten.name);


            Bundle bundle = new Bundle();
            bundle.putSerializable("KINDERGARTEN", kindergarten);
            NavController navController = Navigation.findNavController(view);
            navController.navigate(R.id.action_teacherMainFragment_to_upgradeTeacherFragment,bundle);
        }

    }

    private void findView(View view) {
        TeacherMain_card_Profile = view.findViewById(R.id.TeacherMain_card_Profile);
        MainTeacher_card_Upgrade = view.findViewById(R.id.MainTeacher_card_Upgrade);

    }
}