package com.example.storetodoor;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Chat_user extends Fragment {

    TextView adminMessageUserside, userMessageUserside;
    ImageView sendMessageImage;
    EditText messageTypetextview;
    String msg;
    ArrayList<Pojo> datalist;
    FirebaseFirestore firebaseFirestore;
    FirebaseAuth firebaseAuth;
    String fuser;
    SwipeRefreshLayout swipeRefreshLayout;
    DocumentReference documentReference;

    public Chat_user() {
        // Required empty public constructor
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_chat, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable  Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        firebaseFirestore = FirebaseFirestore.getInstance();
        firebaseAuth = FirebaseAuth.getInstance();
        fuser = firebaseAuth.getCurrentUser().getEmail();
        datalist = new ArrayList<>();
        adminMessageUserside = view.findViewById(R.id.adminMessageOn_userSIde_textview);
        userMessageUserside = view.findViewById(R.id.userMessageOn_userSIde_textview);
        sendMessageImage = view.findViewById(R.id.sendMessage_imgview);
        messageTypetextview = view.findViewById(R.id.messageSend_edttext);
        swipeRefreshLayout = view.findViewById(R.id.swipeRefreshUser);


        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {


                Toast.makeText(getActivity(), "Messages updated!", Toast.LENGTH_SHORT).show();

                swipeRefreshLayout.setRefreshing(false);
            }
        });
        documentReference =firebaseFirestore.collection("Chat").document(fuser);
        documentReference.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {

                if (task.isSuccessful()) {
                    String adminmessage = task.getResult().getString("AdminMessage");
                    String usermessage=task.getResult().getString("Message");
                    adminMessageUserside.setText(adminmessage);
                    userMessageUserside.setText(usermessage);

                } else {
                    Toast.makeText(getActivity().getApplicationContext(), "No Message", Toast.LENGTH_SHORT).show();
                }


            }
        });


        sendMessageImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (TextUtils.isEmpty( messageTypetextview.getText().toString().trim()))
                {
                    messageTypetextview.setError("Please enter your message");
                    return;
                }
                insertUserData(messageTypetextview.getText().toString(),fuser);

                userMessageUserside.setText(messageTypetextview.getText().toString());
                messageTypetextview.setText("");

            }
        });
    }

    private void insertUserData(String msg,String fuser) {
        Map<String, Object> Chatdata = new HashMap<>();
        Chatdata.put("Message", msg);
        Chatdata.put("userId", fuser);
        Chatdata.put("AdminMessage","1");

        firebaseFirestore.collection("Chat").document(fuser).set(Chatdata)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        //Toast.makeText(getContext().getApplicationContext(),"Data inserted successfully",Toast.LENGTH_LONG).show();
                    }
                }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                // Toast.makeText(getActivity().getApplicationContext(), "Error adding data" + e.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

}