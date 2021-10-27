package com.example.storetodoor.fragments;

import android.os.Bundle;


import androidx.fragment.app.Fragment;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.storetodoor.MyOrderAdapter;
import com.example.storetodoor.Pojo;
import com.example.storetodoor.R;
import com.google.android.gms.tasks.OnSuccessListener;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;


public class MyOrder extends Fragment {
    private ArrayList<Pojo> pojoDetails;
    RecyclerView listView;
    FirebaseAuth mAuth = FirebaseAuth.getInstance();
    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private MyOrderAdapter adapter;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState)
    {
        View root = inflater.inflate(R.layout.fragment_my_order, container, false);
        listView = root.findViewById(R.id.myorderlist);
        pojoDetails = new ArrayList<Pojo>();
        listView.setLayoutManager(new LinearLayoutManager(getContext()));
        adapter = new MyOrderAdapter(pojoDetails);
        listView.setAdapter(adapter);



        db.collection("AddToCart").whereEqualTo("userId", mAuth.getCurrentUser().getEmail()).whereEqualTo("Status","Ordered").get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
            @Override
            public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                if (!queryDocumentSnapshots.isEmpty()) {
                    List<DocumentSnapshot> list = queryDocumentSnapshots.getDocuments();
                    for (DocumentSnapshot d : list) {
                        Pojo pDs = d.toObject(Pojo.class);

                        pDs.setId(d.getId());

                        pojoDetails.add(pDs);
                    }
                    adapter.notifyDataSetChanged();
                }
            }
        });

        return  root;

    }
}