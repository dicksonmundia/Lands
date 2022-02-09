package com.example.landsproject;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class RegisterActivity extends AppCompatActivity {
    private EditText email, password;
    private Button btnSignUp;
    private TextView signUp;
    private FirebaseAuth mFirebaseAuth;
    private FirebaseAuth.AuthStateListener mAuthStateListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        mFirebaseAuth = FirebaseAuth.getInstance();
        email = findViewById(R.id.emailtxt);
        password = findViewById(R.id.passwordTxt);
        btnSignUp = findViewById(R.id.loginBtn);
        signUp = findViewById(R.id.signupTxt);

        signUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(RegisterActivity.this, LoginActivity.class);
                startActivity(intent);
                finish();
            }
        });
        btnSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String uEmail = email.getText().toString().trim();
                String uPassword = email.getText().toString().trim();
                String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";

                if (uEmail.isEmpty() || uPassword.isEmpty()){
                    Toast.makeText(getApplicationContext(), "complete empty fields", Toast.LENGTH_LONG).show();
                }else if (!(uEmail.matches(emailPattern) && uEmail.length() > 0)){
                    Toast.makeText(getApplicationContext(), "Email error", Toast.LENGTH_LONG).show();
                }
                else if (uPassword.length() < 6){
                    Toast.makeText(getApplicationContext(), "Password too short! Minimun 6 characters", Toast.LENGTH_LONG).show();
                } else {
                    mFirebaseAuth.createUserWithEmailAndPassword(uEmail,uPassword)
                            .addOnCompleteListener(RegisterActivity.this, new OnCompleteListener<AuthResult>() {
                                @Override
                                public void onComplete(@NonNull Task<AuthResult> task) {
                                    if (task.isSuccessful()){
                                        Intent intent = new Intent(RegisterActivity.this, LoginActivity.class);
                                        startActivity(intent);
                                        finish();
                                    }else {
                                        Toast.makeText(getApplicationContext(), "error occured! Try again", Toast.LENGTH_LONG).show();
                                    }
                                }
                            });
                }
            }
        });

    }
}