package com.example.bottomnavigation.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.bottomnavigation.Model.FollowModel;
import com.example.bottomnavigation.Model.User;
import com.example.bottomnavigation.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class FollowAdapter extends RecyclerView.Adapter<FollowAdapter.viewHolder> {
Context context;
ArrayList<FollowModel> list = new ArrayList<>();

    public FollowAdapter(Context context, ArrayList<FollowModel> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public viewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.friends,parent,false);
        return  new viewHolder(view);

    }

    @Override
    public void onBindViewHolder(@NonNull viewHolder holder, int position) {
FollowModel model =list.get(position);

        FirebaseDatabase.getInstance().getReference()
                .child("USERS")
                .child(model.getFollowedby())
                .addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        User user  =snapshot.getValue(User.class);
                        Picasso.get()
                                .load(user.getProfile_photo())
                                .placeholder(R.drawable.placeholder)
                                .into(holder.img);
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
        ImageView img;
        TextView name;
        public viewHolder(@NonNull View itemView) {
            super(itemView);
img = itemView.findViewById(R.id.friendphoto);
name = itemView.findViewById(R.id.friendename);

        }
    }

}
