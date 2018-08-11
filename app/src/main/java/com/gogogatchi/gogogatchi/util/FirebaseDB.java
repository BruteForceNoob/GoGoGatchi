package com.gogogatchi.gogogatchi.util;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

//code created for re-usability


public class FirebaseDB {
    public final static FirebaseAuth mAuth = FirebaseAuth.getInstance();
    public final static DatabaseReference mDatabase =
            FirebaseDatabase.getInstance().getReference();
    public static FirebaseAuth getAuthConnection() {
        return FirebaseAuth.getInstance();
    }
    public static DatabaseReference getConnection() {
        return FirebaseDatabase.getInstance().getReference();
    }
}
