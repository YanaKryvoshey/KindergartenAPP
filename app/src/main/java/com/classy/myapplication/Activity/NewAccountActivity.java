package com.classy.myapplication.Activity;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.CustomTarget;
import com.bumptech.glide.request.transition.Transition;
import com.classy.myapplication.MyApp;
import com.classy.myapplication.Object.AppUser;
import com.classy.myapplication.Object.ParentSearchGardenUser;
import com.classy.myapplication.Object.ParentUser;
import com.classy.myapplication.Object.TeacherUser;
import com.classy.myapplication.R;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputLayout;

import org.bson.Document;

import java.util.concurrent.atomic.AtomicReference;

import io.realm.mongodb.Credentials;
import io.realm.mongodb.User;
import io.realm.mongodb.mongo.MongoClient;
import io.realm.mongodb.mongo.MongoCollection;
import io.realm.mongodb.mongo.MongoDatabase;


public class NewAccountActivity extends AppCompatActivity {
    private AppUser newUser;
    private static final String TAG = "Pttt";
    private Context context;
    private TextInputLayout newAccount_EDT_name;
    private TextInputLayout newAccount_EDT_email;
    private TextInputLayout newAccount_EDT_password;
    private TextInputLayout newAccount_EDT_id;
    private TextInputLayout newAccount_EDT_phoneNumber;

    private MaterialButton newAccount_EDT_submit;

