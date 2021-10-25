package com.example.storetodoor;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseUser;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class userAdapter extends RecyclerView.Adapter<userAdapter.myviewholder>  {


    ArrayList<Pojo> userlist;
    FirebaseUser user;

    public userAdapter(ArrayList<Pojo> userlist) {
        this.userlist = userlist;
    }

    @NonNull
    @Override
    public myviewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.user_list_recycleview, parent, false);
        return new myviewholder(view);
    }


    @Override
    public void onBindViewHolder(@NonNull myviewholder holder, int position) {

        holder.userlist_firstname.setText(userlist.get(position).getFirstName());
        holder.userlist_lastname.setText(userlist.get(position).getLastName());
        holder.userlist_city.setText(userlist.get(position).getCity());
        holder.userlist_mobile.setText(userlist.get(position).getMobilenumber());
        holder.userlist_email.setText(userlist.get(position).getEmail());



    }


    @Override
    public int getItemCount() {

        return userlist.size();

    }



    class myviewholder extends RecyclerView.ViewHolder  {
        TextView userlist_firstname,userlist_lastname,userlist_city, userlist_mobile,userlist_email;



        public myviewholder(@NonNull View itemView) {
            super(itemView);
            userlist_firstname = itemView.findViewById(R.id.user_list_firstname);
            userlist_lastname=itemView.findViewById(R.id.user_list_lastname);
            userlist_city = itemView.findViewById(R.id.user_list_city);
            userlist_mobile = itemView.findViewById(R.id.user_list_mobile);
            userlist_email=itemView.findViewById(R.id.user_list_email);

        }


    }
}



