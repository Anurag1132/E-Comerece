package com.example.storetodoor;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;



public class cartAdapter extends RecyclerView.Adapter<cartAdapter.viewHolder> {

    ArrayList<Pojo> pojoDetails;
    Context context;
    public cartAdapter(Context context, ArrayList<Pojo> pojoDetails){
        super();
        this.pojoDetails=pojoDetails;
        this.context=context;

    }

    public cartAdapter(ArrayList<Pojo> pojoDetails) {

        this.pojoDetails=pojoDetails;
    }


    @NonNull
    @Override
    public viewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        context = parent.getContext();

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.cart_items, parent, false);
        return new viewHolder(view);
    }

    @Override
    public int getItemCount() {
        return pojoDetails.size();
    }


    public Pojo getItem(int position)
    {
        return pojoDetails.get(position);
    }


    @Override
    public void onBindViewHolder(@NonNull viewHolder holder, int position) {
        Pojo pojoDetails=getItem(position);
        holder.name.setText(pojoDetails.getNameofgrocery());
        holder.price.setText(pojoDetails.getPrice());
        holder.quantity.setText(pojoDetails.getQuantity());
        holder.des.setText(pojoDetails.getDescription());
        Glide.with(context)
                .load(pojoDetails.getImageUrl()).into(holder.image);

        FirebaseFirestore db= FirebaseFirestore.getInstance();
        holder.remove.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final AlertDialog.Builder builder = new AlertDialog.Builder(context);
                builder.setMessage("Would you like to remove order item permanently from Cart?")
                        //  .setCancelable(false)
                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                db.collection("AddToCart").document(getItem(position).getId()).delete()
                                        .addOnCompleteListener(new OnCompleteListener<Void>() {
                                            @Override
                                            public void onComplete(@NonNull Task<Void> task) {
                                                if (task.isSuccessful()) {
                                                    Intent intent1=new Intent(context,AddToCart.class);

                                                    context.startActivity(intent1);
                                                    ((Activity)context).finish();

                                                }
                                            }
                                        });


                            }
                        });
                builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });
                AlertDialog alert = builder.create();
                alert.show();
                return;

            }
        });
    }




    public class viewHolder extends RecyclerView.ViewHolder{
        TextView name,price,quantity,des,remove;
        ImageView image;

        public viewHolder(@NonNull  View itemView) {
            super(itemView);
            name=itemView.findViewById(R.id.item_name);
            price=itemView.findViewById(R.id.item_price);
            des=itemView.findViewById(R.id.item_des);
            image=itemView.findViewById(R.id.imageitem);
            quantity=itemView.findViewById(R.id.item_quantity);
            remove=itemView.findViewById(R.id.remove);



        }
    }
}

