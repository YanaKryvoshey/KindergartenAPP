package com.classy.myapplication.Fragment;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import com.classy.myapplication.Dialog.NewAccountDialog;
import com.classy.myapplication.MyApp;
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

import static io.realm.Realm.getApplicationContext;


public class LogInFragment extends Fragment {

    private TextView login_LBL_createCount;
    private MaterialButton login_BTN_login;
    private EditText login_EDT_password;
    private EditText login_EDT_mail;

    String TAG="Pttt";

    String email,password;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        Log.d("pttt", "onCreateView - LogInFragment");

        View view = inflater.inflate(R.layout.fragment_log_in, container, false);
        findViews(view);
        fetchLsatLocation();
        //press on login -> open LogInFragment
        login_BTN_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                login_BTN_login.setClickable(false);
                email = login_EDT_mail.getText().toString();
                password = login_EDT_password.getText().toString();
                login(view);
            }
        });


        login_LBL_createCount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                NavController navController = Navigation.findNavController(view);
                navController.navigate(R.id.action_logInFragment_to_searchOrConnectToGardenFragment);

            }
        });

        return view;
    }



    public LogInFragment() {
        // Required empty public constructor
    }




    public void login(View view){
        Credentials emailPasswordCredentials = Credentials.emailPassword(email,password);
        AtomicReference<User> user = new AtomicReference<User>();
        MyApp.app.loginAsync(emailPasswordCredentials, it -> {
            if (it.isSuccess()) {
                Log.d("AUTH", "Successfully authenticated using an email and password.");
                user.set(MyApp.app.currentUser());
                NavController navController = Navigation.findNavController(view);
                navController.navigate(R.id.action_logInFragment_to_mainFragment);

            } else {
                Log.d("Pttt", it.getError().toString());
            }
        });


    }

    private void findViews(View view) {



        login_BTN_login = view.findViewById(R.id.login_BTN_login);
        login_LBL_createCount = view.findViewById(R.id.login_LBL_createCount);
        login_EDT_password = view.findViewById(R.id.login_EDT_password);
        login_EDT_mail = view.findViewById(R.id.login_EDT_mail);
        login_EDT_mail.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                login_EDT_mail.setError(null); // disable error
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        login_EDT_password.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                login_EDT_password.setError(null); // disable error
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });




    }


    // ask from permission to get the place
    private void fetchLsatLocation() {
        if (ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(getContext(),Manifest.permission.ACCESS_COARSE_LOCATION)== PackageManager.PERMISSION_GRANTED){

        }else{
            ActivityCompat.requestPermissions(getActivity(),
                    new String[]{Manifest.permission.ACCESS_FINE_LOCATION,Manifest.permission.ACCESS_COARSE_LOCATION},100);
        }

    }
    //print text if didnt give permission to take the place
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode == 100 && grantResults.length > 0 && (grantResults[0]+grantResults[1] == PackageManager.PERMISSION_GRANTED)){

        }else {
            Toast.makeText(getApplicationContext(),"Premission denied.",Toast.LENGTH_SHORT).show();
        }
    }
}