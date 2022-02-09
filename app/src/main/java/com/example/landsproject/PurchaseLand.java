package com.example.landsproject;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;
import java.util.Map;

public class PurchaseLand extends AppCompatActivity {
    private EditText customerName, phoneNumber;
    private Button button;
    private String name, phone, landNumber, landId, customerId;
    private Land land = null;
    private FirebaseAuth mFirebaseAuth;
    private FirebaseAuth.AuthStateListener mAuthStateListener;
    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference myRef = database.getReference("Lands");
    DatabaseReference newRef = database.getReference("Bought_Land");


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_purchase_land);


        customerName = findViewById(R.id.customerName);
        phoneNumber = findViewById(R.id.phoneNumber);

        button = findViewById(R.id.purchaseBtn);
        Intent intent = getIntent();
        land = (Land) intent.getSerializableExtra("land_details");
        landNumber = land.getNumber();
        landId = land.getLandId();

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                 name = customerName.getText().toString().trim();
                 phone = phoneNumber.getText().toString().trim();
                if(name.isEmpty() || phone.isEmpty()){
                    Toast.makeText(getApplicationContext(), "fill  all fields", Toast.LENGTH_LONG).show();
                }else if (phone.length() != 10){
                    Toast.makeText(getApplicationContext(), "invalid phone number", Toast.LENGTH_LONG);
                }else {
                    purchase();
                }
            }
        });
    }

    private void purchase() {
        mAuthStateListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser mFirebaseUser = mFirebaseAuth.getCurrentUser();
                if (mFirebaseUser != null) {
                    Intent intent = new Intent(PurchaseLand.this, LoginActivity.class);

                    startActivity(intent);
                }else {
                    customerId = mFirebaseUser.getUid();
                    purchaseLand(customerId);
                }

            }
        };
    }

    private void purchaseLand(String customerId) {
        Map<String,String> land = new HashMap<>();
        land.put("customer_id", customerId);
        land.put("customerName", name);
        land.put("customerNumber", phone);
        land.put("landNumber", landNumber);
        newRef.child("Bought").push().setValue(land)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()){

                            Map<String, Object> updateLandStatus = new HashMap<>();
                            updateLandStatus.put("status", "purchased");
                            myRef.child(landId).updateChildren(updateLandStatus);
                            Toast.makeText(getApplicationContext(), "land purchased successfully", Toast.LENGTH_LONG).show();
                            Intent intent = new Intent(PurchaseLand.this, LandsActivity.class);
                            startActivity(intent);
                            finish();
                        }
                    }
                });
    }
}