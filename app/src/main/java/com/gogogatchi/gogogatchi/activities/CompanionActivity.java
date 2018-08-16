package com.gogogatchi.gogogatchi.activities;

import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.gogogatchi.gogogatchi.R;

import java.util.ArrayList;
import java.util.List;

public class CompanionActivity extends AppCompatActivity {
    //private String GIF_IMAGE_URL = "https://steamusercontent-a.akamaihd.net/ugc/318998465884632614/0A024D21926010A676CB753164E6C5F3ACEC21F9/?interpolation=lanczos-none&output-format=jpeg&output-quality=95&fit=inside%7C1024%3A576&composite-to=*,*%7C1024%3A576&background-color=black";

    private ImageView mImageView;
    private List<String> messages = new ArrayList<String>();
    private int count = 0;
    private MediaPlayer ring;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //Enables the back button as action bar
        getSupportActionBar().setTitle(getString(R.string.companionFarm));
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        //setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
        setContentView(R.layout.activity_companion);

        //Uses mediaplayer library to play sounds and starts the sound
        ring= MediaPlayer.create(CompanionActivity.this,R.raw.chinese2);ring.start();

        messages.add("Hi, please feed me!!!");
        messages.add("Thank you, I love you!");

        mImageView = findViewById(R.id.gifImageView);

        if(count==0)
        {
            ((TextView)findViewById(R.id.textView)).setVisibility(View.INVISIBLE);
        }
        ((ImageView)findViewById(R.id.companion_bottom)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((TextView)findViewById(R.id.textView)).setVisibility(View.VISIBLE);

                if(count<messages.size()) {
                    ((TextView) findViewById(R.id.textView)).setText(messages.get(count).toString());
                    count++;
                }
                else {
                    ((TextView) findViewById(R.id.textView)).setVisibility(View.INVISIBLE);
                    count = 0;
                }
            }
        });
    }

    //Enables the back button as action bar
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
    //When user hits back button, song will stop
    @Override
    public void onPause(){
        super.onPause();
        if (ring != null) {
            ring.pause();
            if (isFinishing()) {
                ring.stop();
                ring.release();
            }
        }
    }
}
