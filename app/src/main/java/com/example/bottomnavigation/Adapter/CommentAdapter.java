package com.example.bottomnavigation.Adapter;

import android.content.Context;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.bottomnavigation.Model.CommentModel;
import com.example.bottomnavigation.Model.User;
import com.example.bottomnavigation.R;
import com.github.marlonlom.utilities.timeago.TimeAgo;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class CommentAdapter extends RecyclerView.Adapter<CommentAdapter.viewHolder> {
Context context;
ArrayList<CommentModel> list;

    public CommentAdapter(Context context, ArrayList<CommentModel> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public viewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.comment_dashborad,parent,false);

        return new viewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull viewHolder holder, int position) {
CommentModel commentModel = list.get(position);

//holder.comment.setText(commentModel.getCommentBody());
        String time = TimeAgo.using(commentModel.getCommentTime());
        holder.time.setText(time);
        FirebaseDatabase.getInstance().getReference().child("USERS")
                .child(commentModel.getCommentBy())
                .addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        User user = snapshot.getValue(User.class);
                        Picasso.get()
                                .load(user.getProfile_photo())
                                .placeholder(R.drawable.placeholder)
                                .into(holder.pic);
                        holder.comment.setText(Html.fromHtml("<b>"+user.getName()+":"+"</b>" + commentModel.getCommentBody()));
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

    public class  viewHolder extends RecyclerView.ViewHolder{

ImageView pic;
TextView  comment,time;
        public viewHolder(@NonNull View itemView) {
            super(itemView);

            pic= itemView.findViewById(R.id.senderpic);
        //    name =itemView.findViewById(R.id.namecomment);
            comment = itemView.findViewById(R.id.disccomment);
            time= itemView.findViewById(R.id.timecomment);



        }
    }
}
