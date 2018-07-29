package com.gogogatchi.gogogatchi.activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;

import com.gogogatchi.gogogatchi.FirebaseDB;
import com.gogogatchi.gogogatchi.R;
import com.google.firebase.FirebaseApp;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class ProfileActivity extends AppCompatActivity {

    private Button mSendData;
    private DatabaseReference mRef;
    private String user;
    private CheckBox isCheckAth;
    private CheckBox isCheckCul;
    private CheckBox isCheckMonu;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        mRef = FirebaseDB.mDatabase;
        mSendData= findViewById(R.id.updateProfile);
        user = getString(R.string.username);
        isCheckAth = findViewById(R.id.athletics);
        isCheckCul = findViewById(R.id.cultural);
        isCheckMonu = findViewById(R.id.monuments);
        mSendData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DatabaseReference mChildRef= mRef.child("Username");
                DatabaseReference mGrandChildRef1= mChildRef.child("Athletics");
                mGrandChildRef1.setValue(isCheckAth.isChecked());
                DatabaseReference mGrandChildRef2= mChildRef.child("Cultural");
                mGrandChildRef2.setValue(isCheckCul.isChecked());
                DatabaseReference mGrandChildRef3= mChildRef.child("Monuments");
                mGrandChildRef3.setValue(isCheckMonu.isChecked());
                mChildRef.setValue("Ankush Bhandare");
            }
        });
        }

}
