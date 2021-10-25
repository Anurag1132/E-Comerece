package com.example.storetodoor.fragments;



import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


import com.example.storetodoor.Dashboardadapter;
import com.example.storetodoor.Pojo;
import com.example.storetodoor.R;
import com.example.storetodoor.userAdapter;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;


public class user_listFragment extends Fragment  implements AdapterView.OnItemSelectedListener {
    FirebaseAuth firebaseAuth;
    FirebaseFirestore fireStore;
    TextView userlist_firstname;
    TextView userlist_lastname;
    TextView userlist_city;
    TextView userlist_mobile;
    EditText userlist_search_bar;
    RecyclerView recview;
    ArrayList<Pojo> userlist;
    userAdapter uadapter;
    Spinner userfilterSpinner;
    ArrayAdapter userType_arrayAdapter;
    FirebaseUser firebaseUser;

    //-------------------------------------
    ArrayList<Pojo> filtereduserList =  new ArrayList<>();
    String[] searchuserCategory = {"           Firstname", "           Lastname", "           City","           Mobile"};
    String selecteduserCategory ;

    public user_listFragment() {
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
        return inflater.inflate(R.layout.fragment_user_list, container, false);
    }
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        firebaseAuth = FirebaseAuth.getInstance();
        fireStore = FirebaseFirestore.getInstance();
        userlist_search_bar = view.findViewById(R.id.userlist_searchbar);
        NavigationView navigationView = getActivity().findViewById(R.id.navigationView);
        userlist_firstname = view.findViewById(R.id.user_list_firstname);
        userlist_lastname = view.findViewById(R.id.user_list_lastname);
        userlist_city = view.findViewById(R.id.user_list_city);
        userlist_mobile = view.findViewById(R.id.user_list_mobile);
        recview=view.findViewById(R.id.user_list_recycleView);
        userfilterSpinner = view.findViewById(R.id.user_spinner_filter);
        userfilterSpinner.setOnItemSelectedListener(this);
        recview.setLayoutManager(new LinearLayoutManager(view.getContext()));
        userlist=new ArrayList<>();
        uadapter=new userAdapter(userlist);
        recview.setAdapter(uadapter);
        firebaseUser = firebaseAuth.getCurrentUser();

        userType_arrayAdapter = new ArrayAdapter(getContext(), android.R.layout.simple_spinner_dropdown_item, searchuserCategory);
        userType_arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        userfilterSpinner.setAdapter(userType_arrayAdapter);
        userlist_search_bar.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {

                filtereduserList.clear();

                if(s.toString().isEmpty())
                {
                    recview.setAdapter(new userAdapter(userlist));
                    uadapter.notifyDataSetChanged();
                }
                else
                {
                    filter(s.toString());
                }

            }
        });


        fireStore.collection("ProfileUpdate").get()
                .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                    @Override
                    public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                        List<DocumentSnapshot> list=queryDocumentSnapshots.getDocuments();
                        for(DocumentSnapshot d:list)
                        {
                            Pojo obj=d.toObject(Pojo.class);
                            userlist.add(obj);
                            uadapter.notifyDataSetChanged();
                        }
                        userlist_search_bar.clearFocus();
                    }
                });

    }



    private void filter(String text) {

        for (Pojo model : userlist) {

            if (selecteduserCategory == searchuserCategory[0])
            {
                if (model.getFirstName().toLowerCase().contains(text.toLowerCase())) {
                    filtereduserList.add(model);
                }
            }
            else if (selecteduserCategory == searchuserCategory[1])
            {
                if (model.getLastName().toLowerCase().contains(text.toLowerCase())) {
                    filtereduserList.add(model);
                }
            }
            else if(selecteduserCategory == searchuserCategory[2])
            {
                if (model.getCity().toLowerCase().contains(text.toLowerCase())) {
                    filtereduserList.add(model);
                }
            }
            else if(selecteduserCategory == searchuserCategory[3])
            {
                if (model.getMobilenumber().toLowerCase().contains(text.toLowerCase())) {
                    filtereduserList.add(model);
                }
            }

            recview.setAdapter(new userAdapter(filtereduserList));
            uadapter.notifyDataSetChanged();
        }

    }
    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        selecteduserCategory = userfilterSpinner.getSelectedItem().toString();
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }

}