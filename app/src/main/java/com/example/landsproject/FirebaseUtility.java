package com.example.landsproject;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.ArrayList;

public class FirebaseUtility {
    public static FirebaseDatabase firebaseDatabase;
    public static FirebaseStorage imageStore;
    public static StorageReference imageRef;
    public static DatabaseReference mRef;
    public static FirebaseUtility firebaseUtil;
    public static ArrayList<Land> landArrayList;
    private FirebaseUtility (){}

    public static void getDatabaseData(String DBRef){
        if (firebaseUtil == null) {
            firebaseUtil = new FirebaseUtility();
            firebaseDatabase = FirebaseDatabase.getInstance();
            getImageData();
        }
        landArrayList = new ArrayList<Land>();
        mRef = firebaseDatabase.getReference().child(DBRef);
    }

    private static void getImageData() {
        imageStore = FirebaseStorage.getInstance();
        imageRef = imageStore.getReference().child("Image");
    }

}
