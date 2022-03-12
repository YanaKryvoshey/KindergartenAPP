package com.classy.myapplication.Fragment;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.classy.myapplication.R;
import com.google.android.material.button.MaterialButton;


public class SearchOrConnectToGardenFragment extends Fragment {

    MaterialButton OR_BTN_connect;
    MaterialButton OR_BTN_search;
    public SearchOrConnectToGardenFragment() {
        // Required empty public constructor
    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {


        View view = inflater.inflate(R.layout.fragment_search_or_connect_to_garden, container, false);

       findviews(view);

       OR_BTN_connect.setOnClickListener(new View.OnClickListener() {
           @Override
           //the new User is teacher
           public void onClick(View v) {
              // navController.navigate(R.id.action_logInFragment_to_mainFragment);
           }
       });

       OR_BTN_search.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
               // the new User is Parent
               NavController navController = Navigation.findNavController(view);
               navController.navigate(R.id.action_searchOrConnectToGardenFragment_to_uploadedIDPhotoActivity);
           }
       });
        return view;
    }

    private void findviews(View view) {
        OR_BTN_connect = view.findViewById(R.id.OR_BTN_connect);
        OR_BTN_search = view.findViewById(R.id.OR_BTN_search);
    }
}