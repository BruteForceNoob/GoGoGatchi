package com.gogogatchi.gogogatchi.activities;

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
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

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
        mSendData= findViewById(R.id.updateProfile);
        editText = (EditText)findViewById(R.id.userId);
        isCheckAth = findViewById(R.id.athletics);
        isCheckCul = findViewById(R.id.cultural);
        isCheckMonu = findViewById(R.id.monuments);
      /*  mSendData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                User user= new User();

                user.setUsername("Ankush");
                user.setPassword("test123");
                Map<String,Boolean> interests=user.getInterests();
                interests.put("Athletics",isCheckAth.isChecked());
                interests.put("Cultural",isCheckCul.isChecked());
                interests.put("Monuments",isCheckMonu.isChecked());
                DatabaseReference mChildRef= mRef.child("User").child(user.getUsername());
                mChildRef.setValue(user);


                //user = (EditText)findViewById(R.id.userId);
           /*     mChildRef.child("Username").setValue(user.getText().toString());
                mChildRef.child("Athletics").setValue(isCheckAth.isChecked());
                mChildRef.child("Cultural").setValue(isCheckCul.isChecked());
                mChildRef.child("Monuments").setValue(isCheckMonu.isChecked());*/
                /*DatabaseReference mGrandChildRef1= mChildRef.child("Athletics");
                mGrandChildRef1.setValue(isCheckAth.isChecked());
                DatabaseReference mGrandChildRef2= mChildRef.child("Cultural");
                mGrandChildRef2.setValue(isCheckCul.isChecked());
                DatabaseReference mGrandChildRef3= mChildRef.child("Monuments");
                mGrandChildRef3.setValue(isCheckMonu.isChecked());
                mChildRef.setValue("Ankush Bhandare");
            }
        });*/
        }

}
