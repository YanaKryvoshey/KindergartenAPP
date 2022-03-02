package com.classy.myapplication.Activity;



import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;


import com.classy.myapplication.MyApp;
import com.classy.myapplication.Dialog.NewAccountDialog;
import com.classy.myapplication.Interface.NewAccountDialogListener;
import com.classy.myapplication.Object.ParentUser;
import com.classy.myapplication.R;
import com.google.android.material.button.MaterialButton;

import org.bson.Document;

import java.util.concurrent.atomic.AtomicReference;

import io.realm.mongodb.Credentials;
import io.realm.mongodb.User;
import io.realm.mongodb.mongo.MongoClient;
import io.realm.mongodb.mongo.MongoCollection;
import io.realm.mongodb.mongo.MongoDatabase;

public class LoginActivity extends AppCompatActivity implements NewAccountDialogListener {


    String TAG="Pttt";
    MongoDatabase mongoDatabase;
    MongoClient mongoClient;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
    }

    @Override
    public void getInfoUser(ParentUser parentUser) {

        String emailUser =parentUser.getEmail();
        String passUser = parentUser.getPassword();


        //Credentials emailPasswordCredentials = Credentials.emailPassword(emailUser,passUser);
        MyApp.app.getEmailPassword().registerUserAsync(emailUser,passUser, result -> {
            if (result.isSuccess()) {
                Log.d(TAG, "Sign Up Success ");
                loginUser(parentUser);
            } else {
                Log.d(TAG, "onClick: ");
            }
        });

    }

    private void loginUser(ParentUser parentUser) {
        Credentials emailPasswordCredentials = Credentials.emailPassword(parentUser.getEmail(),parentUser.getPassword());
        AtomicReference<User> user = new AtomicReference<User>();
        MyApp.app.loginAsync(emailPasswordCredentials, it -> {
            if (it.isSuccess()) {
                Log.v("AUTH", "Successfully authenticated using an email and password.");
                user.set(MyApp.app.currentUser());
                saveDetailsUser(parentUser);
            } else {
                Log.e("AUTH", it.getError().toString());
            }
        });
    }

    private void logOutUser() {
        MyApp.app.currentUser().logOutAsync(result -> {
            if (result.isSuccess()){
                Log.d(TAG, "logOutUser SUCCSSES");
            }
            else{
                Log.d(TAG, "logOutUser: ");
            }
        });
    }



    private void saveDetailsUser(ParentUser parentUser) {
        User user1=MyApp.app.currentUser();
        mongoClient = user1.getMongoClient("mongodb-atlas");
        mongoDatabase = mongoClient.getDatabase("SurfData");
        MongoCollection<Document> mongoCollection = mongoDatabase.getCollection("UsersData");

        mongoCollection.insertOne(new Document("userid", user1.getId()).append("name",parentUser.getName()).append("id",parentUser.getId()).append("phoneNumber",parentUser.getPhoneNumber()).append("email",parentUser.getEmail()).append("password",parentUser.getPassword())).getAsync(result ->{
            if (result.isSuccess()){
                Log.d(TAG, "Data Success");
                logOutUser();
            }
            else{
                Log.d(TAG, "Data Failed ");
            }
        });


    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

}