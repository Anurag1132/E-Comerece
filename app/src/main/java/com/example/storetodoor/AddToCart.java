package com.example.storetodoor;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.firestore.WriteBatch;

import java.util.ArrayList;
import java.util.List;
import static com.example.storetodoor.Storage.*;


public class AddToCart extends AppCompatActivity {

    TextView cancel1,payment;

    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    RecyclerView listView;
    Double s=0.0;
    Double x=0.0;
    cartAdapter adapter;
    FirebaseAuth mAuth = FirebaseAuth.getInstance();
    private ArrayList<Pojo> pojoDetails;
    private static TextView total_price;
    Pojo  pDs;
    EditText addresss;
    ImageView back;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_to_cart);

        listView = findViewById(R.id.addcart_recycleView);
        payment=findViewById(R.id.payment);
        total_price = findViewById(R.id.total_price);
        addresss=findViewById(R.id.address);
        pojoDetails = new ArrayList<>();

        listView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));

        adapter = new cartAdapter(pojoDetails);
        listView.setAdapter(adapter);



        back=findViewById(R.id.back);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent account = new Intent(AddToCart.this, UserActivity.class);
                startActivity(account);
            }
        });

        cancel1 = findViewById(R.id.cancel1);
        cancel1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent account = new Intent(AddToCart.this, UserActivity.class);
                startActivity(account);

            }
        });
        payment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(AddToCart.this,"Order Placed Successfully",Toast.LENGTH_LONG).show();
                String status="Ordered";
                final String address=addresss.getText().toString();


                //  final com.example.pizzaworld.model.PojoDetails PojoDetails=   new  PojoDetails( status,address, totalprice);
                db.collection("AddToCart").whereEqualTo("userId", FirebaseAuth.getInstance().getCurrentUser().getEmail()).whereEqualTo("Status", "Cart").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            List<String> list = new ArrayList<>();
                            for (QueryDocumentSnapshot document : task.getResult()) {

                                Log.d("", document.getId());
                                list.add(document.getId());

                            }
                            //  Log.d("", list.toString());
                            updateData(list);
                        }

                        Intent i = new Intent(AddToCart.this,OrderDone.class);
                        startActivity(i);
                    }
                });


            }
        });

        db.collection("AddToCart").whereEqualTo("userId",FirebaseAuth.getInstance().getCurrentUser().getEmail()).whereEqualTo("Status","Cart").get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
            @Override
            public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                if (!queryDocumentSnapshots.isEmpty()) {
                    List<DocumentSnapshot> list = queryDocumentSnapshots.getDocuments();
                    for (DocumentSnapshot d : list) {
                        pDs = d.toObject(Pojo.class);
                        pDs.setId(d.getId());
                        pojoDetails.add(pDs);
                        Storage.abc.add(pDs.getPrice().substring(1));
                    }

                    adapter.notifyDataSetChanged();

                    for (int i = Storage.abc.size()-1;i>=0;i=i-1)
                    {
                        s= Double.parseDouble(Storage.abc.get(i));
                        x=x+s;
                    }
                    total_price.setText("$"+x.toString());
                    abc.clear();
                }
                else{
                    AlertDialog.Builder builder = new AlertDialog.Builder(AddToCart.this);
                    builder.setMessage("There is no item in the Cart rightnow, Press 'OK' to go back to Home")
                            .setCancelable(false)
                            .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int id) {
                                    Intent i=new Intent(AddToCart.this,UserActivity.class);
                                    startActivity(i);

                                }
                            });
                    AlertDialog alert = builder.create();
                    alert.show();

                }
            }

        });

    }

    private void updateData(List<String> list) {
        WriteBatch batch = db.batch();

        // Iterate through the list
        for (int k = 0; k < list.size(); k++) {

            // Update each list item
            DocumentReference ref = db.collection("AddToCart").document(list.get(k));
            batch.update(ref, "Address", addresss.getText().toString());
            batch.update(ref,"Status","Ordered" );
            batch.update(ref,"Totalprice",total_price.getText().toString());

        }


        batch.commit().addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {

            }
        });



    }
}