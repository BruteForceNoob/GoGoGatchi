package com.gogogatchi.gogogatchi.activities;

import android.content.Context;
import android.content.Intent;
import android.location.Location;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.gogogatchi.gogogatchi.BuildConfig;
import com.gogogatchi.gogogatchi.exceptions.LocationException;
import com.gogogatchi.gogogatchi.R;
import com.gogogatchi.gogogatchi.core.GoogleQuery;
import com.gogogatchi.gogogatchi.core.LocationCard;
import com.gogogatchi.gogogatchi.core.LocationData;
import com.gogogatchi.gogogatchi.util.FirebaseDB;
import com.gogogatchi.gogogatchi.util.MapUtil;
import com.gogogatchi.gogogatchi.util.Network;
import com.gogogatchi.gogogatchi.util.UserUtil;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.gson.Gson;
import com.mindorks.placeholderview.SwipeDecor;
import com.mindorks.placeholderview.SwipePlaceHolderView;

import java.util.ArrayList;
import java.util.List;

public class HomeSwipeActivity extends AppCompatActivity {
    private SwipePlaceHolderView mSwipeView;
    private DrawerLayout drawerLayout;
    private ActionBarDrawerToggle actionBarDrawerToggle;
    private Context mContext;
    private Toolbar appBar;
    private List<LocationData> places;
    private MapUtil mapUtil;
    private String myResponse = null;
    private Location location;
    private static Integer dist;
    private List<String> keywords=new ArrayList<String>();
    public static List<LocationData> locationDataList=new ArrayList<>();
    private UserUtil userUtil;
    public static boolean dbFlag;
    public String getResponse() {
        return myResponse;
    }
    public Context getmContext() {
        return mContext;
    }
    private TextView distanceTextView;
    private FirebaseAuth mAuth;
    private DatabaseReference mRef;
    private FirebaseUser currentFirebaseUser;
    private static ArrayList<String> types_of_loc= new ArrayList<>();
    private static Integer typePos = 0;
    private static boolean flag = true;

    private static TextView locT;
    private static ImageButton rewind;
    private static ImageButton fastforward;

    private static DatabaseReference mChildRef;
    private static String uuid;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        build();

