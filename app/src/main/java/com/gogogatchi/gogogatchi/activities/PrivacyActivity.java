package com.gogogatchi.gogogatchi.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import com.gogogatchi.gogogatchi.R;

public class PrivacyActivity extends AppCompatActivity implements View.OnClickListener{

    private void init() {
        Button btnAgree;
        btnAgree = (Button) findViewById(R.id.userAgree);
        btnAgree.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.userAgree:
                Intent goHome = new Intent(PrivacyActivity.this, SplitHomeActivity.class);
                startActivity(goHome);
                break;
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_privacy);
        init();
    }


}
