package com.example.nimmane.activity.Activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;


import com.basgeekball.awesomevalidation.AwesomeValidation;
import com.basgeekball.awesomevalidation.ValidationStyle;
import com.example.nimmane.R;
import com.example.nimmane.activity.Models.RentModel;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class UploadActivity extends AppCompatActivity {

    EditText city, district, advance, rent, address;
    Button post;
    private AwesomeValidation awesomeValidation;
    private DatabaseReference mDatabase;
    FirebaseAuth firebase_auth;
    String userId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_upload);

        // Get Firebase auth Instance
        FirebaseApp.initializeApp(this);
        firebase_auth = FirebaseAuth.getInstance();

        awesomeValidation = new AwesomeValidation(ValidationStyle.BASIC);
        mDatabase = FirebaseDatabase.getInstance().getReference();

        city = (EditText) findViewById(R.id.city);
        district = (EditText) findViewById(R.id.district);
        advance = (EditText) findViewById(R.id.advance);
        rent = (EditText) findViewById(R.id.rent);
        address = (EditText) findViewById(R.id.address);
        post = (Button)findViewById(R.id.post);
//        userId = firebase_auth.getCurrentUser().getUid();

        awesomeValidation.addValidation(city, "^(?!\\s*$).+", "Enter city name");
        awesomeValidation.addValidation(district, "^(?!\\s*$).+", "Enter District" );
        awesomeValidation.addValidation(advance, "^[1-9][0-9]*$", "Enter valid money");
        awesomeValidation.addValidation(rent, "^[1-9][0-9]*$", "Enter valid money");
        awesomeValidation.addValidation(address, "^(?!\\s*$).+", "Enter valid money");


        post.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                validateData();
            }
        });

    }

    public void validateData() {
        if(awesomeValidation.validate()) {
            writeData();
        }
    }

    public void writeData() {
        String key = mDatabase.child("rent_houses").push().getKey();
        RentModel rentModel = new RentModel(userId,
                city.getText().toString(),
                district.getText().toString(),
                Integer.parseInt((advance.getText().toString())),
                Integer.parseInt(rent.getText().toString()),
                new Date()
                );
        Map <String, Object> rentMap = rentModel.toMap();
        Map <String, Object> childUpdates = new HashMap<>();
        childUpdates.put("/rent_houses/" + key, rentMap);
        childUpdates.put("users/" + userId + "/uploads/" + key, rentMap);
        mDatabase.updateChildren(childUpdates).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                Toast.makeText(UploadActivity.this, "Uploaded successfully", Toast.LENGTH_SHORT).show();
                finish();
            }
        });
    }
}
