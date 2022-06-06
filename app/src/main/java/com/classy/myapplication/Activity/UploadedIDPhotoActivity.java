package com.classy.myapplication.Activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.utils.widget.ImageFilterView;

import com.classy.myapplication.Dialog.NewAccountDialog;
import com.classy.myapplication.R;
import com.google.android.material.button.MaterialButton;

import static com.classy.myapplication.R.id;

public class UploadedIDPhotoActivity extends AppCompatActivity {
    private static final int GALLERY_REQUEST_CODE = 3;
    ImageFilterView ID_IMV_idimage;
    MaterialButton ID_BTN_uploadphoto;
    int USER;
    String GardenName = "NONE";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_uploaded_idphoto);
        Intent i = getIntent();
        USER = i.getIntExtra("USERNUM",-1);
        if (USER == 1){
            GardenName = i.getStringExtra("GARDENNAME");
        }
        findView();

        ID_BTN_uploadphoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pickFromGallery();
            }
        });

    }

    private void findView() {
        ID_IMV_idimage = findViewById(id.ID_IMV_idimage);
        ID_BTN_uploadphoto = findViewById(R.id.ID_BTN_uploadphoto);
    }

    private void pickFromGallery(){

       //Create an Intent with action as ACTION_PICK
        Intent intent=new Intent(Intent.ACTION_PICK);
        // Sets the type as image/*. This ensures only components of type image are selected
        intent.setType("image/*");
        //We pass an extra array with the accepted mime types. This will ensure only components with these MIME types as targeted.
        String[] mimeTypes = {"image/jpeg", "image/png"};
        intent.putExtra(Intent.EXTRA_MIME_TYPES,mimeTypes);
        // Launching the Intent
        startActivityForResult(intent,GALLERY_REQUEST_CODE);
    }

@Override
    public void onActivityResult(int requestCode,int resultCode,Intent data) {
    // Result code is RESULT_OK only if the user selects an Image
    super.onActivityResult(requestCode, resultCode, data);
    if (resultCode == Activity.RESULT_OK)
        switch (requestCode) {
            case GALLERY_REQUEST_CODE:
                //data.getData returns the content URI for the selected Image
                Uri selectedImage = data.getData();
                ID_IMV_idimage.setImageURI(selectedImage);
                checkImage();
                break;
        }
}

    private void checkImage() {
        //Check if the new user have children
        //openDialog(this);
        openNewAccountActivity();
    }

    private void openNewAccountActivity() {
        Intent myIntent = new Intent(UploadedIDPhotoActivity.this, NewAccountActivity.class);
        myIntent.putExtra("USERNUM",USER);
        if (USER == 1){
            myIntent.putExtra("GARDENNAME",GardenName);
        }

        startActivity(myIntent);
    }

    private void openNewAccountFragment() {
        //Fragment fragment = new NewAccountFragment();
       // FragmentManager fragmentManager = getSupportFragmentManager();
        //fragmentManager.beginTransaction().replace(R.id.container, fragment).commit();
    }

    private void openDialog(Context context) {
        NewAccountDialog newAccountDialog = new NewAccountDialog(context, USER, GardenName);
        newAccountDialog.show();
        int height = (int) (getResources().getDisplayMetrics().heightPixels );
        int width = (int) (getResources().getDisplayMetrics().widthPixels );
        newAccountDialog.getWindow().setLayout(width, height);
        newAccountDialog.getWindow().setGravity(Gravity.CENTER_HORIZONTAL | Gravity.CENTER_VERTICAL);
        newAccountDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        newAccountDialog.getWindow().setDimAmount(1f);
    }


}