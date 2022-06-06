package com.classy.myapplication.Fragment;

import android.location.Location;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import com.classy.myapplication.MyApp;
import com.classy.myapplication.R;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.material.button.MaterialButton;

import org.bson.Document;

import java.util.concurrent.atomic.AtomicReference;

import io.realm.mongodb.Credentials;
import io.realm.mongodb.User;
import io.realm.mongodb.mongo.MongoClient;
import io.realm.mongodb.mongo.MongoCollection;
import io.realm.mongodb.mongo.MongoDatabase;


public class LogInFragment extends Fragment {

    private TextView login_LBL_createCount;
    private MaterialButton login_BTN_login;
    private EditText login_EDT_password;
    private EditText login_EDT_mail;
    Location lastLocation;
    private FusedLocationProviderClient fusedLocationClient;
    String TAG = "Pttt";

    String email, password, userType, gardenName;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        Log.d(TAG, "onCreateView - LogInFragment");

        View view = inflater.inflate(R.layout.fragment_log_in, container, false);
        findViews(view);
        //press on login -> open LogInFragment
        login_BTN_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
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


    public void login(View view) {
        Credentials emailPasswordCredentials = Credentials.emailPassword(email, password);
        AtomicReference<User> user = new AtomicReference<>();

        MyApp.app.loginAsync(emailPasswordCredentials, it -> {
            if (it.isSuccess()) {
                Log.d(TAG, "Successfully authenticated using an email and password.");
                user.set(MyApp.app.currentUser());
                String email = MyApp.app.currentUser().getProfile().getEmail();
                Log.d(TAG, "email = " + email);
                findTheUserType(email, "Parents", view);
                if (userType == null) {
                    findTheUserType(email, "SearchKindergarten", view);
                    if (userType == null) {
                        findTheUserType(email, "Teachers", view);
                    }
                }
            } else {
                Log.d(TAG, it.getError().toString());
            }
        });
    }

    private void findTheUserType(String email, String collectionName, View view) {
        User user1 = MyApp.app.currentUser();
        Document queryFilter = new Document().append("email", email);
        MongoClient mongoClient = user1.getMongoClient("mongodb-atlas");
        MongoDatabase mongoDatabase = mongoClient.getDatabase("AppUserData");
        MongoCollection<Document> mongoCollection = mongoDatabase.getCollection(collectionName);
        mongoCollection.findOne(queryFilter).getAsync(result -> {
            if (result.isSuccess()) {
                Log.d("Pttt", result.toString());
                Document resultData = result.get();
                if (resultData != null) {
                    userType = resultData.getString("usertype");
                    gardenName = resultData.getString("gardenName");
                    Log.d(TAG, "user found in mongoDB result.toString() = " + result.toString() + " type = " + userType);
                    nextFragment(view);
                }
            } else {
                Log.d(TAG, result.getError().toString());
            }
        });
    }

    private void nextFragment(View view) {
        Log.d(TAG, "userType = " + userType);
        Bundle bundle = new Bundle();
        bundle.putString("GARDEN_NAME", gardenName);

        if (userType.equals("TeacherUser")) {
            NavController navController = Navigation.findNavController(view);
            navController.navigate(R.id.action_logInFragment_to_teacherMainFragment, bundle);
        } else if (userType.equals("ParentSearchGardenUser")) {
            NavController navController1 = Navigation.findNavController(view);
            navController1.navigate(R.id.action_logInFragment_to_parentSearchMainFragment, bundle);
        } else if (userType.equals("ParentUser")) {
            NavController navController2 = Navigation.findNavController(view);
            navController2.navigate(R.id.action_logInFragment_to_parentMainFragment, bundle);
        }
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



}