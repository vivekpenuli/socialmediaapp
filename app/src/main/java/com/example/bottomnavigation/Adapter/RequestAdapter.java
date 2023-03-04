package com.example.bottomnavigation.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.bottomnavigation.Model.FollowModel;
import com.example.bottomnavigation.Model.Ifollow;
import com.example.bottomnavigation.Model.RequestModel;
import com.example.bottomnavigation.R;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.Date;

public class RequestAdapter extends RecyclerView.Adapter<RequestAdapter.viewHolder> {
Context context;
ArrayList<RequestModel> list;

    public RequestAdapter(Context context, ArrayList<RequestModel> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public viewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(context).inflate(R.layout.requestrecycle_sample,parent,false);

        return new viewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull viewHolder holder, int position) {

        RequestModel model = list.get(position);
        Picasso.get()
                        .load(model.getProfile_photo())
                                .placeholder(R.drawable.placeholder)
                                        .into(holder.rimg);
        holder.rproff.setText(model.getProfession());
        holder.rname.setText(model.getName());


        FirebaseDatabase.getInstance().getReference()
                        .child("USERS")
                                .child(model.getUserID())
                                        .child("Followers")
                                                .child(FirebaseAuth.getInstance().getUid()).addValueEventListener(new ValueEventListener()
                //add value listner ne followers folder ke undhar jithna b data tha unh sabh ko fetch kar deya
                {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {

                        if (snapshot.exists())
                        {
                            holder.follow.setText("Follwoing");
                            holder.follow.setEnabled(false);
                        }
                        else
                        {
                            holder.follow.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {

                                    Toast.makeText(context, "click me", Toast.LENGTH_SHORT).show();

                                    //follow concept

                                    Ifollow ifollow = new Ifollow();
                                    ifollow.setFollow(model.getUserID());
                                    ifollow.setFollowtime(new Date().getTime());

                                    FirebaseDatabase.getInstance().getReference()
                                            .child("USERS")
                                            .child(FirebaseAuth.getInstance().getUid())
                                            .child("follow")
                                            .push()
                                            .setValue(ifollow);



                                    //create dummy class
// Followers concept
                                    FollowModel followModel = new FollowModel();
                                    followModel.setFollowedby(FirebaseAuth.getInstance().getUid());
                                    followModel.setFollowtime(new Date().getTime());

                                    FirebaseDatabase.getInstance().getReference()
                                            .child("USERS")
                                            .child(model.getUserID())
                                            .child("Followers")
                                            .child(FirebaseAuth.getInstance().getUid())
                                            .setValue(followModel)
                                            .addOnSuccessListener(new OnSuccessListener<Void>() {
                                                @Override
                                                public void onSuccess(Void unused) {
                                                    // Toast.makeText(context, "sussefully followed", Toast.LENGTH_SHORT).show();


                                              /*

                                                    FirebaseDatabase.getInstance().getReference()
                                                            .child("USERS")
                                                            .child(model.getUserID())
                                                            .child("Follow_count").addValueEventListener(new ValueEventListener() {
                                                                @Override
                                                                public void onDataChange(@NonNull DataSnapshot snapshot) {


                                                                }

                                                                @Override
                                                                public void onCancelled(@NonNull DatabaseError error) {

                                                                }
                                                            });
                                                    */

                                                    FirebaseDatabase.getInstance().getReference()
                                                            .child("USERS")
                                                            .child(model.getUserID())
                                                            .child("followcount")
                                                            .setValue(model.getFollowcount() + 1).addOnSuccessListener(new OnSuccessListener<Void>() {
                                                                @Override
                                                                public void onSuccess(Void unused) {

                                                                    Toast.makeText(context, "You Followed "  + model.getName(), Toast.LENGTH_SHORT).show();

                                                                }
                                                            });




                                                }
                                            });



             /*   FirebaseDatabase.getInstance().getReference()
                        .child("USERS")
                        .child(model.getUserID()) // esh ka mtlb ush admi ke pass jao jeshe jeshe ham ne select keya he
                        .child("Followers")
                        .child(FirebaseAuth.getInstance().getUid())
                        .setValue(followModel).addOnSuccessListener(new OnSuccessListener<Void>() {  // add pn susses listner ka mtlb jabh value set ho jaye tabh kya kar na he
                            @Override                                                                // aghar value set nhai hue to on susssess listner nhia chalegha
                            public void onSuccess(Void unused) {
                                FirebaseDatabase.getInstance().getReference()
                                        .child("USERS")
                                        .child(model.getUserID())
                                        .child("followcount")
                                        .setValue(model.getFollowcount() + 1).addOnSuccessListener(new OnSuccessListener<Void>() {
                                            @Override
                                            public void onSuccess(Void unused) {

                                                Toast.makeText(context, "Follow sussefeully ", Toast.LENGTH_SHORT).show();
                                            // diable follow butten here

                                            }
                                        });




                            }
                        });  */



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

        TextView rname, rproff;
        ImageView rimg;
Button follow;
        public viewHolder(@NonNull View itemView) {
            super(itemView);
            rimg = itemView.findViewById(R.id.senderpic);
            rname = itemView.findViewById(R.id.postusername);
        rproff = itemView.findViewById(R.id.postuserprofession);

        follow = itemView.findViewById(R.id.follow);
        }
    }
}
