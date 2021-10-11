package com.example.storetodoor;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;


import java.util.ArrayList;

public class Dashboardadapter extends RecyclerView.Adapter<Dashboardadapter.viewHolder> {

    ArrayList<Pojo> datalist;


    public Dashboardadapter(ArrayList<Pojo> datalist) {
        this.datalist = datalist;

    }
    @Override
    public viewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.user_dashboard_recycleview, parent, false);
        return new viewHolder(view);
    }


    @Override
    public void onBindViewHolder(@NonNull  viewHolder holder, int position) {
        holder.name_of_grocery.setText(datalist.get(position).getNameofgrocery());
        holder.price.setText(datalist.get(position).getPrice());

    }

    @Override
    public int getItemCount() {
        return datalist.size();
    }

    public class viewHolder extends RecyclerView.ViewHolder{
        TextView name_of_grocery,price;
        ImageView image;
        public viewHolder(@NonNull  View itemView) {
            super(itemView);
            name_of_grocery = itemView.findViewById(R.id.nameofGrocery);
            price = itemView.findViewById(R.id.price);

            image= itemView.findViewById(R.id.imgView_dashRec);
        }
    }
}
