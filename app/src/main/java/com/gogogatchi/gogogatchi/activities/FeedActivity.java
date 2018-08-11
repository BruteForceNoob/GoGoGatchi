package com.gogogatchi.gogogatchi.activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.Toast;

import com.gogogatchi.gogogatchi.R;
import com.gogogatchi.gogogatchi.adapters.FeedAdapter;

public class FeedActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_feed);

        RecyclerView rv = (RecyclerView)findViewById(R.id.rv);
        rv.setHasFixedSize(true);

        LinearLayoutManager llm = new LinearLayoutManager(getApplicationContext());
        rv.setLayoutManager(llm);
        if(HomeSwipeActivity.locationDataList.isEmpty())
        {
            Toast.makeText(getApplicationContext(),"You have not liked any locations yet!",Toast.LENGTH_LONG).show();
        }
        else {
            FeedAdapter adapter = new FeedAdapter(HomeSwipeActivity.locationDataList, this);
            rv.setAdapter(adapter);
        }
    }
}