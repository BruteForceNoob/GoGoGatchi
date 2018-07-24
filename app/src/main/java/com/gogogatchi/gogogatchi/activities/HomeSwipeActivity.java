package com.gogogatchi.gogogatchi.activities;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.content.Context;
import android.util.Log;
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
    private Context mContext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_swipe);

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
    }
}