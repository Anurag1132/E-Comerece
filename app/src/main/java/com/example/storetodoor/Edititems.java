package com.example.storetodoor;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
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

public class Edititems extends AppCompatActivity {
    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    FirebaseAuth mAuth;
    EditText textDescription,textprice, textname,textcategory;
    String itemname,itemprice,itemdescription,price,itemcategory,fuser;
    Button cancel,edit;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edititems);
        mAuth = FirebaseAuth.getInstance();
        //fuser = mAuth.getCurrentUser().getEmail();

        cancel=findViewById(R.id.cancel);
        edit=findViewById(R.id.edit);
        /*price= (String) getIntent().getExtras().get("price");*/

        textname = (EditText) findViewById(R.id.itemname2);
        textprice = (EditText) findViewById(R.id.itemprice2);
        textcategory=(EditText) findViewById(R.id.itemcategory2);
        ImageView imageitem=findViewById(R.id.imageItem2);

        price=getIntent().getStringExtra("price");
        textDescription = (EditText) findViewById(R.id.description2);
        textname.setText(getIntent().getStringExtra("nameofgrocery"));
        textcategory.setText(getIntent().getStringExtra("category"));
        textprice.setText(getIntent().getStringExtra("price"));
        textDescription.setText(getIntent().getStringExtra("Description"));

        String img = getIntent().getStringExtra("imageUrl");

        Glide.with(getApplicationContext())
                .load(img).into(imageitem);


        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i= new Intent(Edititems.this, Adminactivity.class);
                startActivity(i);
            }
        });

        edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                itemname = textname.getText().toString();
                itemprice = textprice.getText().toString();
                itemdescription = textDescription.getText().toString();
                itemcategory = textcategory.getText().toString();
                String img2 = getIntent().getStringExtra("imageUrl");

                /* insertitemData(itemname,itemprice, itemdescription,itemcategory,img);*/
                db.collection("Items").whereEqualTo("imageUrl", img).get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
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

                Intent i = new Intent(Edititems.this, Adminactivity.class);
                startActivity(i);
            }
        });
    }

            private void updateData(List<String> list) {
                WriteBatch batch = db.batch();
                // Iterate through the list
                for (int k = 0; k < list.size(); k++) {
                    // Update each list item
                    DocumentReference ref = db.collection("Items").document(list.get(k));
                    batch.update(ref, "nameofgrocery", itemname);
                    batch.update(ref, "category", itemcategory);
                    batch.update(ref, "price", itemprice);
                    batch.update(ref, "Description", itemdescription);
                }


                batch.commit().addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {

                    }
                });

            }
        }
