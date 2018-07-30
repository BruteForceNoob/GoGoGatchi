package com.gogogatchi.gogogatchi.activities;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Patterns;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.Toast;

import com.gogogatchi.gogogatchi.FirebaseDB;
import com.gogogatchi.gogogatchi.R;
import com.gogogatchi.gogogatchi.core.User;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.EmailAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthUserCollisionException;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class RegisterActivity extends AppCompatActivity{

    private ProgressBar progressBar;
    private Spinner spinnerGender, spinnerInterest, spinnerAge;
    private EditText editTextEmail, editTextPassword, editUsername;
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

    private void handleInterestSpinner() {
        // create spinner or dropdown box class
        ArrayAdapter<CharSequence> adapter =
                ArrayAdapter.createFromResource(this, R.array.interest, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerInterest.setAdapter(adapter);
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
        String textEthnicity = spinnerInterest.getSelectedItem().toString();
        String userId = FirebaseAuth.getInstance().getCurrentUser().getUid();

        if (textGender.equals("Select Option..")) {
            textGender = "";
        }
        if (textAge.equals("Select Option..")) {
            textAge = "";
        }
        if (textEthnicity.equals("Select Option..") ||
                textEthnicity.equals("Decline to State")) {
            textEthnicity = "";
        }

        DatabaseReference profileDB = mDatabase.child("users").child(userId);

            User currentUser = new User(userId, username, textGender, textAge, textEthnicity);
            profileDB.setValue(currentUser);

    }

    public void registerUser(View v) {
        String email = editTextEmail.getText().toString().trim();
        String password = editTextPassword.getText().toString().trim();
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

        editTextPassword = findViewById(R.id.etCreatePW);
        editTextPassword.addTextChangedListener(mTextEditorWather);
        editUsername = findViewById(R.id.etCreateUser);

        spinnerGender = findViewById(R.id.spnGender);
        spinnerInterest = findViewById(R.id.spnInt);
        spinnerAge = findViewById(R.id.spnAge);

        progressBar = (ProgressBar) findViewById(R.id.pbRegister);

        mAuth = FirebaseAuth.getInstance();
        mDatabase = FirebaseDatabase.getInstance().getReference();

        // initialize drop box for gender
        handleGenderSpinner();
        handleAgeSpinner();
        handleInterestSpinner();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        // initialize activity
        init();
    }


}
