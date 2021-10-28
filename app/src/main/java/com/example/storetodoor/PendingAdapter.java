package com.example.storetodoor;


import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;


import com.bumptech.glide.Glide;

import java.util.ArrayList;

public class PendingAdapter extends RecyclerView.Adapter<PendingAdapter.myviewholder> {

    ArrayList<Pojo> pendingList;
    private Context mcontext;

    public PendingAdapter(Context mcontext)
    {
        this.mcontext = mcontext;
    }


    public PendingAdapter(ArrayList<Pojo> pendingList) {
        this.pendingList = pendingList;
    }

    @Override
    public myviewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        mcontext = parent.getContext();
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.pendingorder_recyclerview, parent, false);
        return new myviewholder(view);

    }



    @Override
    public void onBindViewHolder(@NonNull myviewholder holder, int position) {
        holder.nameofgrocery.setText(pendingList.get(position).getNameofgrocery());
        holder.quantity.setText(pendingList.get(position).getQuantity());
        holder.uid.setText(pendingList.get(position).getUserId());

        Glide.with(mcontext)
                .load(pendingList.get(position).getImageUrl()).into(holder.itempicture);


        holder.nameofgrocery.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                        Intent intent = new Intent(holder.nameofgrocery.getContext(), OrderStatusDetails.class);
                        intent.putExtra("category", pendingList.get(position).getCategory());
                        intent.putExtra("nameofgrocery", pendingList.get(position).getNameofgrocery());
                        intent.putExtra("price", pendingList.get(position).getPrice());
                        intent.putExtra("Description", pendingList.get(position).getDescription());
                        intent.putExtra("ImageUrl", pendingList.get(position).getImageUrl());
                        intent.putExtra("qunty", pendingList.get(position).getQuantity());
                        intent.putExtra("email", pendingList.get(position).getUserId());
                        intent.putExtra("address", pendingList.get(position).getAddress());

                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        holder.nameofgrocery.getContext().startActivity(intent);
            }
        });
    }


    @Override
    public int getItemCount() {
        return pendingList.size();
    }

    class myviewholder extends RecyclerView.ViewHolder {
        TextView nameofgrocery, uid, quantity;
        ImageView itempicture;

        public myviewholder(@NonNull View itemView) {
            super(itemView);
            nameofgrocery = itemView.findViewById(R.id.nameofGroce);
            uid = itemView.findViewById(R.id.userId);
            quantity = itemView.findViewById(R.id.Quantity);
            itempicture = itemView.findViewById(R.id.itempicture);
        }
    }
}
