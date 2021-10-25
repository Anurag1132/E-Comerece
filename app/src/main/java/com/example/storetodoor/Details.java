package com.example.storetodoor;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.auth.User;

import java.util.HashMap;
import java.util.Map;

public class Details extends AppCompatActivity {
    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    FirebaseAuth mAuth;
    private  TextView textDescription,textprice,quantity, textname;
    int quantt;
    String price;
    double totall;
    String quant,ttt;
    Button cancel,cart;

    CollectionReference reference ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);

       /* getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);*/


        mAuth = FirebaseAuth.getInstance();
        final String userId = mAuth.getCurrentUser().getEmail();

        cancel=findViewById(R.id.cancel);
        cart=findViewById(R.id.cart);
        /*price= (String) getIntent().getExtras().get("price");*/

        textname = (TextView) findViewById(R.id.textname);
        textprice = (TextView) findViewById(R.id.textprice);
        ImageView imageitem=findViewById(R.id.imageItem);
        quantity=findViewById(R.id.textQuantity);
        Button add=findViewById(R.id.add);
        Button sub=findViewById(R.id.sub);
        price=getIntent().getStringExtra("price");
        textDescription = (TextView) findViewById(R.id.description);
        textname.setText(getIntent().getStringExtra("nameofgrocery"));
        textprice.setText(getIntent().getStringExtra("price"));
        textDescription.setText(getIntent().getStringExtra("Description"));
        String img = getIntent().getStringExtra("ImageUrl");

        Glide.with(getApplicationContext())
                .load(img).into(imageitem);



        Double temp=Double.parseDouble(price);
        textprice.setText("$"+temp);


        quant= quantity.getText().toString();
        quantt = Integer.parseInt(quant);


        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i= new Intent(Details.this,UserActivity.class);
                startActivity(i);
            }
        });

        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(quantt<=49) {
                    int a= quantt+1;
                    String qqq=String.valueOf(a);
                    quantity.setText(qqq);
                    totall = Double.parseDouble(price)*a;
                    ttt=String.valueOf(totall);
                    textprice.setText("$"+ttt);
                    //  Toast.makeText(Details.this,"quantt"+a,Toast.LENGTH_SHORT).show();
                }
                else if (quantt == 50)

                {
                    AlertDialog.Builder builder = new AlertDialog.Builder(Details.this);
                    builder.setMessage("The Quantity of item must not be more than 50")
                            .setCancelable(false)
                            .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int id) {

                                }
                            });
                    AlertDialog alert = builder.create();
                    alert.show();
                    return;
                }
                else {
                    return;
                }

                quantt= quantt+1;
            }
        });


        sub.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(quantt>1) {
                    int x = quantt - 1;
                    String qqq = String.valueOf(x);
                    quantity.setText(qqq);
                    totall = Double.parseDouble(price) * x;
                    String ttt = String.valueOf(totall);
                    textprice.setText("$"+ttt);
                }

                else if (quantt==1){

                    AlertDialog.Builder builder = new AlertDialog.Builder(Details.this);
                    builder.setMessage("The Quantity of item must not be less than 1")
                            .setCancelable(false)
                            .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int id) {

                                }
                            });
                    AlertDialog alert = builder.create();
                    alert.show();
                    return;

                }
                else {
                    return;
                }
                quantt=quantt-1;

            }
        });



        //add to cart
        cart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String itemname=textname.getText().toString();
                String price=textprice.getText().toString();
                String quantuu=quantity.getText().toString();
                String des=textDescription.getText().toString();
                String status="Cart";
                String totalprice="";
                String address="";
                //  if (!hasValidationErrors(name, price, quantuu, des, status)) {

                reference = db.collection("AddToCart");

                insertItemData(itemname,price,quantuu,des,status,totalprice,address,userId);
                //finish();


                Intent i=new Intent(Details.this, UserActivity.class);
                startActivity(i);

            }


        });


    }

    private void insertItemData(String itemname, String price, String quantuu, String des, String status, String totalprice, String address,String email) {
        Map<String, Object> ItemDetails = new HashMap<>();

        ItemDetails.put("quantity", quantuu);
        ItemDetails.put("Description", des);
        ItemDetails.put("nameofgrocery", itemname);
        ItemDetails.put("Status", status);
        ItemDetails.put("price",price);
        ItemDetails.put("TotalPrice",totalprice);
        ItemDetails.put("Address", address);
        ItemDetails.put("userId", email);


        db.collection("AddToCart").document().set(ItemDetails)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {

                        Toast.makeText(getApplication(), "Data inserted successfully", Toast.LENGTH_LONG).show();
                    }
                }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(getApplication(), "Error adding data" + e.getMessage(), Toast.LENGTH_SHORT).show();

            }
        });
    }
}