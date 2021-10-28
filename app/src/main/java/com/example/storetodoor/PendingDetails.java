package com.example.storetodoor;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
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
import com.google.firebase.firestore.FirebaseFirestore;

import org.jetbrains.annotations.NotNull;

import java.util.HashMap;
import java.util.Map;

public class PendingDetails extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pending_details);

        FirebaseFirestore firebaseFirestore;


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

        firebaseFirestore = FirebaseFirestore.getInstance();

        String img = getIntent().getStringExtra("ImageUrl");

        Glide.with(getApplicationContext()).load(img).into(groceItemPicture);

        groceName.setText(getIntent().getStringExtra("nameofgrocery"));
        grocePrice.setText(getIntent().getStringExtra("price"));
        groceQuantity.setText(getIntent().getStringExtra("qunty"));
        groceUid.setText(getIntent().getStringExtra("email"));
        groceAddress.setText(getIntent().getStringExtra("address"));

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String mode = modeofDelivary.getText().toString().trim();

                if (TextUtils.isEmpty(mode))
                {
                    modeofDelivary.setError("Please specify mode of delivery.");
                    return;
                }

                //====================================================================//

                Map<String, Object> UserDetails = new HashMap<>();

                UserDetails.put("NameOfGrocery", groceName.getText().toString());
                UserDetails.put("ImgUrl", img );
                UserDetails.put("price", grocePrice.getText().toString());
                UserDetails.put("Quantity", groceQuantity.getText().toString());
                UserDetails.put("Email", groceUid.getText().toString());
                UserDetails.put("Address", groceAddress.getText().toString());
                UserDetails.put("modeOfDelivery",modeofDelivary.getText().toString());

                firebaseFirestore.collection("AdminViewedOrders").document().set(UserDetails)
                        .addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull @NotNull Task<Void> task) {
                                Toast.makeText(PendingDetails.this, "Mode of delivary has been sent to Customer", Toast.LENGTH_SHORT).show();
                                Intent intent = new Intent(PendingDetails.this, Adminactivity.class);
                                startActivity(intent);
                            }
                        }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull @NotNull Exception e) {
                        Toast.makeText(PendingDetails.this, "Error Adding Data !!!", Toast.LENGTH_SHORT).show();
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
}