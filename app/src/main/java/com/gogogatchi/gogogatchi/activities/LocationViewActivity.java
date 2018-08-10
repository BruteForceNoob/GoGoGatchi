package com.gogogatchi.gogogatchi.activities;

import android.content.Context;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.widget.RatingBar;
import android.widget.TextView;

import com.gogogatchi.gogogatchi.R;
import com.gogogatchi.gogogatchi.ViewPagerAdapter;
import com.gogogatchi.gogogatchi.core.LocationData;
import com.gogogatchi.gogogatchi.core.Profile;
import com.google.android.gms.location.places.GeoDataClient;
import com.google.android.gms.location.places.PlaceDetectionClient;
import com.google.android.gms.location.places.PlacePhotoMetadata;
import com.google.android.gms.location.places.PlacePhotoMetadataBuffer;
import com.google.android.gms.location.places.PlacePhotoMetadataResponse;
import com.google.android.gms.location.places.PlacePhotoResponse;
import com.google.android.gms.location.places.Places;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;

import java.util.ArrayList;

public class LocationViewActivity extends AppCompatActivity {

    private RatingBar ratingBar;
    private ViewPager viewPager;
    protected GeoDataClient mGeoDataClient;
    protected PlaceDetectionClient mPlaceDetectionClient;
    private Context mContext = this;

    private ArrayList pictures = new ArrayList<Bitmap>();

    private static Profile mProfile;
    private static LocationData mLocationProfile;
    TextView textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_location_view);

        /** PLACES SDK
         The GeoDataClient provides access to Google's database of local place and business
         information.
         The PlaceDetectionClient provides quick access to the device's current place, and offers
         the opportunity to report the location of the device at a particular place.

         See Link for more information:
         https://google-developer-training.gitbooks.io/android-developer-advanced-course-concepts/unit-4-add-geo-features-to-your-apps/lesson-8-places/8-1-c-places-api/8-1-c-places-api.html
         **/

        mGeoDataClient = Places.getGeoDataClient(this);
        mPlaceDetectionClient = Places.getPlaceDetectionClient(this);

        textView = findViewById(R.id.textView4);


        /*** For use with Google Places API ***/
        Bundle bundle = this.getIntent().getExtras();
        if (bundle != null)
            mLocationProfile = bundle.getParcelable("mLocationProfile");

        ratingBar = (RatingBar) findViewById(R.id.ratingBar);
        ratingBar.setMax(5);
        //ratingBar.setNumStars(5);

        ratingBar.setRating((float) mLocationProfile.getRating());
        textView.setText(mLocationProfile.getLocationName());

        final String placeId = mLocationProfile.getPlaceID();
        final Task<PlacePhotoMetadataResponse> photoMetadataResponse = mGeoDataClient
                .getPlacePhotos(placeId);


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

                viewPager = findViewById(R.id.viewPager2);
                //ViewPagerAdapter2 viewPagerAdapter = new ViewPagerAdapter2(mContext, pictures);
                Integer [] imgs = {R.mipmap.fountain1, R.mipmap.tower, R.mipmap.clocktower1};
                ViewPagerAdapter viewPagerAdapter = new ViewPagerAdapter(mContext, imgs);
                viewPager.setAdapter(viewPagerAdapter);
            }
        });
    }
}