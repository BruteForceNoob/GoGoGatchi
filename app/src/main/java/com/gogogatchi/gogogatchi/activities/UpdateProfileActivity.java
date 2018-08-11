package com.gogogatchi.gogogatchi.activities;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Base64;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

import com.gogogatchi.gogogatchi.FirebaseDB;
import com.gogogatchi.gogogatchi.R;
import com.google.android.gms.auth.api.signin.internal.Storage;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class UpdateProfileActivity extends AppCompatActivity {

    private static final int RESULT_LOAD_IMAGE = 1;
    private Button mSendData;
    private DatabaseReference mRef;
    private EditText editText;
    private String encodedImage;
    private ImageView profilePicture;
    private CheckBox AmusementPark;
    private CheckBox ArtGallery;
    private CheckBox Bar;
    private CheckBox Cafe;
    private CheckBox Campground;
    private CheckBox Lodging;
    private CheckBox MovieTheatre;
    private CheckBox Museum;
    private CheckBox NightClub;
    private CheckBox Park;
    private CheckBox Restaurant;
    private CheckBox ShoppingMall;
    private CheckBox Stadium;
    private CheckBox TravelAgency;
    private CheckBox Zoo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_profile);

        mRef = FirebaseDB.mDatabase;
        final DatabaseReference mChildRef= mRef.child("users");
        mSendData= findViewById(R.id.updateSettings);
        editText = findViewById(R.id.txtUsername);
        profilePicture = findViewById(R.id.profileImageView);
        AmusementPark = findViewById(R.id.chk_Amusement_park);
        ArtGallery = findViewById(R.id.chk_Art_Gallery);
        Bar = findViewById(R.id.chk_Bar);
        Cafe = findViewById(R.id.chk_Cafe);
        Campground = findViewById(R.id.chk_Campground);
        Lodging = findViewById(R.id.chk_Lodging);
        MovieTheatre = findViewById(R.id.chk_Movie_Theatre);
        Museum = findViewById(R.id.chk_Museum);
        NightClub = findViewById(R.id.chk_Night_Club);
        Park = findViewById(R.id.chk_Park);
        Restaurant = findViewById(R.id.chk_Restaurant);
        ShoppingMall = findViewById(R.id.chk_Shopping_Mall);
        Stadium = findViewById(R.id.chk_Stadium);
        TravelAgency = findViewById(R.id.chk_Travel_Agency);
        Zoo = findViewById(R.id.chk_Zoo);


        mChildRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull final DataSnapshot dataSnapshot) {

                final FirebaseUser currentFirebaseUser = FirebaseAuth.getInstance().getCurrentUser();
                final String uuid = currentFirebaseUser.getUid();
                String profileName=(dataSnapshot.child(uuid).child("username").getValue().toString());

                encodedImage = (dataSnapshot.child(uuid).child("profileImage").getValue().toString());

                byte[] decodedString = Base64.decode(encodedImage, Base64.DEFAULT);
                Bitmap decodedByte = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);

                editText.setText(profileName);
                profilePicture.setImageBitmap(decodedByte);
                if ((dataSnapshot.child(uuid).child("interests").child("Amusement Park").getValue().toString()).equals("true"))
                    AmusementPark.setChecked(true);
                if ((dataSnapshot.child(uuid).child("interests").child("Art Gallery").getValue().toString()).equals("true"))
                    ArtGallery.setChecked(true);
                if ((dataSnapshot.child(uuid).child("interests").child("Bar").getValue().toString()).equals("true"))
                    Bar.setChecked(true);
                if ((dataSnapshot.child(uuid).child("interests").child("Cafe").getValue().toString()).equals("true"))
                    Cafe.setChecked(true);
                if ((dataSnapshot.child(uuid).child("interests").child("Campground").getValue().toString()).equals("true"))
                    Campground.setChecked(true);
                if ((dataSnapshot.child(uuid).child("interests").child("Lodging").getValue().toString()).equals("true"))
                    Lodging.setChecked(true);
                if ((dataSnapshot.child(uuid).child("interests").child("Movie Theatre").getValue().toString()).equals("true"))
                    MovieTheatre.setChecked(true);
                if ((dataSnapshot.child(uuid).child("interests").child("Museum").getValue().toString()).equals("true"))
                    Museum.setChecked(true);
                if ((dataSnapshot.child(uuid).child("interests").child("Night Club").getValue().toString()).equals("true"))
                    NightClub.setChecked(true);
                if ((dataSnapshot.child(uuid).child("interests").child("Park").getValue().toString()).equals("true"))
                    Park.setChecked(true);
                if ((dataSnapshot.child(uuid).child("interests").child("Restaurant").getValue().toString()).equals("true"))
                    Restaurant.setChecked(true);
                if ((dataSnapshot.child(uuid).child("interests").child("Shopping Mall").getValue().toString()).equals("true"))
                    ShoppingMall.setChecked(true);
                if ((dataSnapshot.child(uuid).child("interests").child("Stadium").getValue().toString()).equals("true"))
                    Stadium.setChecked(true);
                if ((dataSnapshot.child(uuid).child("interests").child("Travel Agency").getValue().toString()).equals("true"))
                    TravelAgency.setChecked(true);
                if ((dataSnapshot.child(uuid).child("interests").child("Zoo").getValue().toString()).equals("true"))
                    Zoo.setChecked(true);

                mSendData.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        try {
                            String updatedUsername = editText.getText().toString();
                            mChildRef.child(uuid).child("username").setValue(updatedUsername);
                            mChildRef.child(uuid).child("profileImage").setValue(encodedImage);
                            if(AmusementPark.isChecked())
                                mChildRef.child(uuid).child("interests").child("Amusement Park").setValue("true");
                            else
                                mChildRef.child(uuid).child("interests").child("Amusement Park").setValue("false");
                            if(ArtGallery.isChecked())
                                mChildRef.child(uuid).child("interests").child("Art Gallery").setValue("true");
                            else
                                mChildRef.child(uuid).child("interests").child("Art Gallery").setValue("false");
                            if(Bar.isChecked())
                                mChildRef.child(uuid).child("interests").child("Bar").setValue("true");
                            else
                                mChildRef.child(uuid).child("interests").child("Bar").setValue("false");
                            if(Cafe.isChecked())
                                mChildRef.child(uuid).child("interests").child("Cafe").setValue("true");
                            else
                                mChildRef.child(uuid).child("interests").child("Cafe").setValue("false");
                            if(Campground.isChecked())
                                mChildRef.child(uuid).child("interests").child("Campground").setValue("true");
                            else
                                mChildRef.child(uuid).child("interests").child("Campground").setValue("false");
                            if(Lodging.isChecked())
                                mChildRef.child(uuid).child("interests").child("Lodging").setValue("true");
                            else
                                mChildRef.child(uuid).child("interests").child("Lodging").setValue("false");
                            if(MovieTheatre.isChecked())
                                mChildRef.child(uuid).child("interests").child("Movie Theatre").setValue("true");
                            else
                                mChildRef.child(uuid).child("interests").child("Movie Theatre").setValue("false");
                            if(Museum.isChecked())
                                mChildRef.child(uuid).child("interests").child("Museum").setValue("true");
                            else
                                mChildRef.child(uuid).child("interests").child("Museum").setValue("false");
                            if(NightClub.isChecked())
                                mChildRef.child(uuid).child("interests").child("Night Club").setValue("true");
                            else
                                mChildRef.child(uuid).child("interests").child("Night Club").setValue("false");
                            if(Park.isChecked())
                                mChildRef.child(uuid).child("interests").child("Park").setValue("true");
                            else
                                mChildRef.child(uuid).child("interests").child("Park").setValue("false");
                            if(Restaurant.isChecked())
                                mChildRef.child(uuid).child("interests").child("Restaurant").setValue("true");
                            else
                                mChildRef.child(uuid).child("interests").child("Restaurant").setValue("false");
                            if(ShoppingMall.isChecked())
                                mChildRef.child(uuid).child("interests").child("Shopping Mall").setValue("true");
                            else
                                mChildRef.child(uuid).child("interests").child("Shopping Mall").setValue("false");
                            if(Stadium.isChecked())
                                mChildRef.child(uuid).child("interests").child("Stadium").setValue("true");
                            else
                                mChildRef.child(uuid).child("interests").child("Stadium").setValue("false");
                            if(TravelAgency.isChecked())
                                mChildRef.child(uuid).child("interests").child("Travel Agency").setValue("true");
                            else
                                mChildRef.child(uuid).child("interests").child("Travel Agency").setValue("false");
                            if(Zoo.isChecked())
                                mChildRef.child(uuid).child("interests").child("Zoo").setValue("true");
                            else
                                mChildRef.child(uuid).child("interests").child("Zoo").setValue("false");

                            Context context = getApplicationContext();
                            CharSequence text = "Profile Updated Successfully";
                            int duration = Toast.LENGTH_SHORT;

                            Toast toast = Toast.makeText(context, text, duration);
                            toast.show();

                            Intent i = new Intent(getApplicationContext(), HomeSwipeActivity.class);
                            startActivity(i);
                        } catch(Exception ex)
                        {
                            Context context = getApplicationContext();
                            CharSequence text = "Profile Update Failed";
                            int duration = Toast.LENGTH_SHORT;

                            Toast toast = Toast.makeText(context, text, duration);
                            toast.show();
                        }
                    }
                });
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Context context = getApplicationContext();
                CharSequence text = "Profile Update Failed";
                int duration = Toast.LENGTH_SHORT;
                Toast toast = Toast.makeText(context, text, duration);
                toast.show();
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
            ByteArrayOutputStream stream = new ByteArrayOutputStream();
            scaled.compress(Bitmap.CompressFormat.JPEG, 100, stream);
            byte[] byteFormat = stream.toByteArray();
            encodedImage = Base64.encodeToString(byteFormat, Base64.NO_WRAP);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        }
    }
}
