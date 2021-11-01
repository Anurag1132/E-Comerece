package com.example.storetodoor;

import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.firestore.WriteBatch;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

public class Adminchatdetails extends AppCompatActivity {

    TextView adminMessageUserside, userMessageUserside;
    ImageView sendMessageImage;
    EditText messageTypetextview;
    FirebaseFirestore firebaseFirestore;
    FirebaseAuth firebaseAuth;
    String fuser,message,adminmessage;
    SwipeRefreshLayout swipeRefreshLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_adminchatdetails);
        firebaseFirestore = FirebaseFirestore.getInstance();
        firebaseAuth = FirebaseAuth.getInstance();
        fuser = getIntent().getStringExtra("userId");
        message=getIntent().getStringExtra("Message");
        adminMessageUserside = findViewById(R.id.adminMessageOn_AdminSIde_textview);
        userMessageUserside = findViewById(R.id.userMessageOn_AdminSIde_textview);
        sendMessageImage = findViewById(R.id.sendMessage_imgview_admin);
        messageTypetextview = findViewById(R.id.messageSend_edttext_admin);
        swipeRefreshLayout = findViewById(R.id.swipeRefreshAdmin);
        messageTypetextview.setText(getIntent().getStringExtra("AdminMessage"));
        userMessageUserside.setText(message);

        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {

                Toast.makeText(getApplicationContext(), "Messages updated!", Toast.LENGTH_SHORT).show();
                swipeRefreshLayout.setRefreshing(false);
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

                adminmessage=messageTypetextview.getText().toString();
                firebaseFirestore.collection("Chat").whereEqualTo("userId", fuser).get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            List<String> list = new ArrayList<>();
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                Log.d("", document.getId());
                                list.add(document.getId());
                            }
                            updateData(list);
                        }
                    }
                });

                adminMessageUserside.setText(adminmessage);
                messageTypetextview.setText("");


            }
        });
    }
    private void updateData(List<String> list) {
        WriteBatch batch = firebaseFirestore.batch();
        // Iterate through the list
        for (int k = 0; k < list.size(); k++) {
            // Update each list item
            DocumentReference ref = firebaseFirestore.collection("Chat").document(list.get(k));
            batch.update(ref, "AdminMessage", adminmessage);
        }
        batch.commit().addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {

            }
        });

    }
}
