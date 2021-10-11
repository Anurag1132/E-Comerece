package com.example.storetodoor.fragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;

import com.example.storetodoor.Dashboardadapter;
import com.example.storetodoor.Pojo;
import com.example.storetodoor.R;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;
import java.util.ArrayList;
import java.util.List;


public class UserDashboardFragment extends Fragment implements AdapterView.OnItemSelectedListener {

    EditText search_bar;
    Spinner filterSpinner;
    RecyclerView recview;
    ArrayList<Pojo> datalist;
    String[] searchCategory = {"Location", "Grossory Name"};
    String selectedCategory ;
    ArrayAdapter searchAdapter;
    Dashboardadapter adapter;
    FirebaseFirestore firestore= FirebaseFirestore.getInstance();

    public UserDashboardFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_user_dashboard, container, false);
    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        search_bar = view.findViewById(R.id.searchBar_dashboard);
        filterSpinner = view.findViewById(R.id.spinner_filter);
        filterSpinner.setOnItemSelectedListener(this);
        searchAdapter = new ArrayAdapter(getContext(), android.R.layout.simple_spinner_dropdown_item, searchCategory);
        searchAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        filterSpinner.setAdapter(searchAdapter);

        recview=view.findViewById(R.id.dashboard_recycleView);
        recview.setLayoutManager(new LinearLayoutManager(view.getContext()));
        datalist=new ArrayList<>();
        adapter=new Dashboardadapter(datalist);
        recview.setAdapter(adapter);


        firestore.collection("Items").get()
                .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                    @Override
                    public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                        List<DocumentSnapshot> list=queryDocumentSnapshots.getDocuments();
                        for(DocumentSnapshot d:list)
                        {
                            Pojo obj=d.toObject(Pojo.class);
                            datalist.add(obj);
                            adapter.notifyDataSetChanged();
                        }
                        search_bar.clearFocus();
                    }
                });

    }



    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

    }


    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}