package com.gogogatchi.gogogatchi.activities;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Patterns;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.Toast;

import com.gogogatchi.gogogatchi.R;
import com.gogogatchi.gogogatchi.core.User;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthUserCollisionException;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;
import java.util.Map;

public class RegisterActivity extends AppCompatActivity{

    private ProgressBar progressBar;
    private Spinner spinnerGender, spinnerInterest, spinnerAge;
    private EditText editTextEmail, editTextPassword, editUsername;
    private EditText editTextPasswordCheck, editTextEmailCheck;
    private FirebaseAuth mAuth;
    private DatabaseReference mDatabase;

    // watch changes in text to test password length
    private final TextWatcher mTextEditorWather = new TextWatcher() {
        int passLength = 0;
        @Override
        public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
        }
        @Override
        public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            passLength = charSequence.length();
            if (passLength > 0 && passLength < 6) {
                editTextPassword.setError("Minimum length of password should be 6");
                editTextPassword.requestFocus();
            }
        }
        @Override
        public void afterTextChanged(Editable editable) {
            /*
            String passwordOG = editTextPassword.getText().toString().trim();
            String passwordCheck = editTextPasswordCheck.getText().toString().trim();
            if (!passwordOG.equals(passwordCheck)){
                editTextPasswordCheck.setError("Passwords do not match");
                editTextPasswordCheck.requestFocus();
            }
            */

        }
    };

    //Watches changes in text for password checking between 2 fields
    private final TextWatcher mTextEditorWather2 = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
        }
        @Override
        public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            String passwordOG = editTextPassword.getText().toString().trim();
            String passwordCheck = editTextPasswordCheck.getText().toString().trim();
            if (!passwordOG.equals(passwordCheck)){
                editTextPasswordCheck.setError("Passwords do not match");
                editTextPasswordCheck.requestFocus();
            }
        }
        @Override
        public void afterTextChanged(Editable editable) {

        }

    };

    //Text watcher for emails to match
    private final TextWatcher mTextEditorWather3 = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
        }
        @Override
        public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            String emailOG = editTextEmail.getText().toString().trim();
            String emailCheck = editTextEmailCheck.getText().toString().trim();
            if (!emailOG.equals(emailCheck)){
                editTextEmailCheck.setError("Emails do not match");
                editTextEmailCheck.requestFocus();
            }
        }
        @Override
        public void afterTextChanged(Editable editable) {

        }

    };



    ///Spinner Section
    private void handleGenderSpinner() {
        // create spinner or dropdown box class
        ArrayAdapter<CharSequence> adapter =
                ArrayAdapter.createFromResource(this, R.array.gender, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerGender.setAdapter(adapter);
    }

    private void handleAgeSpinner() {
        // create spinner or dropdown box class
        ArrayAdapter<CharSequence> adapter =
                ArrayAdapter.createFromResource(this, R.array.age, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerAge.setAdapter(adapter);
    }
    ///End Spinner Section

    private void writeOptionalProfile(String username) {
        String textGender = spinnerGender.getSelectedItem().toString();
        String textAge = spinnerAge.getSelectedItem().toString();
        Map<String,Boolean>interests = new HashMap<>();
        interests.put("Amusement Park",true);
        interests.put("Art Gallery",true);
        interests.put("Bar",true);
        interests.put("Cafe",true);
        interests.put("Campground",true);
        interests.put("Lodging",true);
        interests.put("Movie Theatre",true);
        interests.put("Museum",true);
        interests.put("Night Club",true);
        interests.put("Park",true);
        interests.put("Restaurant",true);
        interests.put("Shopping Mall",true);
        interests.put("Stadium",true);
        interests.put("Travel Agency",true);
        interests.put("Zoo",true);
        Integer distance=5;
        String userId = FirebaseAuth.getInstance().getCurrentUser().getUid();
        String profileImage = "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";

        if (textGender.equals("Select Option..")) {
            textGender = "";
        }
        if (textAge.equals("Select Option..")) {
            textAge = "";
        }

        DatabaseReference profileDB = mDatabase.child("users").child(userId);

            User currentUser = new User(userId, username, textGender, textAge, profileImage,interests,distance);
            profileDB.setValue(currentUser);

    }

    //Additional checks on registration activity
    public void registerUser(View v) {
        String email = editTextEmail.getText().toString().trim();
        String emailCheck = editTextEmailCheck.getText().toString().trim();
        String password = editTextPassword.getText().toString().trim();
        String passwordCheck = editTextPasswordCheck.getText().toString().trim();
        final String username = editUsername.getText().toString().trim();

        if (email.isEmpty()) {
            editTextEmail.setError("Email is required");
            editTextEmail.requestFocus();
            return;
        }
        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            editTextEmail.setError("Please enter a valid email");
            editTextEmail.requestFocus();
            return;
        }
        if (emailCheck.isEmpty()){
            editTextEmailCheck.setError("Email is required");
            editTextEmailCheck.requestFocus();
            return;
        }
        if (!emailCheck.equals(email)){
            editTextEmailCheck.setError("Incorrect email");
            editTextEmailCheck.requestFocus();
            return;
        }
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
        if (passwordCheck.isEmpty()) {
            editTextPasswordCheck.setError("Re-Enter Password");
            editTextPasswordCheck.requestFocus();
            return;
        }
        if (!passwordCheck.equals(password)) {
            editTextPasswordCheck.setError("Incorrect password");
            editTextPassword.requestFocus();
            return;
        }
        if (username.isEmpty()) {
            editUsername.setError("Username is required");
            editUsername.requestFocus();
            return;
        }

        progressBar.setVisibility(View.VISIBLE);
        //FirebaseUser temp = mAuth.getCurrentUser();
            mAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    progressBar.setVisibility(View.GONE);
                    if (task.isSuccessful()) {
                        writeOptionalProfile(username);
                        Toast.makeText(getApplicationContext(),
                                "User Registration Successful", Toast.LENGTH_SHORT).show();
                        Intent mainMenu =
                                new Intent(RegisterActivity.this, MonsterHatchActivity.class);
                        startActivity(mainMenu);
                        finish();
                    } else {
                        if (task.getException() instanceof FirebaseAuthUserCollisionException) {
                            Toast.makeText(getApplicationContext(),
                                    "You are already registered", Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(getApplicationContext(),
                                    task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    }
                }
            });

    }

    private void init() {
        editTextEmail = findViewById(R.id.etCreateEmail);
        editTextEmail.requestFocus();

        editTextEmailCheck = findViewById(R.id.etCheckEmail);
        //editTextEmailCheck.requestFocus();
        editTextEmailCheck.addTextChangedListener((mTextEditorWather3));

        editTextPassword = findViewById(R.id.etCreatePW);
        editTextPassword.addTextChangedListener(mTextEditorWather);

        //New check
        editTextPasswordCheck = findViewById(R.id.etCheckPW);
        editTextPasswordCheck.addTextChangedListener(mTextEditorWather2);

        editUsername = findViewById(R.id.etCreateUser);

        spinnerGender = findViewById(R.id.spnGender);
        //spinnerInterest = findViewById(R.id.spnInt);
        spinnerAge = findViewById(R.id.spnAge);

        progressBar = (ProgressBar) findViewById(R.id.pbRegister);

        mAuth = FirebaseAuth.getInstance();
        mDatabase = FirebaseDatabase.getInstance().getReference();

        // initialize drop box for gender
        handleGenderSpinner();
        handleAgeSpinner();
        //handleInterestSpinner();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().setTitle(getString(R.string.register2));
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        setContentView(R.layout.activity_register);

        // initialize activity
        init();
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
