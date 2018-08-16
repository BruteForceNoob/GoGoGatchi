package com.gogogatchi.gogogatchi.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.util.Patterns;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.gogogatchi.gogogatchi.R;
import com.gogogatchi.gogogatchi.adapters.ViewPagerAdapter;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    ViewPager viewPager;
    private FirebaseAuth mAuth;
    EditText editTextEmail, editTextPassword;
    ProgressBar progressBar;

    //Function for user log in activity
    private void userLogin() {
        String email = editTextEmail.getText().toString().trim();
        String password = editTextPassword.getText().toString().trim();

        //Checks for email validation using pattern library
        if (email.isEmpty()) {
            editTextEmail.setError("Username is required");
            editTextEmail.requestFocus();
            return;
        }
        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            editTextEmail.setError("Please enter a valid email");
            editTextEmail.requestFocus();
            return;
        }
        //Checks password fields
        if (password.isEmpty()) {
            editTextPassword.setError("Password is required");
            editTextPassword.requestFocus();
            return;
        }
        if (password.length() < 6) {
            editTextPassword.setError("Minimum length of password should be 6");
            editTextPassword.requestFocus();
            return;
        }
        //Once those tasks have been completed, it will log in the user.
        mAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()){
                    Intent intent = new Intent(MainActivity.this, HomeSwipeActivity.class);
                    startActivity(intent);
                }else{
                    Toast.makeText(getApplicationContext(), "Log-In Error", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    //Initialization of text fields and buttons
    private void init(){
        editTextEmail = (EditText) findViewById(R.id.editEmail);
        editTextPassword = (EditText) findViewById(R.id.editPassword);
        //Enter more if needed
        mAuth = FirebaseAuth.getInstance();

        Button btnLogin, btnReg;
        btnLogin = (Button) findViewById(R.id.btnLogin);
       // btnReg = (Button) findViewById(R.id.btnRegister);
        btnLogin.setOnClickListener(this);
        //btnReg.setOnClickListener(this);
    }

    //OnClick creation implementation
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            //case R.id.btnRegister:
             //   Intent openRegister = new Intent(MainActivity.this, RegisterActivity.class);
              //  startActivity(openRegister);
             //   break;
            case R.id.btnLogin:
                userLogin();
                break;
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().setTitle(getString(R.string.login));
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        setContentView(R.layout.activity_main);

        init();
        /*
        Button button=findViewById(R.id.button2);
        button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), MonsterHatchActivity.class);
            startActivity(intent);}
        });
        */

        //View pager implementation so user can see images of locations preview
        viewPager = (ViewPager) findViewById(R.id.viewPager);
        Integer [] images = {R.mipmap.clocktower1, R.mipmap.fountain1, R.mipmap.csulb1};
        ViewPagerAdapter viewPagerAdapter = new ViewPagerAdapter(this, images);
        viewPager.setAdapter(viewPagerAdapter);
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

