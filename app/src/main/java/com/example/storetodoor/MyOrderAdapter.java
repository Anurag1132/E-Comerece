package com.example.storetodoor;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class MyOrderAdapter extends RecyclerView.Adapter<MyOrderAdapter.viewHolder> {
    ArrayList<Pojo> pojoDetails;
    Context context;
    public MyOrderAdapter(Context context, ArrayList<Pojo> pojoDetails){
        super();
        this.pojoDetails=pojoDetails;
        this.context=context;

    }
    public MyOrderAdapter(ArrayList<Pojo> pojoDetails) {
        this.pojoDetails= pojoDetails;
    }

    @NonNull
    @Override
    public viewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        context = parent.getContext();

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.myorderitems, parent, false);
        return new viewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull  viewHolder holder, int position) {
        holder.ordername.setText(pojoDetails.get(position).getNameofgrocery());
        holder.orderprice.setText(pojoDetails.get(position).getPrice());
        holder.orderquantity.setText(pojoDetails.get(position).getQuantity());
        holder.orderdes.setText(pojoDetails.get(position).getDescription());
        holder.orderaddress.setText(pojoDetails.get(position).getAddress());
        holder.orderstatus.setText(pojoDetails.get(position).getStatus());
        Glide.with(context)
                .load(pojoDetails.get(position).getImageUrl()).into(holder.imageView);

    }

    @Override
    public int getItemCount() {
        return pojoDetails.size();
    }

    public class viewHolder extends RecyclerView.ViewHolder {
        ImageView imageView;
        TextView ordername,orderquantity,orderprice,orderdes,orderstatus,orderaddress;
        public viewHolder(@NonNull  View itemView) {
            super(itemView);
            ordername=itemView.findViewById(R.id.order_name);
            orderprice=itemView.findViewById(R.id.order_price);
            orderdes=itemView.findViewById(R.id.order_des);
            imageView=itemView.findViewById(R.id.imageorder);
            orderquantity=itemView.findViewById(R.id.order_quantity);
            orderstatus=itemView.findViewById(R.id.order_status);
            orderaddress=itemView.findViewById(R.id.order_address);

        }
    }
}
