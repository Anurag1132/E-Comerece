package com.example.storetodoor;


import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;


import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.List;


public class cartAdapter extends RecyclerView.Adapter<cartAdapter.viewHolder> {

    public FirebaseFirestore db = FirebaseFirestore.getInstance();
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
    public void onBindViewHolder(@NonNull viewHolder holder, int position) {
        holder.name.setText(pojoDetails.get(position).getNameofgrocery());
        holder.price.setText(pojoDetails.get(position).getPrice());
        holder.quantity.setText(pojoDetails.get(position).getQuantity());
        holder.des.setText(pojoDetails.get(position).getDescription());
    }



    @Override
    public int getItemCount() {
        return pojoDetails.size();
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

