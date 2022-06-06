package com.classy.myapplication.Activity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.classy.myapplication.MessageAdapter;
import com.classy.myapplication.Object.Message;
import com.classy.myapplication.R;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

public class ChatActivity extends AppCompatActivity {

    private TextInputLayout chat_LBL_message;
    private MaterialButton chat_BTM_send;
    private DatabaseReference mDatabase;
    ArrayList<Message> send = new ArrayList<>();
    ArrayList<Message> myListData = new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);
        String gardenName = getIntent().getStringExtra("NAME");
        mDatabase = FirebaseDatabase.getInstance().getReference();
        initView();
        getChatFromFireBase(gardenName);
        chat_BTM_send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DateFormat df = new SimpleDateFormat("yyyy.MM.dd 'at' HH:mm:ss ");
                String date = df.format(Calendar.getInstance().getTime());
                String message = chat_LBL_message.getEditText().getText().toString();
                Message newmessage = new Message(date, message);
                send = myListData;
                send.add(newmessage);
                SaveChatToFireBase(gardenName);
            }
        });
        setRecyclerView();
    }

    private void getChatFromFireBase(String gardenName) {
        myListData.clear();
        DatabaseReference usersRef = FirebaseDatabase.getInstance("https://kindergartenapp-ea823-default-rtdb.firebaseio.com/").getReference("Message");
        usersRef.child(gardenName).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                try {
                    for (DataSnapshot snapshot:dataSnapshot.getChildren()){
                        Message mess = snapshot.getValue(Message.class);
                        myListData.add(mess);
                        Log.d("pttt", "getChatFromFireBase  - get chat"  );

                    }
                    setRecyclerView();

                } catch (Exception ex) {
                    Log.d("pttt", "getChatFromFireBase Failed to read from firebase" + ex.toString());
                }
            }

            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
                Log.d("pttt", "Failed to read latitude and longituds.", error.toException());
            }
        });
    }

    private void SaveChatToFireBase(String gardenName) {


        FirebaseDatabase database = FirebaseDatabase.getInstance("https://kindergartenapp-ea823-default-rtdb.firebaseio.com/");
        DatabaseReference myRef = database.getReference("Message");
        myRef.child(gardenName).setValue(send).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                Log.d("Pttt", "updateFireBase onSuccess push data to fireBase");
                getChatFromFireBase(gardenName);
            }
        }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.d("Pttt", "updateFireBase onFailure push data to fireBase" + e.toString());
                    }
                });


    }

    private void initView() {
        chat_LBL_message = findViewById(R.id.chat_LBL_message);
        chat_BTM_send = findViewById(R.id.chat_BTM_send);
    }


    private void setRecyclerView() {

        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.chat_RCV_message);
        MessageAdapter adapter = new MessageAdapter(myListData);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);
    }
}