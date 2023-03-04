package com.example.bottomnavigation.Adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.bottomnavigation.ChatUserActivity;
import com.example.bottomnavigation.Model.RequestModel;
import com.example.bottomnavigation.Model.User;
import com.example.bottomnavigation.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class ChatAdapter extends RecyclerView.Adapter<ChatAdapter.viewHolder> {
Context context;
ArrayList<RequestModel> list;

    public ChatAdapter(Context context, ArrayList<RequestModel> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public viewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(context).inflate(R.layout.sample_chat_recycler,parent,false);

        return new viewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull viewHolder holder, int position) {

        RequestModel model = list.get(position);


        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, ChatUserActivity.class);

               intent.putExtra("userId",model.getUserID());
                context.startActivity(intent);
            }
        });

        // esha sabh q keya q ke jabh ham fragment se follwoers ka data bhej rahe he
        // followers wale folder me sirf id he or kuch nhai
        // or aghar hame phone , name chahye to hame ush id ke undher se wo cheez la ne padheghae
        // ehsleye ham ush id pe jayenghe ek ek kar ke or phir photo , name lauenghe
        FirebaseDatabase.getInstance().getReference()
                .child("USERS")
                .child(model.getUserID()).addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        User user = snapshot.getValue(User.class);
                        Picasso.get()
                                .load(user.getProfile_photo())
                                .placeholder(R.drawable.placeholder)
                                .into(holder.pic);

                        holder.name.setText(user.getName());

                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class viewHolder extends RecyclerView.ViewHolder{

        TextView name, message;
        ImageView pic;

        public viewHolder(@NonNull View itemView) {
            super(itemView);

            name = itemView.findViewById(R.id.userchatname);
            message = itemView.findViewById(R.id.messagelast);
            pic = itemView.findViewById(R.id.senderpic);

        }
    }
}
