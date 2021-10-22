package com.example.storetodoor.fragments;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.storetodoor.Dashboardadapter;
import com.example.storetodoor.Pojo;
import com.example.storetodoor.R;
import com.example.storetodoor.admindashadapter;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;


import java.util.ArrayList;
import java.util.List;


public class AdmindashboardFragment extends Fragment implements AdapterView.OnItemSelectedListener {


    EditText search_bar;
    RecyclerView recview;
    ArrayList<Pojo> datalist;
    Spinner filterSpinner;
    ArrayAdapter searchAdapter;

    //-------------------------------------------------------------------------------------//
    ArrayList<Pojo> filteredList =  new ArrayList<>();
    String[] searchCategory = {"           Item Price", "           Item Category"};
    String selectedCategory;
    //------------------------------------------------------------------------------------//


    admindashadapter adapter;
    FirebaseFirestore firestore = FirebaseFirestore.getInstance();

    public AdmindashboardFragment() {
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
        return inflater.inflate(R.layout.fragment_admindashboard, container, false);
    }

    @Override
    public void onViewCreated( View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        search_bar = view.findViewById(R.id.searchBar_dashboard1);;
        filterSpinner = view.findViewById(R.id.spinner_filter);
        filterSpinner.setOnItemSelectedListener(this);
        recview = view.findViewById(R.id.adminhomerecyclerView);
        recview.setLayoutManager(new GridLayoutManager(view.getContext(), 3));
        datalist = new ArrayList<>();
        adapter = new admindashadapter(datalist);
        recview.setAdapter(adapter);

        //----------------------------------------------------------------
        searchAdapter = new ArrayAdapter(getContext(), android.R.layout.simple_spinner_dropdown_item, searchCategory);
        searchAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        filterSpinner.setAdapter(searchAdapter);
        //----------------------------------------------------------------
        search_bar.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {


                filteredList.clear();

                if(s.toString().isEmpty())
                {
                    recview.setAdapter(new Dashboardadapter(datalist));
                    adapter.notifyDataSetChanged();
                }
                else
                {
                    filter(s.toString());
                }

            }
        });




        firestore.collection("Items").get()
                .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                    @Override
                    public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                        List<DocumentSnapshot> list = queryDocumentSnapshots.getDocuments();
                        for (DocumentSnapshot d : list) {
                            Pojo obj = d.toObject(Pojo.class);
                            datalist.add(obj);
                            adapter.notifyDataSetChanged();
                        }
                        search_bar.clearFocus();
                    }
                });

    }
    private void filter(String text) {

        for (Pojo model : datalist) {

            if (selectedCategory == searchCategory[1])
            {
                if (model.getPrice().toLowerCase().contains(text.toLowerCase())) {
                    filteredList.add(model);

                }
            }
            else if(selectedCategory == searchCategory[2])
            {
                if (model.getCategory().toLowerCase().contains(text.toLowerCase())) {
                    filteredList.add(model);
                }
            }
            else
            {
                Toast.makeText(getContext(), "Please select Category from the filter option.", Toast.LENGTH_SHORT).show();
            }
            recview.setAdapter(new Dashboardadapter(filteredList));
            adapter.notifyDataSetChanged();
        }

    }


    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        selectedCategory = filterSpinner.getSelectedItem().toString();
    }


    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}

