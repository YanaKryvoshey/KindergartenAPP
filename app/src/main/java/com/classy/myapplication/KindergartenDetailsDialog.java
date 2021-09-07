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


import org.bson.Document;

import io.realm.mongodb.RealmResultTask;
import io.realm.mongodb.User;
import io.realm.mongodb.mongo.MongoClient;
import io.realm.mongodb.mongo.MongoCollection;
import io.realm.mongodb.mongo.MongoDatabase;
import io.realm.mongodb.mongo.iterable.MongoCursor;
public class KindergartenDetailsDialog extends Dialog {

    public static final String TAG = "pttt";

    private MaterialButton addToFavorite;
    private MaterialButton deleteFromFavorite;
    private Context context;
    private TextView name;
    private TextView address;
    private TextView rating;
    private ImageView photo;
    private ImageView exitBtn;
    private Kindergarten kindergarten;
    MongoCollection<Document> mongoCollection;
    MongoDatabase mongoDatabase;
    MongoClient mongoClient;
    User user1;

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

        user1= MyApp.app.currentUser();
        mongoClient = user1.getMongoClient("mongodb-atlas");
        mongoDatabase = mongoClient.getDatabase("SurfData");
        mongoCollection = mongoDatabase.getCollection("FavoriteBeachData");

        initViews();
        isBeachAlreadyExistInFavorites();
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
        addToFavorite=findViewById(R.id.placeDialog_BTN_addToFavorite);
        deleteFromFavorite=findViewById(R.id.placeDialog_BTN_deleteFromFavorite);





        exitBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dismiss();
            }
        });
        addToFavorite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                addToListOfUser();
                dismiss();

            }
        });

        deleteFromFavorite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                deleteBeachFromListOfUser();
                dismiss();


            }
        });


    }

    private void addToListOfUser() {
        mongoCollection.insertOne(new Document("userid", user1.getId()).append("name:",kindergarten.name).append("rating:",kindergarten.rat)
                .append("Photo ref",kindergarten.photoRef))
                .getAsync(result ->{
                    if (result.isSuccess()){
                        Log.d(TAG, "Data Success ");
                    }
                    else{
                        Log.d(TAG, "Data Failed ");
                    }
                });


    }




    private void isBeachAlreadyExistInFavorites() {
        Document query= new Document().append("userid", user1.getId());
        RealmResultTask<MongoCursor<Document>> findTask=mongoCollection.find(query).iterator();
        final String[] data = new String[1];
        data[0]="";

        findTask.getAsync(task->{
            if(task.isSuccess()){
                MongoCursor<Document> results=task.get();
                Boolean flag=false;
                while (results.hasNext()){
                    Document currentDocument =results.next();
                    if (currentDocument.getString("name:").equals(kindergarten.name))
                    {
                        flag=true;
                    }

                }
                if(flag){
                    deleteFromFavorite.setVisibility(View.VISIBLE);
                    addToFavorite.setVisibility(View.GONE);
                }
                else {
                    deleteFromFavorite.setVisibility(View.GONE);
                    addToFavorite.setVisibility(View.VISIBLE);

                }

            }
            else{
                Log.d(TAG, "Task eror");
            }
        });




    }


    private void deleteBeachFromListOfUser() {
        mongoCollection.deleteOne(new Document("userid", user1.getId()).append("name:",kindergarten.name))
                .getAsync(result ->{
                    if (result.isSuccess()){
                        Log.d(TAG, "Data Success ");
                    }
                    else{
                        Log.d(TAG, "Data Failed ");
                    }
                });
    }







}
