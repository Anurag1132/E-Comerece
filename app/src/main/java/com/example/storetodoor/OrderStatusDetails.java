package com.example.storetodoor;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.google.firebase.firestore.FirebaseFirestore;

public class OrderStatusDetails extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_status_details);

        FirebaseFirestore firebaseFirestore;


        ImageView groceItemPicture;
        TextView groceName,grocePrice, groceQuantity, groceUid, groceAddress, modeofDelivary;
        Button cancel;

        groceItemPicture = findViewById(R.id.grocepicture1);
        groceName = findViewById(R.id.groceName1);
        grocePrice = findViewById(R.id.grocePrice1);
        groceQuantity = findViewById(R.id.groceQuantity1);
        groceUid = findViewById(R.id.groceUid1);
        groceAddress = findViewById(R.id.groceAddress1);
        modeofDelivary = findViewById(R.id.modeOfDelivery1);
        cancel = findViewById(R.id.cancelPending1);

        firebaseFirestore = FirebaseFirestore.getInstance();

        String img = getIntent().getStringExtra("ImageUrl");

        Glide.with(getApplicationContext()).load(img).into(groceItemPicture);

        groceName.setText(getIntent().getStringExtra("nameofgrocery"));
        grocePrice.setText(getIntent().getStringExtra("price"));
        groceQuantity.setText(getIntent().getStringExtra("qunty"));
        groceUid.setText(getIntent().getStringExtra("email"));
        groceAddress.setText(getIntent().getStringExtra("address"));

        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(OrderStatusDetails.this, UserActivity.class);
                startActivity(intent);
            }
        });


    }
}