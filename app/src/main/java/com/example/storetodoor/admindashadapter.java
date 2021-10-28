package com.example.storetodoor;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;



import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;


public class admindashadapter extends RecyclerView.Adapter<admindashadapter.viewHolder> {
    ArrayList<Pojo> datalist;
    private Context mContext;

    public admindashadapter(ArrayList<Pojo> datalist) {
        this.datalist = datalist;
    }

    public admindashadapter(Context mcontext)
    {
        this.mContext = mcontext;
    }
    @NonNull
    @Override
    public viewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        mContext = parent.getContext();
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.admin_dashboard_recycleview, parent, false);
        return new viewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull  viewHolder holder, int position) {
        holder.name_of_grocery.setText(datalist.get(position).getNameofgrocery());
        holder.price.setText(datalist.get(position).getPrice());
        holder.category.setText(datalist.get(position).getCategory());

        Glide.with(mContext)
                .load(datalist.get(position).getImageUrl()).into(holder.image);

        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(holder.name_of_grocery.getContext(), Edititems.class);
                intent.putExtra("category", datalist.get(position).getCategory());
                intent.putExtra("nameofgrocery", datalist.get(position).getNameofgrocery());
                intent.putExtra("price", datalist.get(position).getPrice());
                intent.putExtra("Description", datalist.get(position).getDescription());
                intent.putExtra("imageUrl", datalist.get(position).getImageUrl());

                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                holder.cardView.getContext().startActivity(intent);
            }
        });

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
