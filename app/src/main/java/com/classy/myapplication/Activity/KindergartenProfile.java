package com.classy.myapplication.Activity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.classy.myapplication.Object.Kindergarten;
import com.classy.myapplication.Object.KindergartenUpdate;
import com.classy.myapplication.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class KindergartenProfile extends AppCompatActivity {
    private TextView fullname_field;
    private TextView address_field;
    private TextView profile_LBL_numOfChildren;
    private TextView profile_LBL_AgeRange;
    private TextView profile_LBL_KindergartenTeacher;
    private TextView profile_LBL_Message;
    private ImageView iamge_profile;
    private KindergartenUpdate updateGarten;
    View view;
    int b1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_kindergarten_profile);
        Kindergarten kindergarten = (Kindergarten) getIntent().getSerializableExtra("KINDERGARTEN");
        findView();
        //if the specific kindergarten found in the FireBase that mean
        // that teacher connect to garden and upload more information about the kindergarten
        if (kindergarten.name != null){
            findKindergartenonFireBse(kindergarten.name);

        }
        if(updateGarten != null){
            setdetails();
        }
        getPlacePhoto(kindergarten);
        fullname_field.setText(kindergarten.name);
        address_field.setText(kindergarten.address);




    }

    private boolean findKindergartenonFireBse(String name) {
        DatabaseReference usersRef = FirebaseDatabase.getInstance("https://kindergartenapp-ea823-default-rtdb.firebaseio.com/").getReference("kindergartenUpdate");
        usersRef.child(name).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                try {
                    updateGarten = dataSnapshot.getValue(KindergartenUpdate.class);
                    Log.d("pttt", "findKindergartenonFireBse  - get KindergartenUpdate");

                    profile_LBL_numOfChildren.setText("" +  updateGarten.getChildren_num());
                    profile_LBL_AgeRange.setText("" + updateGarten.getAgeRange());
                    profile_LBL_KindergartenTeacher.setText("" + updateGarten.getTeachers_num());
                    profile_LBL_Message.setText(updateGarten.getMassege());
                } catch (Exception ex) {
                    Log.d("pttt", "getChatFromFireBase Failed to read from firebase" + ex.toString());
                }
            }

            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
                Log.d("pttt", "Failed to read latitude and longituds.", error.toException());
            }
        });
        return false;
    }

    private void setdetails() {

    }


    private void findView() {
        fullname_field = findViewById(R.id.fullname_field);
        address_field = findViewById(R.id.address_field);
        iamge_profile = findViewById(R.id.iamge_profile);
        profile_LBL_numOfChildren = findViewById(R.id.profile_LBL_numOfChildren);
        profile_LBL_AgeRange = findViewById(R.id.profile_LBL_AgeRange);
        profile_LBL_KindergartenTeacher = findViewById(R.id.profile_LBL_KindergartenTeacher);
        profile_LBL_Message = findViewById(R.id.profile_LBL_Message);
    }

    //In this function I use the GOOGLE API and locate an image of a specific gene. I will note that most genes do not have a picture.
    //For genes that have an image, I display it when the dialog opens.
    private void getPlacePhoto(Kindergarten kindergarten) {
        Log.d("Pttt", "KindergartenProfile - getPlacePhoto: fetching place image");
        if (kindergarten.getPhotoRef() != null) {
            String photoUrl = "https://maps.googleapis.com/maps/api/place/photo?photoreference=" + kindergarten.getPhotoRef() + "&sensor=false&maxheight=300&maxwidth=300&key=AIzaSyDBPfMDN1CGaF5OaA8O83ADn7dUsHkHB4Q";
            Log.d("Pttt", "KindergartenProfile - initViews: Fetching icon: " + photoUrl);
            Glide.with(iamge_profile).load(photoUrl).into(iamge_profile);
        }


    }
//    void create_img1(String ss, int ID)
//    {
//        LinearLayout linearLayout = (LinearLayout) findViewById(R.id.imgLayout1);
//        LinearLayout.LayoutParams parms = new LinearLayout.LayoutParams(200, 200);
//        parms.gravity = Gravity.CENTER;
//        parms.setMargins(20, 20, 20, 20);
//        final ImageView imageView = new ImageView(this);
//        imageView.setLayoutParams(parms);
//
//        int id = getResources().getIdentifier(ss, "id", getPackageName());
//        imageView.setImageResource(id);
//        linearLayout.addView(imageView);
//        imageView.setId(ID);
//    }

}

/*
<HorizontalScrollView
            android:id="@+id/HorizontalScrollView1"
            android:layout_width="match_parent"
            android:layout_height="100dp"
            android:layout_marginBottom="1dp"
            android:background="#FFF"
            android:scrollbars="none">

            <LinearLayout
                android:id="@+id/imgLayout1"
                android:layout_width="wrap_content"
                android:layout_height="match_parent"
                android:orientation="horizontal">

            </LinearLayout>


        </HorizontalScrollView>
* */