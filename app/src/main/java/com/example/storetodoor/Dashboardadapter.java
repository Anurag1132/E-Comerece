package com.example.storetodoor;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;


import java.util.ArrayList;

public class Dashboardadapter extends RecyclerView.Adapter<Dashboardadapter.viewHolder> {

    ArrayList<Pojo> datalist;
    private Context mContext ;


    public Dashboardadapter(Context mContext, ArrayList<Pojo> datalist) {
        this.mContext = mContext;
        this.datalist = datalist;
    }

    public Dashboardadapter(ArrayList<Pojo> datalist) {
        this.datalist = datalist;

    }

    @NonNull
    @Override
    public viewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.user_dashboard_recycleview, parent, false);
        return new viewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull  viewHolder holder, int position) {
        holder.name_of_grocery.setText(datalist.get(position).getNameofgrocery());
        holder.price.setText(datalist.get(position).getPrice());
        holder.category.setText(datalist.get(position).getCategory());

        holder.name_of_grocery.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(holder.name_of_grocery.getContext(), Details.class);
                intent.putExtra("category", datalist.get(position).getCategory());
                intent.putExtra("nameofgrocery", datalist.get(position).getNameofgrocery());
                intent.putExtra("price", datalist.get(position).getPrice());
                intent.putExtra("Description", datalist.get(position).getDescription());
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                holder.name_of_grocery.getContext().startActivity(intent);
            }
        });





    }

    @Override
    public int getItemCount() {
        return datalist.size();
    }

    public class viewHolder extends RecyclerView.ViewHolder{
        TextView name_of_grocery,price,category;
        ImageView image;
        CardView cardView ;
        public viewHolder(@NonNull  View itemView) {
            super(itemView);
            name_of_grocery = itemView.findViewById(R.id.nameofGrocery);
            price = itemView.findViewById(R.id.price);
            category=itemView.findViewById(R.id.type);
            image=itemView.findViewById(R.id.imgView_dashRec);
            cardView = (CardView) itemView.findViewById(R.id.cardview_id);
        }
    }
}
