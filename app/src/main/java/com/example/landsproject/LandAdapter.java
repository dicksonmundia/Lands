package com.example.landsproject;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

public class LandAdapter extends RecyclerView.Adapter<LandAdapter.LandViewHolder> {
    private ArrayList<Land> landArrayList;
    private FirebaseDatabase mFirebaseDatabase;
    private DatabaseReference mDatabaseReference;
    private ChildEventListener mChildListener;
    private Context context;

    public LandAdapter (Context context){
        this.context = context;
        FirebaseUtility.getDatabaseData("Lands");
        landArrayList = FirebaseUtility.landArrayList;
        mFirebaseDatabase = FirebaseUtility.firebaseDatabase;
        mDatabaseReference = FirebaseUtility.mRef;

        mChildListener = new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                Land land = snapshot.getValue(Land.class);
                land.setLandId(snapshot.getKey());
                landArrayList.add(land);
                notifyItemInserted(landArrayList.size() + 1);

            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot snapshot) {

            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        };

    }


    @NonNull
    @Override
    public LandViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        View singleItemView = LayoutInflater.from(context).inflate(R.layout.single_view_item, parent, false);
        return new  LandViewHolder(singleItemView);

    }

    @Override
    public void onBindViewHolder(@NonNull LandViewHolder holder, int position) {
        Land land = landArrayList.get(position);
        holder.bind(land);
    }

    @Override
    public int getItemCount() {
        if (landArrayList.size() > 0){
            return landArrayList.size();
        }
        return 0;
    }


    public class LandViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView landLocation, landStatus,landPrice;
        ImageView imageView;

        public LandViewHolder(@NonNull View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.landImageView);
            landLocation = itemView.findViewById(R.id.landLocationS);
            landStatus = itemView.findViewById(R.id.landSStatus);
            landPrice = itemView.findViewById(R.id.landPrice);
            itemView.setOnClickListener(this);
        }

        public void bind(Land landDeal){
            landLocation.setText(landDeal.getLocation());
            landStatus.setText(landDeal.getStatus());
            landPrice.setText(landDeal.getPrice());
            displayImage(landDeal.getImageUrl());
        }

        private void displayImage(String imageUrl) {
            Glide.with(context)
                    .load(imageUrl)
                    .centerCrop()
                    .placeholder(R.drawable.placeholder_image)
                    .into(imageView);

        }

        @Override
        public void onClick(View v) {
            int position = getAbsoluteAdapterPosition();
            Land land = landArrayList.get(position);
            Intent intent = new Intent(v.getContext(), ViewLand.class);
            intent.putExtra("land_details", land);
            v.getContext().startActivity(intent);
        }
    }
}
