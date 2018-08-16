package com.gogogatchi.gogogatchi.activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.gogogatchi.gogogatchi.R;

import java.util.Timer;
import java.util.TimerTask;

public class MonsterHatchActivity extends AppCompatActivity {

    // loads initial egg hatch animation after registering an account

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_monster_hatch);

        new Timer().schedule(new TimerTask() {
            @Override
            public void run() {
                startActivity(new Intent(getApplicationContext(), HomeSwipeActivity.class));
            }
        }, 4300);
    }
}
