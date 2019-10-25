package com.example.eio.Fragments;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;


import com.example.eio.ViewHolder.RecyclerAdapter;
import com.example.eio.R;
import com.example.eio.Models.RentModel;

import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.List;

public class LeaseListFragment extends Fragment {

    private static final String TAG = "LeaseListFragment";

    TextView user_name;
    FirebaseAuth firebase_auth;
    DatabaseReference mDatabase;

    // [END define_database_reference]

    //    private FirebaseRecyclerAdapter<RentModel, RentViewHolder> mAdapter;
    RecyclerAdapter mAdapter;
    private RecyclerView mRecycler;
    private LinearLayoutManager mManager;
    List<RentModel> rent_list;

    public LeaseListFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        View rootView = inflater.inflate(R.layout.activity_home, container, false);

        // [START create_database_reference]
        mDatabase = FirebaseDatabase.getInstance().getReference();
        // [END create_database_reference]

        mRecycler = rootView.findViewById(R.id.rent_list);
        mRecycler.setHasFixedSize(true);


        return rootView;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        // Get Firebase auth Instance
        FirebaseApp.initializeApp(getActivity());
        firebase_auth = FirebaseAuth.getInstance();
        mDatabase = FirebaseDatabase.getInstance().getReference().child("rent_houses");

        mRecycler.setHasFixedSize(true);

        // Set up Layout Manager, reverse layout
        mManager = new LinearLayoutManager(getActivity());

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
        mAdapter = new RecyclerAdapter(rent_list, getActivity());
        mRecycler.setAdapter(mAdapter);
    }

    public void deleteAllRents(DataSnapshot dataSnapshot) {
        for(RentModel rentModel: rent_list) {
            if(rentModel.equals(dataSnapshot.getValue(RentModel.class))) {
                rent_list.remove(rentModel);
            }
        }
        mAdapter = new RecyclerAdapter(rent_list, getActivity());
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

    public String getUid() {
        return FirebaseAuth.getInstance().getCurrentUser().getUid();
    }

}

