package com.gogogatchi.gogogatchi.activities;

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
import android.view.MenuItem;
import android.view.View;

import com.gogogatchi.gogogatchi.BuildConfig;
import com.gogogatchi.gogogatchi.core.LocationCard;
import com.gogogatchi.gogogatchi.R;
import com.gogogatchi.gogogatchi.core.LocationData;
import com.gogogatchi.gogogatchi.core.Profile;
import com.gogogatchi.gogogatchi.util.*;

import com.mindorks.placeholderview.SwipeDecor;
import com.mindorks.placeholderview.SwipePlaceHolderView;

import org.json.JSONException;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

public class HomeSwipeActivity extends AppCompatActivity {

    private SwipePlaceHolderView mSwipeView;
    private DrawerLayout drawerLayout;
    private ActionBarDrawerToggle actionBarDrawerToggle;
    private Context mContext;
    private Toolbar appBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_swipe);

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

        //Load all Profiles from JSON query
        /*** For use with CSULB Profiles ***/
        /*
        for(Profile profile : Utils.loadProfiles(this.getApplicationContext())) {
            mSwipeView.addView(new LocationCard(mContext, profile, mSwipeView));
        }
        */

        /*** For use with Google Places API ***/
        try {
            String url = "https://maps.googleapis.com/maps/api/place/nearbysearch/json?location=33.783022,-118.112858&radius=6000&type=museum,library,aquarium&key=";
            url += BuildConfig.ApiKey;

            for(LocationData profile : Utils.loadLocationProfiles(mContext, new URL(url))) {
                mSwipeView.addView(new LocationCard(mContext, profile, mSwipeView));
            }
        } catch (JSONException e1) {
            e1.printStackTrace();
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

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
                                Intent intent = new Intent (getApplicationContext(), MainActivity.class);
                                startActivity(intent);
                                break;
                            }
                        }
                        return true;
                    }
                });
        /***END MENU CODE ***/
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

    /*@Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.action_bar_menu, menu);
        return true;
    }*/
    @Override
    public void onBackPressed()
    {
        this.finishAffinity();
        System.exit(0);
    }

}


