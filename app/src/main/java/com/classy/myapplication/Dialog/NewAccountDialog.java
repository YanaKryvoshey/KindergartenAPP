package com.classy.myapplication.Dialog;



import android.app.Dialog;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;



import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.CustomTarget;
import com.bumptech.glide.request.transition.Transition;
import com.classy.myapplication.Interface.NewAccountDialogListener;
import com.classy.myapplication.Object.ParentUser;
import com.classy.myapplication.R;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputLayout;

import io.realm.mongodb.User;


public class NewAccountDialog extends Dialog {
    private static final String TAG = "Pttt";
    private Context context;
    private ConstraintLayout newAccount_LAY_mainLayout;
    private TextInputLayout newAccount_EDT_name;
    private TextInputLayout newAccount_EDT_email;
    private TextInputLayout newAccount_EDT_password;
    private TextInputLayout newAccount_EDT_id;
    private TextInputLayout newAccount_EDT_phoneNumber;

    private MaterialButton newAccount_EDT_submit;

    User user;
    public NewAccountDialog(@NonNull Context context) {
        super(context);
        this.context = context;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.d(TAG, "onCreate: New account dialog");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_new_account);



        findViews();
    }


    private void findViews() {
        Log.d(TAG, "findViews new account dialog: ");
        newAccount_LAY_mainLayout = findViewById(R.id.newAccount_LAY_mainLayout);
        glideToBackground(newAccount_LAY_mainLayout, R.color.clear);
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
            newAccount_EDT_name.setError(getContext().getString(R.string.enter_name_error));
            return;
        }
        if (!android.util.Patterns.EMAIL_ADDRESS.matcher(newAccount_EDT_email.getEditText().getText().toString()).matches()) {
            Log.d(TAG, "checkForValidInputs: Email invalid");
            newAccount_EDT_email.setError(getContext().getString(R.string.enter_email_error));
            return;
        }

        if (newAccount_EDT_password.getEditText().getText().toString().equals("")
                || newAccount_EDT_password.getEditText().getText().toString().length() < 6) {
            if (newAccount_EDT_password.getEditText().getText().toString().length() < 6) {
                Log.d(TAG, "checkForValidInputs: short password");
                newAccount_EDT_password.setError(getContext().getString(R.string.six_chars_password_error));
                return;
            } else {
                Log.d(TAG, "checkForValidInputs: invalid password");
                newAccount_EDT_password.setError(getContext().getString(R.string.null_password));
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

        ParentUser parentUser = new ParentUser(name,id,email,password,phoneNumber);


        //Creating a new user with the extracted information
        callBackNewUser(parentUser);
    }

    /**
     * A method to callback the new user
     */
    private void callBackNewUser(ParentUser parentUser) {
        NewAccountDialogListener newUserDetailsCallback;
        try {
            newUserDetailsCallback = (NewAccountDialogListener) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString() + "Must implement dialog listener");
        }


        // Send the trip Dates and trip name to main layout
        newUserDetailsCallback.getInfoUser(parentUser);
        Log.d(TAG, "callBackNewUser: 444");
        dismiss();
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