package com.gogogatchi.gogogatchi.activities;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;

import com.gogogatchi.gogogatchi.FirebaseDB;
import com.gogogatchi.gogogatchi.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

import java.io.FileNotFoundException;
import java.io.IOException;

public class UpdateProfileActivity extends AppCompatActivity {

    private static final int RESULT_LOAD_IMAGE = 1;
    private Button mSendData;
    private DatabaseReference mRef;
    private EditText editText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_profile);

        mRef = FirebaseDB.mDatabase;
        final DatabaseReference mChildRef= mRef.child("users");
        mSendData= findViewById(R.id.updateSettings);
        editText = findViewById(R.id.txtUsername);

        mChildRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                FirebaseUser currentFirebaseUser = FirebaseAuth.getInstance().getCurrentUser();
                String uuid = currentFirebaseUser.getUid();
                String profileName=(dataSnapshot.child(uuid).child("username").getValue().toString());
                editText.setText(profileName);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        Button buttonLoadImage = (Button) findViewById(R.id.btnUpdateProfileImage);
        buttonLoadImage.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                startActivityForResult(new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.INTERNAL_CONTENT_URI), RESULT_LOAD_IMAGE);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
    super.onActivityResult(requestCode, resultCode, data);

    //Detects request codes
    if(requestCode==RESULT_LOAD_IMAGE && resultCode == Activity.RESULT_OK) {
        Uri selectedImage = data.getData();
        Bitmap bitmap = null;
        try {
            bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), selectedImage);
            //int nh = (int) ( bitmap.getHeight() * (125.0 / bitmap.getWidth()) );
            Bitmap scaled = Bitmap.createScaledBitmap(bitmap, 512, 512, true);
            ImageView imageView = (ImageView) findViewById(R.id.profileImageView);
            imageView.setImageBitmap(scaled);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
}
