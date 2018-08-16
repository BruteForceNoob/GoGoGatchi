package com.gogogatchi.gogogatchi.activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.MenuItem;
import android.widget.Toast;

import com.gogogatchi.gogogatchi.R;
import com.gogogatchi.gogogatchi.adapters.FeedAdapter;

public class FeedActivity extends AppCompatActivity {

    //provides feed functionality wherein users can see what they have liked

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().setTitle(getString(R.string.newsFeed));
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        setContentView(R.layout.activity_feed);

        RecyclerView rv = (RecyclerView)findViewById(R.id.rv);  // recycler view for feeds
        rv.setHasFixedSize(true);

        LinearLayoutManager llm = new LinearLayoutManager(getApplicationContext());
        rv.setLayoutManager(llm);
        if(HomeSwipeActivity.locationDataList.isEmpty())
        {
            Toast.makeText(getApplicationContext(),"You have not liked any locations yet!",Toast.LENGTH_LONG).show();
        }
        else {
            FeedAdapter adapter = new FeedAdapter(HomeSwipeActivity.locationDataList, this); // initiate feed adapter for managing recycler view
            rv.setAdapter(adapter);
        }
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
