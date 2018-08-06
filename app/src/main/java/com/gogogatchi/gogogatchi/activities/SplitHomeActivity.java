package com.gogogatchi.gogogatchi.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.util.Patterns;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.LinearLayout;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import android.widget.ProgressBar;

import com.gogogatchi.gogogatchi.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;

import java.util.Timer;
import java.util.TimerTask;

public class SplitHomeActivity extends AppCompatActivity implements View.OnClickListener{

    private void init() {
        Button btnLogin, btnReg;
        btnLogin = (Button) findViewById(R.id.loginSplit);
        btnReg = (Button) findViewById(R.id.createAccountSplit);

        btnLogin.setOnClickListener(this);
        btnReg.setOnClickListener(this);


    }


    @Override
    public void onClick(View v){
        switch (v.getId()) {
            case R.id.loginSplit:
                Intent openLogin = new Intent (SplitHomeActivity.this, MainActivity.class);
                startActivity(openLogin);
                break;

            case R.id.createAccountSplit:
                Intent createAccount = new Intent(SplitHomeActivity.this, RegisterActivity.class);
                startActivity(createAccount);
                break;
                //Implement xml view on privacy conditions later
        }

    }


    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_split_home);

        init();

    }



}