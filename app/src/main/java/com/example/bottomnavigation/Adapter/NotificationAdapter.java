package com.example.bottomnavigation.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.bottomnavigation.Model.NotificationModel;
import com.example.bottomnavigation.R;

import java.util.ArrayList;

public class NotificationAdapter extends RecyclerView.Adapter<NotificationAdapter.viewHolder> {
Context context;
ArrayList<NotificationModel> list;

    public NotificationAdapter(Context context, ArrayList<NotificationModel> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public viewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.notificationrecycle_sample,parent,false);


        return new viewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull viewHolder holder, int position) {

        NotificationModel model = list.get(position);
        holder.img.setImageResource(model.getImg());
        holder.name.setText(model.getMention());
        holder.time.setText(model.getTime());

        /*

PostModel postModel = list.get(position);
holder.profile.setImageResource(postModel.getProfile());

 */
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class viewHolder extends RecyclerView.ViewHolder{
TextView name,time;
ImageView img;

        public viewHolder(@NonNull View itemView) {
            super(itemView);
        img = itemView.findViewById(R.id.commentprofile);
        name = itemView.findViewById(R.id.textView15);
        time =itemView.findViewById(R.id.textView16);
        }
    }
}
