package com.example.storetodoor;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;



import java.util.ArrayList;

public class OrderStatusAdapter extends RecyclerView.Adapter<OrderStatusAdapter.myviewholder>  {

    ArrayList<Pojo> orderedStatusList;
    private Context mcontext;

    public OrderStatusAdapter(Context mcontext)
    {
        this.mcontext = mcontext;
    }


    public OrderStatusAdapter(ArrayList<Pojo> orderedStatusList) {
        this.orderedStatusList = orderedStatusList;
    }


    @Override
    public myviewholder onCreateViewHolder(@NonNull  ViewGroup parent, int viewType) {

        mcontext = parent.getContext();
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.orderstatus_recyclerview, parent, false);
        return new myviewholder(view);
    }


    @Override
    public void onBindViewHolder(@NonNull  myviewholder holder, int position) {
        holder.nameofgrocery.setText(orderedStatusList.get(position).getNameofgrocery());
        holder.quantity.setText(orderedStatusList.get(position).getQuantity());
        holder.uid.setText(orderedStatusList.get(position).getUserId());

        Glide.with(mcontext)
                .load(orderedStatusList.get(position).getImageUrl()).into(holder.itempicture);


        holder.nameofgrocery.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(holder.nameofgrocery.getContext(), PendingDetails.class);
                intent.putExtra("category", orderedStatusList.get(position).getCategory());
                intent.putExtra("nameofgrocery", orderedStatusList.get(position).getNameofgrocery());
                intent.putExtra("price", orderedStatusList.get(position).getPrice());
                intent.putExtra("Description", orderedStatusList.get(position).getDescription());
                intent.putExtra("ImageUrl", orderedStatusList.get(position).getImageUrl());
                intent.putExtra("qunty", orderedStatusList.get(position).getQuantity());
                intent.putExtra("email", orderedStatusList.get(position).getUserId());
                intent.putExtra("address", orderedStatusList.get(position).getAddress());

                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                holder.nameofgrocery.getContext().startActivity(intent);
            }
        });
    }


    @Override
    public int getItemCount() {
        return orderedStatusList.size();
    }

    class myviewholder extends RecyclerView.ViewHolder {
        TextView nameofgrocery, uid, quantity;
        ImageView itempicture;

        public myviewholder(@NonNull View itemView) {
            super(itemView);
            nameofgrocery = itemView.findViewById(R.id.nameofGroce1);
            uid = itemView.findViewById(R.id.userId1);
            quantity = itemView.findViewById(R.id.Quantity1);
            itempicture = itemView.findViewById(R.id.itempicture1);
        }
    }
}
