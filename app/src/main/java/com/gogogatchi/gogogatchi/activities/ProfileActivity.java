package com.gogogatchi.gogogatchi.activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.gogogatchi.gogogatchi.R;

public class ProfileActivity extends AppCompatActivity {

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
                mRef.child("Username").setValue(user);
                DatabaseReference mChildRef= mRef.child(user);
                mChildRef.child("Athletics").setValue(isCheckAth.isChecked());
                mChildRef.child("Cultural").setValue(isCheckCul.isChecked());
                mChildRef.child("Monuments").setValue(isCheckMonu.isChecked());
                /*DatabaseReference mGrandChildRef1= mChildRef.child("Athletics");
                mGrandChildRef1.setValue(isCheckAth.isChecked());
                DatabaseReference mGrandChildRef2= mChildRef.child("Cultural");
                mGrandChildRef2.setValue(isCheckCul.isChecked());
                DatabaseReference mGrandChildRef3= mChildRef.child("Monuments");
                mGrandChildRef3.setValue(isCheckMonu.isChecked());
                mChildRef.setValue("Ankush Bhandare");*/
            }
        });
        }

}
