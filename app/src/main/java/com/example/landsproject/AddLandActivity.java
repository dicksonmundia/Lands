package com.example.landsproject;

        import androidx.annotation.NonNull;
        import androidx.annotation.Nullable;
        import androidx.appcompat.app.AppCompatActivity;

        import android.content.ContentResolver;
        import android.content.Intent;
        import android.net.Uri;
        import android.os.Bundle;
        import android.util.Log;
        import android.view.View;
        import android.webkit.MimeTypeMap;
        import android.widget.Button;
        import android.widget.EditText;
        import android.widget.ImageView;
        import android.widget.Toast;

        import com.google.android.gms.tasks.OnCompleteListener;
        import com.google.android.gms.tasks.OnFailureListener;
        import com.google.android.gms.tasks.Task;
        import com.google.firebase.database.DatabaseReference;
        import com.google.firebase.database.FirebaseDatabase;
        import com.google.firebase.storage.FirebaseStorage;
        import com.google.firebase.storage.StorageReference;
        import com.google.firebase.storage.UploadTask;

        import java.util.HashMap;
        import java.util.Map;

public class AddLandActivity extends AppCompatActivity {
    private static final int PICTURE_RESULTS = 42;
    private Uri imgUri;
    private EditText  ownerName, ownerNumber, description, price, status, landNumber, location;
    private String oName,descr,lstatus,lNumber,lLocation;
    private int oNumber,lPrice;
    private Button btnUploadImg, btnSubmit;
    private ImageView imageView;
    private StorageReference storageRef;
    private UploadTask uploadTask;
    private FirebaseDatabase database = FirebaseDatabase.getInstance();
    private DatabaseReference myDbRef = database.getReference("Lands");
    Land lang;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_land);
        Land l = lang;


        ownerName = findViewById(R.id.ownerNameEtxt);
        ownerNumber = findViewById(R.id.ownerNumberEtxt9);
        description = findViewById(R.id.descriptionEtxt);
        price = findViewById(R.id.priceEtxt);
        status = findViewById(R.id.statusEtxt);
        landNumber = findViewById(R.id.landNumberEtxt);
        location = findViewById(R.id.locationEtxt);
        btnSubmit = findViewById(R.id.submitBtn);
        btnUploadImg = findViewById(R.id.imgUploadbtn1);
        imageView = findViewById(R.id.uploadImageView);

        storageRef = FirebaseStorage.getInstance().getReference("Land_Image");


        btnUploadImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fileChoser();
            }
        });

        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                oName = ownerName.getText().toString().trim();
                oNumber = Integer.parseInt(ownerNumber.getText().toString().trim());
                descr = description.getText().toString().trim();
                lstatus =status.getText().toString().trim();
                lLocation = location.getText().toString().trim();
                lNumber = landNumber.getText().toString().trim();
                lPrice = Integer.parseInt(price.getText().toString().trim());
                String num = ownerNumber.getText().toString().trim();
                String prc = price.getText().toString().trim();


                if(oName.isEmpty() || num.isEmpty() || prc.isEmpty() || descr.isEmpty() || lstatus.isEmpty() || lLocation.isEmpty() ){
                    Toast.makeText(getApplicationContext(), "Fill in all Fields", Toast.LENGTH_LONG).show();
                }else if (num.length() !=10 ){
                    Toast.makeText(getApplicationContext(), "enter a valid phone number", Toast.LENGTH_LONG).show();
                }else if (imgUri == null){
                    Toast.makeText(getApplicationContext(), "You should upload an image to proceed", Toast.LENGTH_LONG).show();
                }else {
                    if (uploadTask != null && uploadTask.isInProgress()){
                        Toast.makeText(getApplicationContext(), "upload in progress", Toast.LENGTH_LONG).show();
                    }else {
                        Toast.makeText(getApplicationContext(), "hellow", Toast.LENGTH_LONG).show();
                    }
                }

                if(inputValidated()){
                    if (uploadTask != null && uploadTask.isInProgress()){
                        Toast.makeText(getApplicationContext(), "upload in progress", Toast.LENGTH_LONG).show();
                    }else {
                        Toast.makeText(getApplicationContext(), "hellow", Toast.LENGTH_LONG).show();
                    }

                }
            }
        });


    }
    private String getExtension(Uri uri){
        ContentResolver contentResolver = getContentResolver();
        MimeTypeMap mimeTypeMap = MimeTypeMap.getSingleton();
        return mimeTypeMap.getExtensionFromMimeType(contentResolver.getType(uri));
    }

    private void upLoadFile() {
        final StorageReference storeRef = storageRef.child(System.currentTimeMillis() + "." +getExtension(imgUri));
        Log.d("jsjsjjsjsjj", ""+imgUri);
         /uploadTask = storeRef.putFile(imgUri);
        if (imgUri != null){
            Task<Uri> uriTask = uploadTask.continueWithTask(new Continuation<UploadTask.TaskSnapshot, Task<Uri>>() {
                @Override
                public Task<Uri> then(@NonNull Task<UploadTask.TaskSnapshot> task) throws Exception {
                    if (!task.isSuccessful()){
                        throw task.getException();

                    }
                    return storeRef.getDownloadUrl();
                }
            }).addOnCompleteListener(new OnCompleteListener<Uri>() {
                @Override
                public void onComplete(@NonNull Task<Uri> task) {
                    if(task.isSuccessful()){
                        Uri downloadUri = task.getResult();
                        assert downloadUri != null;
                        String imageUrl = downloadUri.toString();
                        saveImage(imageUrl);
                    }
                }
            });
        }
    }

    private void saveImage(String imageUrl) {
        Map<String, Object> data = new HashMap<>();
        data.put("description", descr);
        data.put("imageUrl", imageUrl);
        data.put("ownerNumber", oNumber);
        data.put("status", "not purchased");
        data.put("location", lLocation);
        data.put("ownerName", oName);
        data.put("number", lNumber);
        data.put("price", lPrice);
        Task<Void> res = myDbRef.push().setValue(data);
        res.addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                Toast.makeText(getApplicationContext(), "file uploaded successfully", Toast.LENGTH_LONG).show();
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(getApplicationContext(), "file not uploaded", Toast.LENGTH_LONG).show();
            }
        });

    }

    private boolean inputValidated() {
        String num = ownerNumber.getText().toString().trim();
        String prc = price.getText().toString().trim();
        if(oName.isEmpty() || num.isEmpty() || prc.isEmpty() || descr.isEmpty() || lstatus.isEmpty() || lLocation.isEmpty() ){
            Toast.makeText(getApplicationContext(), "Fill in all Fields", Toast.LENGTH_LONG).show();
            return false;
        }else if (num.length() !=10 ){
            Toast.makeText(getApplicationContext(), "enter a valid phone number", Toast.LENGTH_LONG).show();
            return false;
        }else if (imgUri == null){
            Toast.makeText(getApplicationContext(), "You should upload an image to proceed", Toast.LENGTH_LONG).show();
            return false;
        }else {

            return true;
        }
    }

    private void fileChoser() {
        Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
        intent.setType("image/jpeg");
        intent.putExtra(Intent.EXTRA_LOCAL_ONLY, true);
        startActivityForResult(Intent.createChooser(intent,
                "insert picture"), 42);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK && requestCode == PICTURE_RESULTS && data != null){
            imgUri = data.getData();
            imageView.setImageURI(imgUri);
        }
    }
}