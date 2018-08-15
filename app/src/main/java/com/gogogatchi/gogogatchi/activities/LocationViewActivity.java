package com.gogogatchi.gogogatchi.activities;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.gogogatchi.gogogatchi.R;
import com.gogogatchi.gogogatchi.core.LocationData;
import com.google.android.gms.location.places.GeoDataClient;
import com.google.android.gms.location.places.PlaceBufferResponse;
import com.google.android.gms.location.places.PlaceDetectionClient;
import com.google.android.gms.location.places.PlacePhotoMetadata;
import com.google.android.gms.location.places.PlacePhotoMetadataBuffer;
import com.google.android.gms.location.places.PlacePhotoMetadataResponse;
import com.google.android.gms.location.places.PlacePhotoResponse;
import com.google.android.gms.location.places.Places;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;

import java.net.MalformedURLException;
import java.util.LinkedList;

public class LocationViewActivity extends AppCompatActivity{

    private RatingBar ratingBar;
    private ViewPager viewPager;
    protected GeoDataClient mGeoDataClient;
    protected PlaceDetectionClient mPlaceDetectionClient;
    private Context mContext = this;

    private LinkedList<Bitmap> pictures = new LinkedList<Bitmap>();
    private boolean flag = true;
    private int count = 1;

    private static LocationData mLocationProfile;
    private TextView textView;
    private ImageView imgView;
    private ImageButton navi;

    private String pno = null;
    private String url = null;
    private String address = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().setTitle(getString(R.string.locationView));
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        setContentView(R.layout.activity_location_view);

        mGeoDataClient = Places.getGeoDataClient(this);
        mPlaceDetectionClient = Places.getPlaceDetectionClient(this);

        textView = findViewById(R.id.textView4);
        imgView = findViewById(R.id.imagess);
        navi = findViewById(R.id.navigateButton);
        navi.bringToFront();

        /*** For use with Google Places API ***/
        Bundle bundle = this.getIntent().getExtras();
        if (bundle != null)
            mLocationProfile = bundle.getParcelable("mLocationProfile");

        ratingBar = findViewById(R.id.ratingBar);
        ratingBar.setMax(5);
        ratingBar.setStepSize(0.5f);
        ratingBar.setRating((float) mLocationProfile.getRating());
        textView.setText(mLocationProfile.getLocationName());

        final String placeId = mLocationProfile.getPlaceID();
        final Task<PlacePhotoMetadataResponse> photoMetadataResponse =
                mGeoDataClient.getPlacePhotos(placeId);

        //get reviews
        String reviews_api_call = "https://maps.googleapis.com/maps/api/place/details/json?reference="
                + mLocationProfile.getLocationReference()
                + "&sensor=false&key="
                + com.gogogatchi.gogogatchi.BuildConfig.ApiKey;

        mGeoDataClient.getPlaceById(placeId).addOnCompleteListener(new OnCompleteListener<PlaceBufferResponse>() {
            @Override
            public void onComplete(@NonNull Task<PlaceBufferResponse> task) {
                if (task.isSuccessful()) {
                    PlaceBufferResponse places = task.getResult();

                    if (places.get(0).getPhoneNumber() != null)
                        pno = places.get(0).getPhoneNumber().toString();

                    if (places.get(0).getWebsiteUri() != null)
                        url = places.get(0).getWebsiteUri().toString();

                    if (places.get(0).getAddress() != null)
                        address = places.get(0).getAddress().toString();

                    places.release();
                }
            }
        });

        //Will use this to retrieve list of pictures, working
        photoMetadataResponse.addOnCompleteListener(new OnCompleteListener<PlacePhotoMetadataResponse>() {
            @Override
            public void onComplete(@NonNull Task<PlacePhotoMetadataResponse> task) {
                // Get the results: list of photos.
                PlacePhotoMetadataResponse photos = task.getResult();

                // Get the PlacePhotoMetadataBuffer
                final PlacePhotoMetadataBuffer photoMetadataBuffer = photos.getPhotoMetadata();

                // Traverse all photos
                for (PlacePhotoMetadata instance: photoMetadataBuffer) {
                    // A link to photographer images
                    CharSequence attribution = instance.getAttributions();

                    // Get the full-sized photo
                    Task<PlacePhotoResponse> photoResponse = mGeoDataClient.getPhoto(instance);

                    photoResponse.addOnCompleteListener(new OnCompleteListener<PlacePhotoResponse>() {
                        @Override
                        public void onComplete(@NonNull Task<PlacePhotoResponse> task) {
                            PlacePhotoResponse photo = task.getResult();
                            pictures.add(photo.getBitmap());
                            photoMetadataBuffer.release();
                        }
                    });
                }
            }
        });

        findViewById(R.id.leftButton).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (flag) {
                    resetCount(pictures.size() - 1);
                }
                else {
                    --count;
                    if (count < 0)
                        resetCount(pictures.size() - 1);
                }

                imgView.setImageBitmap(pictures.get(count));
            }
        });

        findViewById(R.id.rightButton).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (flag) {
                    resetCount(1);
                }
                else {
                    ++count;
                    if (count >= pictures.size())
                        resetCount(0);
                }

                imgView.setImageBitmap(pictures.get(count));
            }
        });

        findViewById(R.id.callButton).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (pno != null ) {
                    startActivity(new Intent(Intent.ACTION_DIAL,
                            Uri.fromParts("tel", pno, null)));
                }
                else {
                    Toast.makeText(mContext, mLocationProfile.getLocationName()
                            + " does not have an associated phone number.", Toast.LENGTH_LONG).show();
                }
            }
        });


        findViewById(R.id.websiteButton).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (url != null) {
                    startActivity(new Intent(Intent.ACTION_VIEW)
                            .setData(Uri.parse(url)));
                    Log.d("PHONE:", pno);
                }
                else
                    Toast.makeText(mContext, mLocationProfile.getLocationName()
                            + " does not have an associated website.", Toast.LENGTH_LONG).show();
            }
        });

        findViewById(R.id.reviewButton).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String newName = mLocationProfile.getLocationName()
                        .replace(' ', '+');
                String reviewUrl = "https://www.google.com/search?q="
                        + newName + "+reviews&oq=" + newName;

                startActivity(new Intent(Intent.ACTION_VIEW)
                        .setData(Uri.parse(reviewUrl)));
            }
        });

        findViewById(R.id.navigateButton).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (address != null) {
                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            Uri gmmIntentUri = Uri.parse("google.navigation:q=" + address);
                            Intent mapIntent = new Intent(Intent.ACTION_VIEW, gmmIntentUri);
                            mapIntent.setPackage("com.google.android.apps.maps");
                            startActivity(mapIntent);
                        }
                    }, 1000);
                }
                else {
                    Toast.makeText(mContext,
                            "Looks like we're having some problems navigating to "
                                    + mLocationProfile.getLocationName()
                                    + ".", Toast.LENGTH_LONG).show();
                }
            }
        });

        try {
            Glide.with(mContext).load(mLocationProfile.getImageUrl()).into(imgView);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
    }

    private void resetCount(Integer param) {
        count = param;
        flag = false;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                Intent reHash = new Intent(this, HomeSwipeActivity.class);
                reHash.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(reHash);
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}