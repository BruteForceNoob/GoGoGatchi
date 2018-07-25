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
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;

import com.gogogatchi.gogogatchi.core.LocationCard;
import com.gogogatchi.gogogatchi.core.Profile;
import com.gogogatchi.gogogatchi.R;
import com.gogogatchi.gogogatchi.util.Utils;
import com.mindorks.placeholderview.SwipeDecor;
import com.mindorks.placeholderview.SwipePlaceHolderView;

import java.util.List;

import static com.gogogatchi.gogogatchi.util.Utils.loadProfiles;

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
         appBar=(Toolbar) findViewById(R.id.app_bar);
        setSupportActionBar(appBar);
      // getSupportActionBar().setDisplayShowTitleEnabled(false);
      ActionBar actionBar = getSupportActionBar();

        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setHomeAsUpIndicator(R.drawable.ic_baseline_menu_24px);
       // actionBar.setIcon(R.drawable.ic_newspaper);

        mSwipeView = findViewById(R.id.swipeView);
        mContext = getApplicationContext();

        //================================================
        //Check if any profiles left;
        final Integer[] cardinality = {loadProfiles(mContext).size()};
        //================================================

        mSwipeView.getBuilder()
                .setDisplayViewCount(3)
                .setSwipeDecor(new SwipeDecor()
                        .setPaddingTop(20)
                        .setRelativeScale(0.01f)
                        .setSwipeInMsgLayoutId(R.layout.location_swipe_right)
                        .setSwipeOutMsgLayoutId(R.layout.location_swipe_left));


        //Load all Profiles from JSON query
        for(Profile profile : loadProfiles(this.getApplicationContext())){
            mSwipeView.addView(new LocationCard(mContext, profile, mSwipeView));
        }

        findViewById(R.id.rejectBtn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //--cardinality[0];
                //System.out.print("REJECT");
                mSwipeView.doSwipe(false);
            }
        });

        findViewById(R.id.acceptBtn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //--cardinality[0];
                //System.out.print("ACCEPT");
                mSwipeView.doSwipe(true);
            }
        });

        /*
        if (cardinality[0] == loadProfiles(mContext).size() - 1) {
            System.out.print("HIHIHIHIHIHIHIHIHIHIHIHIHIHIHIHIHIHIHIHIHIHIHIHIHIHIHIHIHIHIHIHIHIHIHIHIHIHIHIHIHIHIHIHIHIHI");
            Log.d("END","END");
        }
        */

        //Stop drawing Reject and Accept buttons when no more profiles
        //R.id.LinearLayoutSwipe;

//        drawerLayout=findViewById(R.id.drawer_layout);
//        actionBarDrawerToggle= new ActionBarDrawerToggle(this,drawerLayout,R.string.open,R.string.close);
//        drawerLayout.addDrawerListener(actionBarDrawerToggle);
//        actionBarDrawerToggle.syncState();
//        getSupportActionBar().setDisplayHomeAsUpEnabled(true);



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

        System.exit(0);}
}


