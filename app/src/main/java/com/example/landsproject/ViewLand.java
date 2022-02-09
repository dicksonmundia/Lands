package com.example.landsproject;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

public class ViewLand extends AppCompatActivity {
    private TextView name,number,status,descr,price,location;
    private ImageView imageView;
    private Land land = null;
    private Button purchase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_land);

        name = findViewById(R.id.name);
        number = findViewById(R.id.number);
        status = findViewById(R.id.statusEtxt);
        descr = findViewById(R.id.descr);
        price = findViewById(R.id.priceEtxt);
        location = findViewById(R.id.locationEtxt);
        imageView = findViewById(R.id.imageViewL);
        purchase = findViewById(R.id.purchaseBtn);


        final Intent intent = getIntent();
        land = (Land) intent.getSerializableExtra("land_details");

        name.setText(land.getOwnerName());
        number.setText(land.getOwnerNumber());
        status.setText(land.getStatus());
        descr.setText(land.getDescription());
        price.setText(land.getPrice());
        location.setText(land.getLocation());
        showImage(land.getImageUrl());

        purchase.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent1 = new Intent(ViewLand.this, PurchaseLand.class);
                intent1.putExtra("land_details", land);
                startActivity(intent1);
            }
        });


    }

    private void showImage(String imageUrl) {
        Glide.with(this)
                .load(imageUrl)
                .centerCrop()
                .placeholder(R.drawable.placeholder_image)
                .into(imageView);

    }
}