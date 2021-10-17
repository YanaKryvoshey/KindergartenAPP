package com.classy.myapplication.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.classy.myapplication.R;

public class KindergartenProfile extends AppCompatActivity {

    View view;
    int b1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_kindergarten_profile);
        for (int j=1; j<=7; j++)
        {
            b1=j;
            create_img1("drawable/p"+j, b1);
        }

    }
    void create_img1(String ss, int ID)
    {
        LinearLayout linearLayout = (LinearLayout) findViewById(R.id.imgLayout1);
        LinearLayout.LayoutParams parms = new LinearLayout.LayoutParams(200, 200);
        parms.gravity = Gravity.CENTER;
        parms.setMargins(20, 20, 20, 20);
        final ImageView imageView = new ImageView(this);
        imageView.setLayoutParams(parms);

        int id = getResources().getIdentifier(ss, "id", getPackageName());
        imageView.setImageResource(id);
        linearLayout.addView(imageView);
        imageView.setId(ID);
    }

}