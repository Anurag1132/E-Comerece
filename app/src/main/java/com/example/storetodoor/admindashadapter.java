package com.example.storetodoor;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

public class admindashadapter extends RecyclerView.Adapter<admindashadapter.viewHolder> {
    ArrayList<Pojo> datalist;

    public admindashadapter(ArrayList<Pojo> datalist) {
        this.datalist = datalist;
    }
    @NonNull
    @Override
    public viewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.admin_dashboard_recycleview, parent, false);
        return new viewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull viewHolder holder, int position) {
        holder.name_of_grocery.setText(datalist.get(position).getNameofgrocery());
        holder.price.setText(datalist.get(position).getPrice());
        holder.category.setText(datalist.get(position).getCategory());
    }

    @Override
    public int getItemCount() {
        return datalist.size();
    }

    public class viewHolder extends RecyclerView.ViewHolder {
        TextView name_of_grocery,price,category;
        ImageView image;
        CardView cardView ;
        public viewHolder(@NonNull View itemView) {
            super(itemView);
            name_of_grocery = itemView.findViewById(R.id.nameofGrocery1);
            price = itemView.findViewById(R.id.price1);
            category=itemView.findViewById(R.id.type1);
            image=itemView.findViewById(R.id.imgView_dashRec1);
            cardView = (CardView) itemView.findViewById(R.id.cardview_id1);
        }
    }
}
