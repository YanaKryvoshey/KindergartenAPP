package com.classy.myapplication;

import android.util.Log;

import com.classy.myapplication.Object.KindergartenUpdate;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class UpdateKindergartenToFireBase {

    private DatabaseReference databaseReference;

    public UpdateKindergartenToFireBase() {
        FirebaseDatabase db = FirebaseDatabase.getInstance();
        databaseReference = db.getReference(KindergartenUpdate.class.getSimpleName());

    }

    public Task<Void> add(KindergartenUpdate info){
        if (info != null){
            Log.d("Pttt","UpdateKindergartenToFireBase = " + info.getMassege() + info.getAgeRange());

            return databaseReference.push().setValue(info);
        }
        return null;
    }
}
