package com.gogogatchi.gogogatchi.activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.gogogatchi.gogogatchi.R;

public class SettingsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
    }

    public void updateUserProfile(View view) {
        Intent intent = new Intent (getApplicationContext(), UpdateProfileActivity.class);
        startActivity(intent);
    }
}
