package com.classy.myapplication.Fragment;


import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.classy.myapplication.Object.Kindergarten;
import com.classy.myapplication.Object.KindergartenUpdate;
import com.classy.myapplication.R;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.io.Serializable;


public class UpgradeTeacherFragment extends Fragment {

    public TextInputLayout Upgrade_EDT_childrenNum;
    public TextInputLayout Upgrade_EDT_AgeRange;
    public TextInputLayout Upgrade_EDT_TeachersMum;
    public TextInputLayout Upgrade_EDT_Message;
    public MaterialButton Upgrade_EDT_submit;
    Serializable kindergarten = new Kindergarten();
    KindergartenUpdate kindergartenUpdate = new KindergartenUpdate();

    public UpgradeTeacherFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_upgrade_teacher, container, false);
        Bundle bundle = getArguments();
        kindergarten = bundle.getSerializable("KINDERGARTEN");
        findView(view);
       //UpdateKindergartenToFireBase updateKindergartenToFireBase = new UpdateKindergartenToFireBase();
        Upgrade_EDT_submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getTextUpdate();
//              updateKindergartenToFireBase.add(kindergartenUpdate).addOnSuccessListener(suc->{
//                    Log.d("Pttt","UpgradeTeacherFragment save garden Update in FireBase");
//                }).addOnFailureListener(er->{
//                    Log.d("Pttt","UpgradeTeacherFragment error on save garden Update in FireBase");
//
//                });
            }
        });
        return view;
    }

    private void getTextUpdate() {
        int children =Integer.parseInt(Upgrade_EDT_childrenNum.getEditText().getText().toString());
        int range =Integer.parseInt(Upgrade_EDT_AgeRange.getEditText().getText().toString());
        int teachers =Integer.parseInt(Upgrade_EDT_TeachersMum.getEditText().getText().toString());
        String message = Upgrade_EDT_Message.getEditText().getText().toString();
        kindergartenUpdate = new KindergartenUpdate((Kindergarten) kindergarten,children,range,teachers,message);
        updateFireBase();
    }

    private void updateFireBase() {
        Kindergarten test = (Kindergarten) kindergarten;
        Log.d("Pttt","UpgradeTeacherFragment - updateFireBase");
     //   Child child = new Child(firebaseUser.getUid(), Child_EDT_name.getText().toString(), Child_EDT_childID.getText().toString(), firebaseUser.getPhoneNumber(), Child_EDT_Phone2.getText().toString(), Child_EDT_BirthDay.getText().toString(), gardenName);
        FirebaseDatabase database = FirebaseDatabase.getInstance("https://kindergartenapp-ea823-default-rtdb.firebaseio.com/");
        DatabaseReference myRef = database.getReference("kindergartenUpdate");
       // myRef.push().setValue(kindergartenUpdate);
        Log.d("Pttt","UpgradeTeacherFragment - updateFireBase push data to fireBase");
        myRef.child(test.getName()).setValue(kindergartenUpdate).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                Log.d("Pttt","updateFireBase onSuccess push data to fireBase");
            }
        })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.d("Pttt","updateFireBase onFailure push data to fireBase" + e.toString());
                    }
                });
    }

    private void findView(View view) {
        Upgrade_EDT_childrenNum = view.findViewById(R.id.Upgrade_EDT_childrenNum);
        Upgrade_EDT_AgeRange = view.findViewById(R.id.Upgrade_EDT_AgeRange);
        Upgrade_EDT_TeachersMum = view.findViewById(R.id.Upgrade_EDT_TeachersMum);
        Upgrade_EDT_Message = view.findViewById(R.id.Upgrade_EDT_Message);
        Upgrade_EDT_submit = view.findViewById(R.id.Upgrade_EDT_submit);
    }
}


/*public class UpdateKindergartenToFireBase {
    private DatabaseReference databaseReference;

    public UpdateKindergartenToFireBase() {
        FirebaseDatabase db = FirebaseDatabase.getInstance();
        databaseReference = db.getReference(UpdateKindergartenToFireBase.class.getSimpleName());

    }

    public Task<Void> add(KindergartenUpdate info){
       if (info != null){
           return databaseReference.push().setValue(info);
       }
       return null;
    }
}

*/