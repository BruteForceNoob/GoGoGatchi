package com.gogogatchi.gogogatchi.core;

import android.content.Context;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.gogogatchi.gogogatchi.R;
import com.mindorks.placeholderview.SwipePlaceHolderView;
import com.mindorks.placeholderview.annotations.Layout;
import com.mindorks.placeholderview.annotations.Resolve;
import com.mindorks.placeholderview.annotations.View;

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
}