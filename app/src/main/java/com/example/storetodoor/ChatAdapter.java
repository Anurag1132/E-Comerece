package com.example.storetodoor;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.google.firebase.firestore.FirebaseFirestore;



import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class ChatAdapter extends RecyclerView.Adapter<ChatAdapter.myviewholder>{
    ArrayList<Pojo> datalist;
    FirebaseFirestore db;




    public ChatAdapter(ArrayList<Pojo> datalist) {
        this.datalist = datalist;

    }

    @Override
    public myviewholder onCreateViewHolder(@NonNull  ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.chat_userlist_recyclerview, parent, false);
        return new myviewholder(view);
    }


    @Override
    public void onBindViewHolder(@NonNull myviewholder holder, int position) {

        holder.user_applicantemail.setText(datalist.get(position).getUserId());


        holder.user_applicantemail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               Intent i = new Intent(holder.user_applicantemail.getContext(), Adminchatdetails.class);
                i.putExtra("userId", datalist.get(position).getUserId());
                i.putExtra("Message",datalist.get(position).getMessage());
                i.putExtra("AdminMessage",datalist.get(position).getAdminMessage());
                i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

                holder.user_applicantemail.getContext().startActivity(i);

            }
        });


    }




    @Override
    public int getItemCount() {
        return datalist.size();
    }

    public class myviewholder extends RecyclerView.ViewHolder {
        TextView user_applicantemail;



        public myviewholder(@NonNull  View itemView) {
            super(itemView);
            user_applicantemail = itemView.findViewById(R.id.user_applicantemail);

        }
    }
}
