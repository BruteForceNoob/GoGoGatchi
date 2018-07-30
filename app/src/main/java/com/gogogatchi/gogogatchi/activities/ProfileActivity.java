package com.gogogatchi.gogogatchi.activities;

import android.provider.ContactsContract;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;

import com.gogogatchi.gogogatchi.FirebaseDB;
import com.gogogatchi.gogogatchi.R;
import com.gogogatchi.gogogatchi.core.User;
import com.google.firebase.FirebaseApp;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.gson.Gson;
import com.google.gson.JsonObject;

import java.util.HashMap;
import java.util.Map;

public class ProfileActivity extends AppCompatActivity {

    private Button mSendData;
    private DatabaseReference mRef;
    //private String user;
    private CheckBox isCheckAth;
    private CheckBox isCheckCul;
    private CheckBox isCheckMonu;
    private EditText editText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        mRef = FirebaseDB.mDatabase;
        final DatabaseReference mChildRef= mRef.child("users");
        DatabaseReference mGrandChildRef = mChildRef.child("Interests");
        mSendData= findViewById(R.id.updateProfile);
        editText = (EditText)findViewById(R.id.userId);
        isCheckAth = findViewById(R.id.athletics);
        isCheckCul = findViewById(R.id.cultural);
        isCheckMonu = findViewById(R.id.monuments);



        mChildRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

               // String user = dataSnapshot.getVal
                //String userName = user.getAsJsonObject("username")
                //Gson gson = new Gson();
                //String json=gson.toJson(user);

               // HashMap value = dataSnapshot.getValue(HashMap.class);
               // Map<String, String> map = dataSnapshot.getValue(Map.class);

                Log.v("E_VALUE","DATA" + dataSnapshot.child("Ankush").child("username"));

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        mSendData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                User user= new User();


                /*user.setUsername("Ankush");
                user.setPassword("test123");*/
                Map<String,Boolean> interests=user.getInterests();
                interests.put("Athletics",isCheckAth.isChecked());
                interests.put("Cultural",isCheckCul.isChecked());
                interests.put("Monuments",isCheckMonu.isChecked());
                //mChildRef= mRef.child("User").child(user.getUsername());
                //mChildRef.setValue(user);
            }
        });

        }

}
