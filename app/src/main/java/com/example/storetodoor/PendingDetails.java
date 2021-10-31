package com.example.storetodoor;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.firestore.WriteBatch;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class PendingDetails extends AppCompatActivity {
    FirebaseFirestore firebaseFirestore=FirebaseFirestore.getInstance();;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pending_details);


        ImageView groceItemPicture;
        TextView groceName,grocePrice, groceQuantity, groceUid, groceAddress;
        EditText modeofDelivary;
        Button submit, cancel;

        groceItemPicture = findViewById(R.id.grocepicture);
        groceName = findViewById(R.id.groceName);
        grocePrice = findViewById(R.id.grocePrice);
        groceQuantity = findViewById(R.id.groceQuantity);
        groceUid = findViewById(R.id.groceUid);
        groceAddress = findViewById(R.id.groceAddress);
        modeofDelivary = findViewById(R.id.modeOfDelivery);
        submit = findViewById(R.id.submit);
        cancel = findViewById(R.id.cancelPending);



        String img = getIntent().getStringExtra("imageUrl");

        Glide.with(getApplicationContext()).load(img).into(groceItemPicture);

        groceName.setText(getIntent().getStringExtra("nameofgrocery"));
        grocePrice.setText(getIntent().getStringExtra("price"));
        groceQuantity.setText(getIntent().getStringExtra("quantity"));
        groceUid.setText(getIntent().getStringExtra("userId"));
        groceAddress.setText(getIntent().getStringExtra("Address"));

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String mode = modeofDelivary.getText().toString().trim();

                if (TextUtils.isEmpty(mode)) {
                    modeofDelivary.setError("Please specify mode of delivery.");
                    return;
                }

                //====================================================================//

                Map<String, Object> UserDetails = new HashMap<>();

                UserDetails.put("nameofgrocery", groceName.getText().toString());
                UserDetails.put("imageUrl", img);
                UserDetails.put("price", grocePrice.getText().toString());
                UserDetails.put("quantity", groceQuantity.getText().toString());
                UserDetails.put("userId", groceUid.getText().toString());
                UserDetails.put("Address", groceAddress.getText().toString());
                UserDetails.put("modeOfDelivery", modeofDelivary.getText().toString());

                firebaseFirestore.collection("AdminViewedOrders").document().set(UserDetails)
                        .addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                Toast.makeText(PendingDetails.this, "Mode of delivary has been sent to Customer", Toast.LENGTH_SHORT).show();
                                Intent intent = new Intent(PendingDetails.this, Adminactivity.class);
                                startActivity(intent);
                            }
                        }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull  Exception e) {
                        Toast.makeText(PendingDetails.this, "Error Adding Data !!!", Toast.LENGTH_SHORT).show();
                    }
                });


                firebaseFirestore.collection("AddToCart").whereEqualTo("Status", "Ordered").whereEqualTo("userId",groceUid.getText().toString()).whereEqualTo("nameofgrocery",groceName.getText().toString()).get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
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
            }
        });

        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(PendingDetails.this, Adminactivity.class);
                startActivity(intent);
            }
        });
    }
    private void updateData(List<String> list) {
        WriteBatch batch = firebaseFirestore.batch();
        // Iterate through the list
        for (int k = 0; k < list.size(); k++) {
            // Update each list item
            DocumentReference ref = firebaseFirestore.collection("AddToCart").document(list.get(k));
            batch.update(ref,"Status","reaching soon" );;
        }


        batch.commit().addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {

            }
        });

    }
}