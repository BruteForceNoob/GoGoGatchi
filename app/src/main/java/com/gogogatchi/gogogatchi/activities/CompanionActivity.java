package com.gogogatchi.gogogatchi.activities;

import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.gogogatchi.gogogatchi.R;
import com.koushikdutta.ion.Ion;

import java.util.ArrayList;
import java.util.List;

public class CompanionActivity extends AppCompatActivity {
    private String GIF_IMAGE_URL = "https://steamusercontent-a.akamaihd.net/ugc/318998465884632614/0A024D21926010A676CB753164E6C5F3ACEC21F9/?interpolation=lanczos-none&output-format=jpeg&output-quality=95&fit=inside%7C1024%3A576&composite-to=*,*%7C1024%3A576&background-color=black";
    private ImageView mImageView;
    List<String> messages = new ArrayList<String>();
    int count=0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
        setContentView(R.layout.activity_companion);

        messages.add("Hi, please feed me!!!");
        messages.add("Thank you, I love you!");

        mImageView = (ImageView) findViewById(R.id.imageView);

        Ion.with(mImageView)
                .placeholder(R.drawable.ic_launcher_background)
                .error(R.drawable.ic_launcher_background)
                .load(GIF_IMAGE_URL);

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
}