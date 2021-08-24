package com.classy.myapplication;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.bumptech.glide.Glide;
import com.google.android.material.button.MaterialButton;

public class KindergartenDetailsDialog extends Dialog {

    public static final String TAG = "pttt";
    String Appid= "surfapp-gwucv";
    private MaterialButton addToFavorite;
    private MaterialButton deleteFromFavorite;
    private Context context;
    private TextView name;
    private TextView address;
    private TextView rating;
    private ImageView photo;
    private ImageView exitBtn;
    private Kindergarten kindergarten;

    public KindergartenDetailsDialog(@NonNull Context context) {
        super(context);
    }

    public KindergartenDetailsDialog(@NonNull Context context, Kindergarten kindergarten) {
        super(context);
        this.context = context;
        this.kindergarten=kindergarten;
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d(TAG, "onCreate: new Place details dialog dialog");
        setContentView(R.layout.dialog_kindergarten_details);


        initViews();
        displayPlaceDetails();

    }
    private void displayPlaceDetails() {
        Log.d(TAG, "displayPlaceDetails: Displaying place details: " + kindergarten.toString());
        if (kindergarten.getName() != null) {
            name.setText(kindergarten.getName());
        }

        if (kindergarten.getRat() != null) {
            rating.setText("Rating: " + kindergarten.getRat().toString());
        } else {
            rating.setVisibility(View.GONE);
        }
        if(kindergarten.getPhotoRef()!= null) {
            getPlacePhoto();
        }

    }
    private void getPlacePhoto() {
        Log.d(TAG, "getPlacePhoto: fetching place image");

        String photoUrl = "https://maps.googleapis.com/maps/api/place/photo?photoreference="+kindergarten.getPhotoRef()+"&sensor=false&maxheight=300&maxwidth=300&key=AIzaSyDBPfMDN1CGaF5OaA8O83ADn7dUsHkHB4Q";
        Log.d(TAG, "initViews: Fetching icon: " + photoUrl);
        Glide.with(photo).load(photoUrl).into(photo);


    }

    /**
     * Initialize the views
     */
    private void initViews() {
        Log.d(TAG, "initViews: initing views");
        name = findViewById(R.id.placeDialog_LBL_placeName);
        rating = findViewById(R.id.placeDialog_LBL_placeRating);
        photo = findViewById(R.id.placeDialog_IMG_placeImage);
        exitBtn = findViewById(R.id.placeDialog_BTN_exitButton);





        exitBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dismiss();
            }
        });
    }







}
