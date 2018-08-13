package com.gogogatchi.gogogatchi.util;

import android.support.annotation.NonNull;
import android.util.Log;

import com.gogogatchi.gogogatchi.core.LocationData;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;
import com.google.gson.Gson;

import java.io.Console;
import java.util.ArrayList;
import java.util.List;

public class UserUtil {
    private static final UserUtil ourInstance = new UserUtil();
    private DatabaseReference mRef;
    private FirebaseAuth mAuth;
    private final DatabaseReference mChildRef;
    private DatabaseReference userRef;
    final String uuid;
    final FirebaseUser currentFirebaseUser;
    Gson gson;


    public static UserUtil getInstance() {

        return ourInstance;
    }

    private UserUtil() {

        mRef = FirebaseDB.mDatabase;
        mAuth = FirebaseAuth.getInstance();
        mChildRef= mRef.child("users");
        currentFirebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        uuid= currentFirebaseUser.getUid();
        userRef=mChildRef.child(uuid);
        gson=new Gson();


    }

    public void updateLikedLocations(List<LocationData> likedLocations)
    {

        userRef.child("likedLocations").removeValue();

        if(likedLocations!=null && !likedLocations.isEmpty()) {

            for (LocationData locationData : likedLocations)
            {

                userRef.child("likedLocations").push().setValue( gson.toJson(locationData));

            }
        }
    }

    public  List<LocationData> getLikedLocations()
    {
       final List<LocationData> likedLocations=new ArrayList<>();
        userRef.child("likedLocations").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot postSnapshot: dataSnapshot.getChildren()) {
                    LocationData locationData=gson.fromJson(postSnapshot.getValue().toString(),LocationData.class);
                    likedLocations.add(locationData);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Log.e("database error",databaseError.getMessage());
            }
        });
        return likedLocations;
    }

}
