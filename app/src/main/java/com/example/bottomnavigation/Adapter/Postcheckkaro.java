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

import com.example.bottomnavigation.CommentActivity;
import com.example.bottomnavigation.Model.PostModel;
import com.example.bottomnavigation.Model.User;
import com.example.bottomnavigation.R;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class Postcheckkaro extends RecyclerView.Adapter<Postcheckkaro.viewHolder> {
Context context;
ArrayList<PostModel> list;

    public Postcheckkaro(Context context, ArrayList<PostModel> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public viewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.dashboard_rv_sample,parent,false);

        return  new Postcheckkaro.viewHolder(view);
        //return new PostAdapter.viewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull viewHolder holder, int position) {
        PostModel postModel = list.get(position);
     //   setHasStableIds(true);

        String dis = postModel.getPostdesc();
        if (dis.equals(""))  // empty discription
        {
            holder.story.setVisibility(View.GONE);
        }
        else {
            holder.story.setText(postModel.getPostdesc());
            holder.story.setVisibility(View.VISIBLE);
        }
//notifyItemChanged(position);
        Picasso.get()
                .load(postModel.getPostimg())
                .placeholder(R.drawable.placeholder)
                .into(holder.post);
        holder.like.setText(postModel.getPostlikes()+"");
        holder.comment.setText(postModel.getCommentcount()+"");

        FirebaseDatabase.getInstance().getReference().child("USERS")
                .child(postModel.getPostby()).addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        User user =snapshot.getValue(User.class);

                        Picasso.get()
                                .load(user.getProfile_photo())
                                .placeholder(R.drawable.placeholder)
                                .into(holder.profile);
                        holder.name.setText(user.getName());
                        holder.prof.setText(user.getProfession());
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });


        holder.comment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(context, CommentActivity.class);
                intent.putExtra("postId",postModel.getPostid());  // put extra function send data from one activity to other
                intent.putExtra("postBy",postModel.getPostby());   // ye dono data next activity me ja rahe he
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(intent);

            }
        });


        FirebaseDatabase.getInstance().getReference()
                .child("Posts")
                .child(postModel.getPostid())
                .child("like")
                .child(FirebaseAuth.getInstance().getUid())
                .addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {

                        if (snapshot.exists())
                        {
//ye do line abhi ke he

                            holder.like.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_baseline_thumb_up_24, 0, 0, 0);

                        }
                        else {
//ye do line abhi ke he


/*
                                                                    holder.like.setOnClickListener(new View.OnClickListener() {
                                                                        @Override
                                                                        public void onClick(View view) {

                                                                            FirebaseDatabase.getInstance().getReference()
                                                                                    .child("Posts")
                                                                                    .child(postModel.getPostid())
                                                                                    .child("likes")
                                                                                    .child(FirebaseAuth.getInstance().getUid())
                                                                                    .setValue(true).addOnSuccessListener(new OnSuccessListener<Void>() {
                                                                                        @Override
                                                                                        public void onSuccess(Void unused) {

                                                                                            FirebaseDatabase.getInstance().getReference()
                                                                                                    .child("Posts")
                                                                                                    .child(postModel.getPostid())
                                                                                                    .child("likes")
                                                                                                    .setValue(postModel.getPostlikes() +1 ).addOnSuccessListener(new OnSuccessListener<Void>() {
                                                                                                        @Override
                                                                                                        public void onSuccess(Void unused) {
                                                                                                            holder.like.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_baseline_thumb_up_24, 0, 0, 0);


                                                                                                        }
                                                                                                    });
                                                                                        }
                                                                                    });
                                                                        }
                                                                    });
*/
                            holder.like.setOnClickListener(new View.OnClickListener() {


                                @Override
                                public void onClick(View view) {


                                    FirebaseDatabase.getInstance().getReference()
                                            .child("Posts")
                                            .child(postModel.getPostid())
                                            .child("like")
                                            .child(FirebaseAuth.getInstance().getUid())
                                            .setValue(true).addOnSuccessListener(new OnSuccessListener<Void>() {
                                                @Override
                                                public void onSuccess(Void unused) {



                                                    FirebaseDatabase.getInstance().getReference()
                                                            .child("Posts")
                                                            .child(postModel.getPostid())
                                                            .child("postlikes")
                                                            .setValue(postModel.getPostlikes() +1 ).addOnSuccessListener(new OnSuccessListener<Void>() {
                                                                @Override
                                                                public void onSuccess(Void unused) {

                                                                    holder.like.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_baseline_thumb_up_24, 0, 0, 0);
                                                                    //   Toast.makeText(context, "sucess", Toast.LENGTH_SHORT).show();

                                                                }
                                                            });


                                                }
                                            });
                                }
                            });






                        }

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
        TextView name,prof,like,comment,story;
        ImageView profile , post;

        public viewHolder(@NonNull View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.postuserName);
            prof= itemView.findViewById(R.id.postprofession);
            like = itemView.findViewById(R.id.like);
            comment = itemView.findViewById(R.id.comment);
            story =itemView.findViewById(R.id.textView4);
            profile =itemView.findViewById(R.id.post_profile_image);
            post = itemView.findViewById(R.id.post_postImg);
        }
    }
}
