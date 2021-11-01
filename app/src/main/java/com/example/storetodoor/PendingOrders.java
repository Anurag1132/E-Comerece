package com.example.storetodoor;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;



import java.util.ArrayList;
import java.util.List;

public class PendingOrders extends Fragment {

    FirebaseAuth firebaseAuth;
    FirebaseFirestore firestore;
    FirebaseUser firebaseUser;

    TextView nameofGrocery;
    TextView userID;
    TextView quantity;
    ImageView itemPicture;
    RecyclerView recview;

    ArrayList<Pojo> pendingList;
    PendingAdapter pendingAdapter;

    AlertDialog.Builder builder;


    public PendingOrders() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }


    @Override
    public void onViewCreated(@NonNull  View view, @Nullable  Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        firebaseAuth = FirebaseAuth.getInstance();
        firestore = FirebaseFirestore.getInstance();

        userID = view.findViewById(R.id.userId);
        nameofGrocery = view.findViewById(R.id.nameofGroce);
        quantity = view.findViewById(R.id.Quantity);
        itemPicture = view.findViewById(R.id.itempicture);

        NavigationView navigationView = getActivity().findViewById(R.id.navigationView);

        recview=view.findViewById(R.id.pendingorder_recyclerview);
        recview.setLayoutManager(new LinearLayoutManager(view.getContext()));

        pendingList=new ArrayList<>();
        pendingAdapter= new PendingAdapter(pendingList);
        recview.setAdapter(pendingAdapter);
        firebaseUser = firebaseAuth.getCurrentUser();

        firestore.collection("AddToCart").whereEqualTo("Status","Ordered").get()
                .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                    @Override
                    public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                        List<DocumentSnapshot> list = queryDocumentSnapshots.getDocuments();
                        for (DocumentSnapshot d : list) {

                            Pojo obj = d.toObject(Pojo.class);
                            pendingList.add(obj);
                            pendingAdapter.notifyDataSetChanged();
                        }


                        if(pendingList.isEmpty())
                        {
                            builder = new AlertDialog.Builder(getActivity());

                            //Setting message manually and performing action on button click
                            builder.setMessage("Currently, there is no orders added.")
                                    .setCancelable(false)
                                    .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                                        public void onClick(DialogInterface dialog, int id) {

                                            Toast.makeText(getActivity(), "You are redirected to the Home screen.",
                                                    Toast.LENGTH_SHORT).show();
                                            Intent i= new Intent(getContext(), Adminactivity.class);
                                            startActivity(i);
                                        }
                                    });

                            //Creating dialog box
                            AlertDialog alert = builder.create();
                            //Setting the title manually
                            alert.setTitle("Pending Posts");
                            alert.show();
                        }

                    }
                });


    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_pending_orders, container, false);
    }
}