    int userNum;
    String gardenName, usertype;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_account);

        Bundle bundle = getIntent().getExtras();
        if (bundle != null){
            // getting information from privies activity
            userNum = bundle.getInt("USERNUM");
            gardenName= bundle.getString("GARDENNAME");
        }else {
            Intent intent = getIntent();
            userNum = intent.getIntExtra("USERNUM",-1);
            if (userNum == 1 || userNum == 3){
                gardenName = intent.getStringExtra("GARDENNAME");
            }

        }

        findViews();
    }

    private void findViews() {
        Log.d(TAG, "findViews new account dialog: ");
        newAccount_EDT_name = findViewById(R.id.newAccount_EDT_name);
        newAccount_EDT_email = findViewById(R.id.newAccount_EDT_email);
        newAccount_EDT_password = findViewById(R.id.newAccount_EDT_password);
        newAccount_EDT_id = findViewById(R.id.newAccount_EDT_id);
        newAccount_EDT_phoneNumber = findViewById(R.id.newAccount_EDT_phoneNumber);
        newAccount_EDT_submit = findViewById(R.id.newAccount_EDT_submit);
        newAccount_EDT_submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                checkValidInfo();
            }
        });
        setViewListeners();
    }

    /**
     * A method to set the new account page listeners
     */
    private void setViewListeners() {
        Log.d(TAG, "setViewListeners: setting listeners");
        newAccount_EDT_name.getEditText().addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                newAccount_EDT_name.setErrorEnabled(false); // disable error
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
        newAccount_EDT_email.getEditText().addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                newAccount_EDT_email.setErrorEnabled(false); // disable error
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
        newAccount_EDT_password.getEditText().addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                newAccount_EDT_password.setErrorEnabled(false); // disable error
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

    }

    /**
     * A method to check the valid information
     */
    private void checkValidInfo() {
        Log.d(TAG, "checkValidInfo: Checking valid input");
        if (newAccount_EDT_name.getEditText().getText().toString().equals("")) {
            Log.d(TAG, "checkForValidInputs: first name invalid");
            newAccount_EDT_name.setError(getString(R.string.enter_name_error));
            return;
        }
        if (!android.util.Patterns.EMAIL_ADDRESS.matcher(newAccount_EDT_email.getEditText().getText().toString()).matches()) {
            Log.d(TAG, "checkForValidInputs: Email invalid");
            newAccount_EDT_email.setError(getString(R.string.enter_email_error));
            return;
        }

        if (newAccount_EDT_password.getEditText().getText().toString().equals("")
                || newAccount_EDT_password.getEditText().getText().toString().length() < 6) {
            if (newAccount_EDT_password.getEditText().getText().toString().length() < 6) {
                Log.d(TAG, "checkForValidInputs: short password");
                newAccount_EDT_password.setError(getString(R.string.six_chars_password_error));
                return;
            } else {
                Log.d(TAG, "checkForValidInputs: invalid password");
                newAccount_EDT_password.setError(getString(R.string.null_password));
                return;
            }
        }

        sendInfoToLoginActivity();
    }

    /**
     * A method to send the confirmed input info to the login activity
     */
    private void sendInfoToLoginActivity() {
        Log.d(TAG, "sendInfoToLoginActivity: sending info back");

        //Extracting information after check to a user object

        String name = newAccount_EDT_name.getEditText().getText().toString();
        String email = newAccount_EDT_email.getEditText().getText().toString().toLowerCase();
        String password = newAccount_EDT_password.getEditText().getText().toString();
        String id = newAccount_EDT_id.getEditText().getText().toString();
        String phoneNumber = newAccount_EDT_phoneNumber.getEditText().getText().toString();

        if (userNum == 1){
            ParentUser newUser = new ParentUser(name,id,email,password,phoneNumber,gardenName);
            usertype = newUser.getUsertype();
            getInfoUser(newUser);
        }else if (userNum ==2){
            ParentSearchGardenUser newUser = new ParentSearchGardenUser(name,id,email,password,phoneNumber,"NONE");
            usertype = newUser.getUsertype();
            getInfoUser(newUser);
        }else if (userNum == 3){
            TeacherUser newUser = new TeacherUser(name,id,email,password,phoneNumber,gardenName);
            usertype = newUser.getUsertype();
            getInfoUser(newUser);
        }


    }

    public void getInfoUser(AppUser newUser) {
        //Credentials emailPasswordCredentials = Credentials.emailPassword(emailUser,passUser);
        MyApp.app.getEmailPassword().registerUserAsync(newUser.getEmail(),newUser.getPassword(), result -> {
            if (result.isSuccess()) {
                Log.d(TAG, "Sign Up Success ");
                loginUser(newUser);
            } else {
                Log.d(TAG, "Sign Up Faild "+ result.getError().toString());
            }
        });

    }

    private void loginUser(AppUser newUser) {
        Credentials emailPasswordCredentials = Credentials.emailPassword(newUser.getEmail(),newUser.getPassword());
        AtomicReference<User> user = new AtomicReference<User>();

        MyApp.app.loginAsync(emailPasswordCredentials, it -> {
            if (it.isSuccess()) {
                Log.d(TAG, "Successfully authenticated using an email and password.");
                user.set(MyApp.app.currentUser());
                saveDetailsUser(newUser);
            } else {
                Log.d(TAG, it.getError().toString());
            }
        });
    }

    //save new user data on mongoBD
    private void saveDetailsUser(AppUser newUser) {


        User user1= MyApp.app.currentUser();
        MongoClient mongoClient = user1.getMongoClient("mongodb-atlas");
        MongoDatabase mongoDatabase = mongoClient.getDatabase("AppUserData");
        MongoCollection<Document> mongoCollection = null;

        if (userNum == 1){
            mongoCollection = mongoDatabase.getCollection("Parents");
        }else if (userNum ==2){
            mongoCollection = mongoDatabase.getCollection("SearchKindergarten");
        }else if (userNum == 3){
            mongoCollection = mongoDatabase.getCollection("Teachers");
        }
        
        mongoCollection.insertOne(new Document("userid", user1.getId()).append("name",newUser.getName()).append("phoneNumber",newUser.getPhoneNumber()).append("email",newUser.getEmail()).append("password",newUser.getPassword()).append("gardenName",newUser.getGardenName()).append("usertype",usertype)).getAsync(result ->{
            if (result.isSuccess()){
                Log.d(TAG, "Data Success");
                logOutUser();
            }
            else{
                Log.d(TAG, "Data Failed "+ result.getError().toString());
            }
        });


    }

    private void logOutUser() {
        MyApp.app.currentUser().logOutAsync(result -> {
            if (result.isSuccess()){
                Log.d(TAG, "logOutUser SUCCESSES");
                Intent intent = new Intent(NewAccountActivity.this,LoginActivity.class);
                startActivity(intent);
            }
            else{
                Log.d(TAG, "logOutUser: ");
            }
        });
    }

    /**
     * A method to insert image to background with glide
     */
    private void glideToBackground(final View target, int pictureID) {
        Glide.with(target).load(pictureID).into(new CustomTarget<Drawable>() {
            @Override
            public void onResourceReady(@NonNull Drawable resource, @Nullable Transition<? super Drawable> transition) {
                target.setBackground(resource);
            }

            @Override
            public void onLoadCleared(@Nullable Drawable placeholder) {
            }
        });
    }




}