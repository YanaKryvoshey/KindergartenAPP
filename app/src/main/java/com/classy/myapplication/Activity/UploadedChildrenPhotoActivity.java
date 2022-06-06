package com.classy.myapplication.Activity;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageSwitcher;
import android.widget.ImageView;
import android.widget.Toast;
import android.widget.ViewSwitcher;

import androidx.appcompat.app.AppCompatActivity;

import com.classy.myapplication.R;
import com.google.android.material.button.MaterialButton;

import java.util.ArrayList;

import javax.annotation.Nullable;

public class UploadedChildrenPhotoActivity extends AppCompatActivity {

    private ImageView children_BTN_previous;
    private ImageView children_BTN_next;
    private ImageSwitcher children_IMV_childrenimage;
    private MaterialButton children_BTN_uploadphoto;

    private ArrayList<Uri> imageUris;
    int position = 0 ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_uploaded_children_photo);
        findView();
        imageUris = new ArrayList<>();
        children_IMV_childrenimage.setFactory(new ViewSwitcher.ViewFactory() {
            @Override
            public View makeView() {
                ImageView imageView = new ImageView(getApplicationContext());
                return imageView;
            }
        });
        children_BTN_uploadphoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pickImagesIntent();
            }
        });
        children_BTN_previous.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (position>0){
                    position--;
                    children_IMV_childrenimage.setImageURI(imageUris.get(position));
                }else {
                    Toast.makeText(UploadedChildrenPhotoActivity.this,"No Previous images", Toast.LENGTH_SHORT).show();
                }
            }
        });
        children_BTN_next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (position<imageUris.size() -1){
                    position++;
                    children_IMV_childrenimage.setImageURI(imageUris.get(position));
                }else {
                    Toast.makeText(UploadedChildrenPhotoActivity.this,"No more images", Toast.LENGTH_SHORT).show();

                }
            }
        });
    }

    private void pickImagesIntent(){
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true);
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent,"select images"),0);
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent date) {
        super.onActivityResult(requestCode, resultCode, date);
        if (requestCode == 0){
            if (resultCode == Activity.RESULT_OK){
                if (date.getClipData() != null){
                    //picked multiple images
                    int count = date.getClipData().getItemCount();
                    for (int i=0; i<count;i++){
                        Uri uri = date.getClipData().getItemAt(i).getUri();
                        imageUris.add(uri);
                    }
                    children_IMV_childrenimage.setImageURI(imageUris.get(0));
                    position = 0;
                }else {
                    //picked single images
                    Uri uri = date.getData();
                    imageUris.add(uri);
                    children_IMV_childrenimage.setImageURI(uri);
                    position = 0;
                }
            }
        }

    }

    private void findView() {
        children_BTN_previous = findViewById(R.id.children_BTN_previous);
        children_BTN_next = findViewById(R.id.children_BTN_next);
        children_IMV_childrenimage = findViewById(R.id.children_IMV_childrenimage);
        children_BTN_uploadphoto = findViewById(R.id.children_BTN_uploadphoto);
    }
}