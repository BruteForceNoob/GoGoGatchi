package com.gogogatchi.gogogatchi.activities;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;

import com.gogogatchi.gogogatchi.BuildConfig;
import com.gogogatchi.gogogatchi.R;
import com.gogogatchi.gogogatchi.core.GoogleQuery;
import com.gogogatchi.gogogatchi.core.LocationCard;
import com.gogogatchi.gogogatchi.core.LocationData;
import com.gogogatchi.gogogatchi.core.Profile;
import com.gogogatchi.gogogatchi.util.Utils;
import com.google.gson.Gson;
import com.mindorks.placeholderview.SwipeDecor;
import com.mindorks.placeholderview.SwipePlaceHolderView;

import org.json.JSONException;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;

import javax.net.ssl.HttpsURLConnection;

public class HomeSwipeActivity extends AppCompatActivity {
    private SwipePlaceHolderView mSwipeView;
    private DrawerLayout drawerLayout;
    private ActionBarDrawerToggle actionBarDrawerToggle;
    private Context mContext;
    private Toolbar appBar;
    private List<LocationData> places;

    private String myResponse = null;

    public String getResponse() {
        return myResponse;
    }

    public Context getmContext() {
        return mContext;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_swipe);

        /*** HTTP QUERY PLACES API***/
        Network task = new Network();
        task.execute();

        /*** Begin Menu Code ***/
        appBar = (Toolbar) findViewById(R.id.app_bar);
        setSupportActionBar(appBar);
        // getSupportActionBar().setDisplayShowTitleEnabled(false);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setHomeAsUpIndicator(R.drawable.ic_baseline_menu_24px);
        // actionBar.setIcon(R.drawable.ic_newspaper);
        /*** End Menu Code ***/

        mSwipeView = findViewById(R.id.swipeView);
        mContext = getApplicationContext();

        mSwipeView.getBuilder()
                .setDisplayViewCount(3)
                .setSwipeDecor(new SwipeDecor()
                        .setPaddingTop(20)
                        .setRelativeScale(0.01f)
                        .setSwipeInMsgLayoutId(R.layout.location_swipe_right)
                        .setSwipeOutMsgLayoutId(R.layout.location_swipe_left));

        findViewById(R.id.rejectBtn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mSwipeView.doSwipe(false);
            }
        });
        findViewById(R.id.acceptBtn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mSwipeView.doSwipe(true);
            }
        });

        findViewById(R.id.imageButtonA).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Network taskB = new Network();
                taskB.execute();
            }
        });

        /*** BEGIN MENU CODE ***/
        NavigationView navigationView = findViewById(R.id.navMenu);
        navigationView.setNavigationItemSelectedListener(
                new NavigationView.OnNavigationItemSelectedListener() {
                    @Override
                    public boolean onNavigationItemSelected(MenuItem menuItem) {
                        // set item as selected to persist highlight
                       // menuItem.setChecked(true);
                        drawerLayout.closeDrawers();
                        switch(menuItem.getItemId()) {
                            case R.id.nav_profile: {
                                //menuItem.setChecked(false);
                                Intent intent= new Intent(getApplicationContext(),ProfileActivity.class);
                                startActivity(intent);

                                break;
                            }
                            case R.id.nav_settings: {
                                Intent intent= new Intent(getApplicationContext(),SettingsActivity.class);
                                startActivity(intent);
                                break;
                            }
                            case R.id.nav_feed: {
                                //menuItem.setChecked(false);
                                Intent intent= new Intent(getApplicationContext(),FeedActivity.class);
                                startActivity(intent);
                                break;
                            }
                            case R.id.nav_logout:{
                                //finishAffinity();
                                Intent intent = new Intent (getApplicationContext(), SplitHomeActivity.class);
                                startActivity(intent);
                                break;
                            }
                        }
                        return true;
                    }
                });
        /***END MENU CODE ***/
    }

    public void populateHTTPCards() {
        Gson gson = new Gson();

        for(LocationData profile : gson.fromJson(myResponse, GoogleQuery.class).getData()) {
            if (profile.getPhoto().isEmpty() == false)// If no photo, don't make a card
                mSwipeView.addView(new LocationCard(mContext, profile, mSwipeView));
        }
    }
    private void populateCSULBCards() {
        /*** For use with CSULB Profiles ***/
        for(Profile profile : Utils.loadProfiles(this.getApplicationContext())) {
            //mSwipeView.addView(new LocationCard(mContext, profile, mSwipeView));
        }
    }

    private void populateLocalPlacesAPICards() {

        // For use with Google Places API
        try {
            String url = "https://maps.googleapis.com/maps/api/place/nearbysearch/json?location=33.783022,-118.112858&radius=6000&type=museum,library,aquarium&key=";
            url += BuildConfig.ApiKey;

            for(LocationData profile : Utils.loadLocationProfiles(mContext, new URL(url))) {
                // If no photo, don't make a card
                if (profile.getPhoto().isEmpty() == false)
                    mSwipeView.addView(new LocationCard(mContext, profile, mSwipeView));
            }
        } catch (JSONException e1) {
            e1.printStackTrace();
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        drawerLayout=findViewById(R.id.drawer_layout);
        switch (item.getItemId()) {
            case android.R.id.home:
                drawerLayout.openDrawer(GravityCompat.START);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed()
    {
        this.finishAffinity();
        System.exit(0);
    }

    public class Network extends AsyncTask<Void, Void, Integer> {
        public String queryGooglePlaces() throws IOException, JSONException {

            String url = "https://maps.googleapis.com/maps/api/place/nearbysearch/json?"
                    + "location=33.783022,-118.112858"
                    + "&radius=12000"
                    + "&type=museum"
                    + "&keyword=art&key="
                    + BuildConfig.ApiKey;

            int response_code;
            HttpsURLConnection con;
            con = (HttpsURLConnection) new URL(url).openConnection();
            con.connect();
            con.setRequestMethod("GET");
            response_code = con.getResponseCode();

            BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
            String inputLine = null;
            StringBuffer response = new StringBuffer();

            while ((inputLine = in.readLine()) != null) {
                response.append(inputLine);
            }

            in.close();
            myResponse = response.toString();
            looper();

            return null;
        }

        public void looper() {
            while(myResponse == null) {}
        }

        @Override
        protected Integer doInBackground(Void... voids) {
            try {
                queryGooglePlaces();
            } catch (IOException e) {
                e.printStackTrace();
            } catch (JSONException e) {
                e.printStackTrace();
            }

            return 0;
        }

        @Override
        protected void onPostExecute(Integer useless) {
            populateHTTPCards();
        }
    }
}


