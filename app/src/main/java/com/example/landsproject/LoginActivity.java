package com.example.landsproject;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class LoginActivity extends AppCompatActivity {
    private SharedPreferences sp;
    private EditText email, password;
    private Button btnSignIn;
    private TextView signUp;
    private FirebaseAuth mFirebaseAuth;
    private FirebaseAuth.AuthStateListener mAuthStateListener;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mFirebaseAuth = FirebaseAuth.getInstance();
        email = findViewById(R.id.emailtxt);
        password = findViewById(R.id.passwordTxt);
        btnSignIn = findViewById(R.id.loginBtn);
        signUp = findViewById(R.id.signupTxt);
        sp = getSharedPreferences("USER_PREFS", Context.MODE_PRIVATE);


        mAuthStateListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser mFirebaseUser = mFirebaseAuth.getCurrentUser();
                if (mFirebaseUser != null) {
                    Intent intent = new Intent(LoginActivity.this, LandsActivity.class);

                    startActivity(intent);
                }
            }
        };

        signUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginActivity.this, RegisterActivity.class);
                startActivity(intent);
                finish();
            }
        });



        btnSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String uEmail = email.getText().toString().trim();
                String uPassword = email.getText().toString().trim();
                String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";

                if (uEmail.isEmpty() || uPassword.isEmpty()){
                    Toast.makeText(getApplicationContext(), "complete empty fields", Toast.LENGTH_LONG).show();
                }else if (!(uEmail.matches(emailPattern) && uEmail.length() > 0)){
                    Toast.makeText(getApplicationContext(), "Email error", Toast.LENGTH_LONG).show();
                }else {
                    mFirebaseAuth.signInWithEmailAndPassword(uEmail,uPassword)
                            .addOnCompleteListener(LoginActivity.this, new OnCompleteListener<AuthResult>() {
                                @Override
                                public void onComplete(@NonNull Task<AuthResult> task) {
                                    if (task.isSuccessful()){
                                         SharedPreferences.Editor editor = sp.edit();
                                        editor.putString("email", uEmail);
                                        editor.apply();
                                        Intent intent = new Intent(LoginActivity.this, LandsActivity.class);
                                        startActivity(intent);
                                        finish();
                                    }else {
                                        Toast.makeText(getApplicationContext(), "you have entered wrong credentials", Toast.LENGTH_LONG).show();
                                    }
                                }
                            });
                }
            }
        });

    }

    @Override
    protected void onStart() {
        super.onStart();
        mFirebaseAuth.addAuthStateListener(mAuthStateListener);

    }
}