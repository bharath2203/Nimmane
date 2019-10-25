package com.example.eio.Activity;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.TextView;

import com.example.eio.R;
import com.example.eio.Models.RentModel;
import com.example.eio.ViewHolder.RecyclerAdapter;
import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;

import java.util.ArrayList;
import java.util.List;

public class HomeActivity extends AppCompatActivity {

    TextView user_name;
    FirebaseAuth firebase_auth;
    DatabaseReference mDatabase;

    // [END define_database_reference]

    RecyclerAdapter mAdapter;
    private RecyclerView mRecycler;
    private LinearLayoutManager mManager;
    List<RentModel> rent_list;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        // Get Firebase auth Instance
        FirebaseApp.initializeApp(this);
        firebase_auth = FirebaseAuth.getInstance();
        mDatabase = FirebaseDatabase.getInstance().getReference().child("rent_houses");

        mRecycler = findViewById(R.id.rent_list);
        mRecycler.setHasFixedSize(true);

        // Set up Layout Manager, reverse layout
        mManager = new LinearLayoutManager(this);

        rent_list = new ArrayList<>();
        mRecycler.setLayoutManager(mManager);

        firebaseAddListener();

    }


    public void firebaseAddListener() {
        mDatabase.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                getAllRents(dataSnapshot);
            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                getAllRents(dataSnapshot);
            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot dataSnapshot) {
                deleteAllRents(dataSnapshot);
            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    public void getAllRents(DataSnapshot dataSnapshot) {
        rent_list.add(dataSnapshot.getValue(RentModel.class));
        mAdapter = new RecyclerAdapter(rent_list, this);
        mRecycler.setAdapter(mAdapter);
    }

    public void deleteAllRents(DataSnapshot dataSnapshot) {
        for(RentModel rentModel: rent_list) {
            if(rentModel.equals(dataSnapshot.getValue(RentModel.class))) {
                rent_list.remove(rentModel);
            }
        }
        mAdapter = new RecyclerAdapter(rent_list, this);
        mRecycler.setAdapter(mAdapter);
    }

    @Override
    public void onStart() {
        super.onStart();

    }

    @Override
    public void onStop() {
        super.onStop();

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if(mAdapter != null) {
            mRecycler.setAdapter(null);
        }
    }

    public String getUid() {
        return FirebaseAuth.getInstance().getCurrentUser().getUid();
    }

    public Query getQuery(DatabaseReference databaseReference) {
        Query rent_list_query = databaseReference.child("rent_houses");
        return rent_list_query;
    }


}
