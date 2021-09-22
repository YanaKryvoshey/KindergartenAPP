package com.classy.myapplication.Activity;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;

import com.classy.myapplication.Fragment.FragmentMap;
import com.classy.myapplication.Object.Kindergarten;
import com.classy.myapplication.R;

import java.util.ArrayList;

public class MapActivity extends AppCompatActivity {
    private FragmentMap fragmentMap;
    private static final String TAG = "arr";
    private ArrayList<Kindergarten> kindergartens= new ArrayList<>();



    @Override
    protected void onPostResume() {
        super.onPostResume();
    }

    @Override
    protected void onStart() {
        super.onStart();
    }

    @Override
    protected void onStop() {
        super.onStop();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map);
        fragmentMap = new FragmentMap();
        FragmentTransaction ft2 = getSupportFragmentManager().beginTransaction();
        ft2.replace(R.id.main_LAY_map, fragmentMap);
        ft2.commit();

    }


}
