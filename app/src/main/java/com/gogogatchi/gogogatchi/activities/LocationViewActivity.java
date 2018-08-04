package com.gogogatchi.gogogatchi.activities;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;
import android.widget.TextView;

import com.gogogatchi.gogogatchi.R;
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

public class LocationViewActivity extends AppCompatActivity {

    protected GeoDataClient mGeoDataClient;
    protected PlaceDetectionClient mPlaceDetectionClient;

    private Bitmap bitmap = null;
    private static Profile mProfile;
    private static LocationData mLocationProfile;
    ImageView imageView;
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

        imageView = findViewById(R.id.imageView5);
        textView = findViewById(R.id.textView4);

        /*** For use with Google Places API ***/
        Bundle bundle = this.getIntent().getExtras();
        if (bundle != null)
            mLocationProfile = bundle.getParcelable("mLocationProfile");


        final String placeId = mLocationProfile.getPlaceID();
        final Task<PlacePhotoMetadataResponse> photoMetadataResponse = mGeoDataClient
                .getPlacePhotos(placeId);


        //Will use this to retrieve list of pictures, working
        photoMetadataResponse
                .addOnCompleteListener(new OnCompleteListener<PlacePhotoMetadataResponse>() {
            @Override
            public void onComplete(@NonNull Task<PlacePhotoMetadataResponse> task) {
                // Get the list of photos.
                PlacePhotoMetadataResponse photos = task.getResult();

                // Get the PlacePhotoMetadataBuffer (metadata for all of the photos).
                PlacePhotoMetadataBuffer photoMetadataBuffer = photos.getPhotoMetadata();

                // Get the first photo in the list.

                PlacePhotoMetadata photoMetadata = null;
                for (PlacePhotoMetadata instance: photoMetadataBuffer) {
                     photoMetadata = instance;
                }

                // Get the attribution text.
                CharSequence attribution = photoMetadata.getAttributions();

                // Get a full-size bitmap for the photo.
                Task<PlacePhotoResponse> photoResponse = mGeoDataClient.getPhoto(photoMetadata);

                photoResponse.addOnCompleteListener(new OnCompleteListener<PlacePhotoResponse>() {
                    @Override
                    public void onComplete(@NonNull Task<PlacePhotoResponse> task) {
                        PlacePhotoResponse photo = task.getResult();
                        bitmap = photo.getBitmap();
                        imageView.setImageBitmap(bitmap);
                    }
                });
            }
        });

        textView.setText(mLocationProfile.getLocationName());
    }
}