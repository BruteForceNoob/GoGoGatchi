package com.gogogatchi.gogogatchi.activities;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import android.util.Base64;
import android.widget.ImageView;
import android.widget.TextView;

import com.gogogatchi.gogogatchi.FirebaseDB;
import com.gogogatchi.gogogatchi.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

public class ProfileActivity extends AppCompatActivity {


    private DatabaseReference mRef;
    private FirebaseAuth mAuth;
    private TextView textViewUsername;
    private TextView textViewEmail;
    private TextView textViewGender;
    private TextView textViewAge;
    private String encodedImage;
    private ImageView profilePicture;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        mRef = FirebaseDB.mDatabase;
        mAuth = FirebaseAuth.getInstance();
        final DatabaseReference mChildRef= mRef.child("users");
        textViewUsername = findViewById(R.id.textViewUsername);
        textViewEmail = findViewById(R.id.textViewEmail);
        textViewGender = findViewById(R.id.textViewGender);
        textViewAge = findViewById(R.id.textViewAge);
        profilePicture = findViewById(R.id.imageViewProfile);

        mChildRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                final FirebaseUser currentFirebaseUser = FirebaseAuth.getInstance().getCurrentUser();
                final String uuid = currentFirebaseUser.getUid();
                String profileName=(dataSnapshot.child(uuid).child("username").getValue().toString());
                String profileEmail= mAuth.getCurrentUser().getEmail();
                String profileGender=(dataSnapshot.child(uuid).child("gender").getValue().toString());
                String profileAgeGroup=(dataSnapshot.child(uuid).child("age").getValue().toString());
                encodedImage = (dataSnapshot.child(uuid).child("profileImage").getValue().toString());
                byte[] decodedString = Base64.decode(encodedImage, Base64.DEFAULT);
                Bitmap decodedByte = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);

                textViewUsername.setText(profileName);
                textViewGender.setText(profileGender);
                textViewAge.setText(profileAgeGroup);
                textViewEmail.setText(profileEmail);
                profilePicture.setImageBitmap(decodedByte);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        }

}
