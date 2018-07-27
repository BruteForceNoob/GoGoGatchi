package com.gogogatchi.gogogatchi.activities;

import android.drm.ProcessedData;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.StrictMode;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.content.Context;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.gogogatchi.gogogatchi.R;
import com.gogogatchi.gogogatchi.core.Profile;

import org.w3c.dom.Text;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class LocationViewActivity extends AppCompatActivity {

    private static Profile mProfile;
    ImageView imageView;
    TextView textView;

    public static Drawable LoadFromURL(String address) {
        try {
            InputStream iStrm = (InputStream) new URL(address).getContent();
            return Drawable.createFromStream(iStrm, "name");
        } catch (Exception e) {
            return null;
        }
    }

    Bitmap drawable_from_url(String url) throws java.net.MalformedURLException, java.io.IOException {

        HttpURLConnection connection = (HttpURLConnection)new URL(url) .openConnection();
        connection.setRequestProperty("User-agent","Mozilla/4.0");

        connection.connect();
        InputStream input = connection.getInputStream();

        return BitmapFactory.decodeStream(input);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_location_view);

        Bundle bundle = this.getIntent().getExtras();
        if (bundle != null)
            mProfile = bundle.getParcelable("mProfile");

        imageView = findViewById(R.id.imageView5);
        textView = findViewById(R.id.textView4);

        Context context = getApplicationContext();
        Glide.with(context).load(mProfile.getImageUrl()).into(imageView);

        textView.setText(mProfile.getDestinationName());
    }
}