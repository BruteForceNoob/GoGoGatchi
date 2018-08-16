package com.gogogatchi.gogogatchi.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.transition.TransitionManager;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ScrollView;
import android.widget.TextView;

import com.gogogatchi.gogogatchi.R;

public class PrivacyActivity extends AppCompatActivity{

    private ScrollView privacyLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().setTitle(getString(R.string.termsConditions));
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        setContentView(R.layout.activity_privacy);
        privacyLayout = (ScrollView) findViewById(R.id.activity_privacy);
    }

    //Card view creation
    public void TermsConditions(View v) {
        CardView cardView = findViewById(R.id.cardViewTermsConditions);
        TextView text = findViewById(R.id.textViewTermsText);

        if(text.getVisibility() == View.GONE){
            TransitionManager.beginDelayedTransition(cardView);
            text.setVisibility(View.VISIBLE);
        }else{
            TransitionManager.beginDelayedTransition(cardView);
            text.setVisibility(View.GONE);
        }
    }

    //Card view creation
    public void PrivacyData(View v) {
        CardView cardView = findViewById(R.id.cardViewPrivacyData);
        TextView text = findViewById(R.id.textViewPrivacyText);

        if(text.getVisibility() == View.GONE){
            TransitionManager.beginDelayedTransition(cardView);
            text.setVisibility(View.VISIBLE);
        }else{
            TransitionManager.beginDelayedTransition(cardView);
            text.setVisibility(View.GONE);
        }
    }

    //Enables the back button as action bar and logic for which screen to go back to
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                Intent reHash = new Intent(this, SplitHomeActivity.class);
                reHash.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(reHash);
            default:
                return super.onOptionsItemSelected(item);
        }
    }

}
