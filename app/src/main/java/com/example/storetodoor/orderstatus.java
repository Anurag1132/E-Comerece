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
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

public class orderstatus extends Fragment {

    FirebaseAuth firebaseAuth;
    FirebaseFirestore firestore;
    String firebaseUser;

    TextView nameofGrocery;
    TextView userID;
    TextView quantity;
    ImageView itemPicture;
    RecyclerView recview;

    ArrayList<Pojo> orderStatusList;
    OrderStatusAdapter orderStatusAdapter;

    AlertDialog.Builder builder;


    public orderstatus() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }


    @Override
    public void onViewCreated(@NonNull @NotNull View view, @Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {

        firebaseAuth = FirebaseAuth.getInstance();
        firestore = FirebaseFirestore.getInstance();
        firebaseUser = firebaseAuth.getCurrentUser().getEmail();

        userID = view.findViewById(R.id.userId1);
        nameofGrocery = view.findViewById(R.id.nameofGroce1);
        quantity = view.findViewById(R.id.Quantity1);
        itemPicture = view.findViewById(R.id.itempicture1);

        recview=view.findViewById(R.id.orderstatus_recyclerView);
        recview.setLayoutManager(new LinearLayoutManager(view.getContext()));

        orderStatusList=new ArrayList<>();
        orderStatusAdapter = new OrderStatusAdapter(orderStatusList);
        recview.setAdapter(orderStatusAdapter);

        firestore.collection("AdminViewedOrders").whereEqualTo("Email",firebaseUser).get()
                .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                    @Override
                    public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                        List<DocumentSnapshot> list = queryDocumentSnapshots.getDocuments();
                        for (DocumentSnapshot d : list) {

                            Pojo obj = d.toObject(Pojo.class);
                            orderStatusList.add(obj);
                            orderStatusAdapter.notifyDataSetChanged();
                        }


                        if(orderStatusList.isEmpty())
                        {
                            builder = new AlertDialog.Builder(getActivity());

                            //Setting message manually and performing action on button click
                            builder.setMessage("Currently, there are no orders.")
                                    .setCancelable(false)
                                    .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                                        public void onClick(DialogInterface dialog, int id) {

                                            Toast.makeText(getActivity(), "You are redirected to the Home screen.",
                                                    Toast.LENGTH_SHORT).show();
                                            Intent i= new Intent(getContext(), UserActivity.class);
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
        return inflater.inflate(R.layout.fragment_orderstatus, container, false);
    }
}