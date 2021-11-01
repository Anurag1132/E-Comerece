package com.example.storetodoor;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;



import java.util.ArrayList;
import java.util.List;

public class Chat_admin extends Fragment {

    FirebaseFirestore firestore;
    ArrayList<Pojo> addeddatalist;
    ChatAdapter userListAdapterObj;
    RecyclerView recview2;

    public Chat_admin() {
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
        return inflater.inflate(R.layout.fragment_chat_admin, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        firestore = FirebaseFirestore.getInstance();

        recview2 = view.findViewById(R.id.userList_addedPost);
        addeddatalist = new ArrayList<>();
        recview2.setLayoutManager(new LinearLayoutManager(getContext()));
        userListAdapterObj = new ChatAdapter(addeddatalist);
        recview2.setAdapter(userListAdapterObj);
        firestore.collection("Chat").get()
                .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                    @Override
                    public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                        List<DocumentSnapshot> list = queryDocumentSnapshots.getDocuments();
                        for (DocumentSnapshot d : list) {
                            Pojo obj = d.toObject(Pojo.class);
                            addeddatalist.add(obj);
                        }
                        userListAdapterObj.notifyDataSetChanged();
                    }
                });

    }
}