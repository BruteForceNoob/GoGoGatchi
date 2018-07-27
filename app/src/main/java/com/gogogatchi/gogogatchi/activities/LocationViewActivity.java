package com.gogogatchi.gogogatchi.activities;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.gogogatchi.gogogatchi.R;
import com.gogogatchi.gogogatchi.core.Profile;

public class LocationViewActivity extends AppCompatActivity {

    private static Profile mProfile;
    ImageView imageView;
    TextView textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_location_view);

        Bundle bundle = this.getIntent().getExtras();
        if (bundle != null)
            mProfile = bundle.getParcelable("mProfile");

        imageView = findViewById(R.id.imageView5);
        textView = findViewById(R.id.textView4);

        Glide.with(getApplicationContext()).load(mProfile.getImageUrl()).into(imageView);
        textView.setText(mProfile.getDestinationName());
    }
}