        try{
            mAuth = FirebaseAuth.getInstance();
            mRef = FirebaseDB.mDatabase;
            userUtil=UserUtil.getInstance();
            if(dbFlag)
            {locationDataList= userUtil.getLikedLocations(); dbFlag=false;}

            setContentView(R.layout.activity_home_swipe2);
            mapUtil=new MapUtil(getApplicationContext());
            location=mapUtil.getLocation();
            keywords.add("art");
            mSwipeView = findViewById(R.id.swipeView);
            mContext = getApplicationContext();

            locT = findViewById(R.id.type_of_loc);
            locT.setText(types_of_loc.get(typePos));
            rewind = findViewById(R.id.rewind);
            fastforward = findViewById(R.id.fastforward);
            makeHttpCall(location,keywords);

            /*** Begin Menu Code ***/
            appBar = (Toolbar) findViewById(R.id.app_bar);
            setSupportActionBar(appBar);
            // getSupportActionBar().setDisplayShowTitleEnabled(false);
            ActionBar actionBar = getSupportActionBar();
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setHomeAsUpIndicator(R.drawable.ic_baseline_menu_24px);
            // actionBar.setIcon(R.drawable.ic_newspaper);
            /*** End Menu Code ***/

            mSwipeView.getBuilder()
                    .setDisplayViewCount(3)
                    .setSwipeDecor(new SwipeDecor()
                            //.setPaddingTop(-40)
                            .setPaddingLeft(30)
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
            findViewById(R.id.outOfIdeas).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    keywords=new ArrayList<>();
                    keywords.add("history");
                    keywords.add("maritime");
                    keywords.add("aeronautical");
                    keywords.add("war");
                    makeHttpCall(location,keywords);

                }
            });
            findViewById(R.id.companion_bottom).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent= new Intent(getApplicationContext(), CompanionActivity.class);
                    startActivity(intent);
                }
            });

            rewind.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (flag) {
                        typePos = types_of_loc.size() - 1;
                        flag = false;
                    }
                    else {
                        --typePos;
                        if (typePos < 0)
                            typePos = types_of_loc.size() - 1;
                    }
                    mSwipeView.removeAllViews();

                    locT.setText(types_of_loc.get(typePos));
                    makeHttpCall(location,keywords);
                }
            });
            fastforward.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (flag) {
                        typePos = 1;
                        flag = false;
                    }
                    else {
                        ++typePos;
                        if (typePos >= types_of_loc.size())
                            typePos = 0;
                    }

                    mSwipeView.removeAllViews();
                    locT.setText(types_of_loc.get(typePos));
                    makeHttpCall(location,keywords);
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
                                case R.id.nav_logout:{
                                    //finishAffinity();

                                    userUtil.updateLikedLocations(locationDataList);
                                    Intent intent = new Intent (getApplicationContext(), SplitHomeActivity.class);
                                    startActivity(intent);
                                    break;
                                }
                                case R.id.nav_feed:
                                {
                                    Intent intent = new Intent (getApplicationContext(), FeedActivity.class);
                                    startActivity(intent);
                                    break;
                                }
                            }
                            return true;
                        }
                    });
        }
        catch(LocationException e)
        {
            Toast.makeText(getApplicationContext(),e.getMessage()+" Please relaunch app.",Toast.LENGTH_LONG).show();
        }
        catch(NullPointerException e)
        {
            Toast.makeText(getApplicationContext(),e.getMessage()+" Please relaunch app.",Toast.LENGTH_LONG).show();
        }

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

    @Override
    public void onBackPressed()
    {
        this.finishAffinity();
        System.exit(0);
    }

    public void makeHttpCall(Location location,List<String> keywords)
    {
        String concatedKeyWords="";
        for(int i=0;i<keywords.size();i++)
        {
            concatedKeyWords+=keywords.get(i);
            if(i!=keywords.size()-1)
                concatedKeyWords+="|";
        }

        String userQuery = "https://maps.googleapis.com/maps/api/place/nearbysearch/json?"
                + "&location="
                + String.valueOf(location.getLatitude()) + ","
                + String.valueOf(location.getLongitude())
                //+ "&radius=" + String.valueOf(dist * 1069)
                + "&rankby=distance"
                + "&type=" + types_of_loc.get(typePos)
                //+ "&keyword="+concatedKeyWords
                + "&key="
                + BuildConfig.ApiKey;

        Log.d("///", userQuery);

        /*** HTTP QUERY PLACES API***/
        Network task = new Network(userQuery,this);
        task.execute();
    }

    public void populateCards(String myResponse) {
        Gson gson = new Gson();
        mSwipeView = findViewById(R.id.swipeView);

        for (LocationData profile : gson.fromJson(myResponse, GoogleQuery.class).getData()) {
            if (profile.getPhoto().isEmpty() == false && profile.getRating() > 3.4f
                    && isDuplicate(profile) == false)
                mSwipeView.addView(new LocationCard(mContext, profile, mSwipeView));
        }
    }

    public boolean isDuplicate(LocationData profile) {
        return locationDataList.contains(profile);
    }

    public void build() {
        types_of_loc.add("museum");
        types_of_loc.add("amusement_park");
        types_of_loc.add("art_gallery");
        types_of_loc.add("cafe");
        types_of_loc.add("campground");
        types_of_loc.add("lodging");
        types_of_loc.add("movie_theater");
        types_of_loc.add("night_club");
        types_of_loc.add("bar");
        types_of_loc.add("park");
        types_of_loc.add("restaurant");
        types_of_loc.add("shopping_mall");
        types_of_loc.add("stadium");
        types_of_loc.add("travel_agency");
        types_of_loc.add("zoo");
        typePos = 0;
        flag = true;

    }
}


