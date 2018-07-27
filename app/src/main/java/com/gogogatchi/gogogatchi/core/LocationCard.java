package com.gogogatchi.gogogatchi.core;

import android.app.Fragment;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.MultiTransformation;
import com.gogogatchi.gogogatchi.R;
import com.gogogatchi.gogogatchi.activities.LocationViewActivity;

import com.gogogatchi.gogogatchi.activities.MonsterHatchActivity;
import com.mindorks.placeholderview.SwipePlaceHolderView;
import com.mindorks.placeholderview.Utils;
import com.mindorks.placeholderview.annotations.Click;
import com.mindorks.placeholderview.annotations.Layout;
import com.mindorks.placeholderview.annotations.Resolve;
import com.mindorks.placeholderview.annotations.View;
import com.mindorks.placeholderview.annotations.swipe.SwipeCancelState;
import com.mindorks.placeholderview.annotations.swipe.SwipeIn;
import com.mindorks.placeholderview.annotations.swipe.SwipeInState;
import com.mindorks.placeholderview.annotations.swipe.SwipeOut;
import com.mindorks.placeholderview.annotations.swipe.SwipeOutState;

@Layout(R.layout.location_card)
public class LocationCard {

    @View(R.id.profileImageView)
    private ImageView profileImageView;

    @View(R.id.nameAgeTxt)
    private TextView destNameTxt;

    @View(R.id.locationNameTxt)
    private TextView cityNameTxt;

    private Profile mProfile;
    private Context mContext;
    private SwipePlaceHolderView mSwipeView;

    public LocationCard(Context context, Profile profile, SwipePlaceHolderView swipeView) {
        mContext = context;
        mProfile = profile;
        mSwipeView = swipeView;
    }

    @Resolve
    private void onResolved(){
        Glide.with(mContext).load(mProfile.getImageUrl()).into(profileImageView);
        destNameTxt.setText(mProfile.getDestinationName());
        cityNameTxt.setText(mProfile.getCity());
    }

    @Click(R.id.profileImageView)
    private void onClick(){
        Intent intent = new Intent(mContext.getApplicationContext(), LocationViewActivity.class);
        Bundle bundle = new Bundle();
        bundle.putParcelable("mProfile", mProfile);
        intent.putExtras(bundle);
        mContext.startActivity(intent);

        /*
        Intent intent = new Intent(mContext.getApplicationContext(), LocationViewActivity.class);
        intent.putExtra("mProfile", mProfile);
        mContext.startActivity(intent);
        */
    }

    // Swipe Left
    @SwipeOut
    private void onSwipedOut(){
        //mSwipeView.addView(this);
    }

    // Swiped, but let go
    @SwipeCancelState
    private void onSwipeCancelState(){

    }

    //Swipe Right
    @SwipeIn
    private void onSwipeIn(){
    }

    @SwipeInState
    private void onSwipeInState(){
    }

    @SwipeOutState
    private void onSwipeOutState(){
    }
}
