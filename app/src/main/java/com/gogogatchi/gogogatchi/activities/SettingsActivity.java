package com.gogogatchi.gogogatchi.activities;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.SeekBar;
import android.widget.TextView;

import com.gogogatchi.gogogatchi.R;
import com.gogogatchi.gogogatchi.util.FirebaseDB;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

public class SettingsActivity extends AppCompatActivity {

    TextView distance;
    private DatabaseReference mRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().setTitle(getString(R.string.settings));
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        setContentView(R.layout.activity_settings);

        mRef = FirebaseDB.mDatabase;
        final DatabaseReference mChildRef= mRef.child("users");
        final FirebaseUser currentFirebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        final String uuid = currentFirebaseUser.getUid();
        final SeekBar seekBar = findViewById(R.id.seekBar);
        /*mChildRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                getSeekbarValue(dataSnapshot);
            }

            private void getSeekbarValue(DataSnapshot dataSnapshot) {
                String progress = dataSnapshot.child(uuid).child("distance").getValue().toString();
                distance = findViewById(R.id.txtDistance);
                seekBar.setProgress(Integer.valueOf(progress));
                distance.setText(progress.toString());
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });*/

        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                setSeekbarValue(seekBar,i,b);
            }

            private void setSeekbarValue(SeekBar seekBar, int i, boolean b) {
                String progress = ((Integer)i).toString();
                distance.setText(progress);
                mChildRef.child(uuid).child("distance").setValue(progress);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
    }

    //Enables the back button as action bar and logic for which screen to go back to
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                Intent reHash = new Intent(this, HomeSwipeActivity.class);
                reHash.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(reHash);
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    public void updateUserProfile(View view) {
        Intent intent = new Intent (getApplicationContext(), UpdateProfileActivity.class);
        startActivity(intent);
    }
}
