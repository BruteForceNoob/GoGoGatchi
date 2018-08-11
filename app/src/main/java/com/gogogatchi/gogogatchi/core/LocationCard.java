package com.gogogatchi.gogogatchi.core;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.gogogatchi.gogogatchi.R;
import com.gogogatchi.gogogatchi.activities.HomeSwipeActivity;
import com.gogogatchi.gogogatchi.activities.LocationViewActivity;
import com.mindorks.placeholderview.SwipePlaceHolderView;
import com.mindorks.placeholderview.annotations.Click;
import com.mindorks.placeholderview.annotations.Layout;
import com.mindorks.placeholderview.annotations.Resolve;
import com.mindorks.placeholderview.annotations.View;
import com.mindorks.placeholderview.annotations.swipe.SwipeCancelState;
import com.mindorks.placeholderview.annotations.swipe.SwipeIn;
import com.mindorks.placeholderview.annotations.swipe.SwipeInState;
import com.mindorks.placeholderview.annotations.swipe.SwipeOut;
import com.mindorks.placeholderview.annotations.swipe.SwipeOutState;

import java.io.IOException;
import java.net.URL;

@Layout(R.layout.location_card)
public class LocationCard {

    @View(R.id.profileImageView)
    private ImageView profileImageView;

    @View(R.id.nameAgeTxt)
    private TextView destNameTxt;

    @View(R.id.locationNameTxt)
    private TextView cityNameTxt;

    private LocationData mLocationProfile;
    private Profile mProfile;
    private Context mContext;
    private SwipePlaceHolderView mSwipeView;

    /*** For use with Google Places API Locations ***/
    public LocationCard(Context context, LocationData profile, SwipePlaceHolderView swipeView) {
        mContext = context;
        mLocationProfile = profile;
        mSwipeView = swipeView;
    }

    @Resolve
    private void onResolved() throws IOException {
        Bitmap image;
        URL url = mLocationProfile.getImageUrl();

        Glide.with(mContext).load(mLocationProfile.getImageUrl()).into(profileImageView);
        destNameTxt.setText(mLocationProfile.getLocationName());
        cityNameTxt.setText(mLocationProfile.getVicinity());
    }

    @Click(R.id.profileImageView)
    private void onClick(){
        Intent intent = new Intent(mContext.getApplicationContext(), LocationViewActivity.class);
        Bundle bundle = new Bundle();
        bundle.putParcelable("mLocationProfile", mLocationProfile);
        intent.putExtras(bundle);
        mContext.startActivity(intent);
    }

    // Swipe Left
    @SwipeOut
    private void onSwipedOut(){
        //mProfile = null;
        //mSwipeView.addView(this);
    }

    // Swiped, but let go
    @SwipeCancelState
    private void onSwipeCancelState(){
    }

    //Swipe Right
    @SwipeIn
    private void onSwipeIn(){
        HomeSwipeActivity.locationDataList.add(this.mLocationProfile);
        Log.i("location",this.mLocationProfile.toString());
    }

    // calls method while moving toward right
    @SwipeInState
    private void onSwipeInState(){
    }

    // calls method while moving toward left
    @SwipeOutState
    private void onSwipeOutState(){
    }
}
