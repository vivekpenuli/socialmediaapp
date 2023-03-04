package com.example.bottomnavigation;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.bottomnavigation.Adapter.CommentAdapter;
import com.example.bottomnavigation.Model.CommentModel;
import com.example.bottomnavigation.Model.PostModel;
import com.example.bottomnavigation.Model.User;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.Date;

public class CommentActivity extends AppCompatActivity {
Intent intent;
String postId;
String postBy;
ImageView profile,post,Go;
EditText commentData;
ArrayList<CommentModel> list = new ArrayList<>();
CommentAdapter commentAdapter;
TextView discription,comment,like;
RecyclerView recyclerView;
FirebaseDatabase database;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_comment);
    intent =getIntent();
database = FirebaseDatabase.getInstance();
profile= findViewById(R.id.commentprofile);
post= findViewById(R.id.commentpost);
comment = findViewById(R.id.commentcomment);
commentData = findViewById(R.id.commentdata);
like =findViewById(R.id.commentlike) ;
recyclerView = findViewById(R.id.comment_rv);




Go= findViewById(R.id.commentbutton);
discription = findViewById(R.id.commentdisc);
    postId = intent.getStringExtra("postId");  // getstringexxtra ka mtlb he jo tumh ne data bheja tha aghar wo string type ka he to ushe get kar ne wala function b string hoga
   postBy = intent.getStringExtra("postBy");


   database.getReference().child("Posts")
           .child(postId)
           .addValueEventListener(new ValueEventListener() {
               @Override
               public void onDataChange(@NonNull DataSnapshot snapshot) {

                   PostModel postModel = snapshot.getValue(PostModel.class);

                   Picasso.get()
                           .load(postModel.getPostimg())
                           .placeholder(R.drawable.placeholder)
                           .into(post);
                   comment.setText(postModel.getCommentcount()+"");


                   String dis = postModel.getPostdesc();
                   if (dis.equals(""))  // empty discription
                   {
                       discription.setVisibility(View.GONE);
                       //holder.story.setVisibility(View.GONE);
                   }
                   else {
                       discription.setText(postModel.getPostdesc());
                       discription.setVisibility(View.VISIBLE);
                       //holder.story.setText(postModel.getPostdesc());
                       //holder.story.setVisibility(View.VISIBLE);
                   }

                  like.setText(postModel.getPostlikes()+"");  // ye + "" ehse leye likh jabh b koe integer value ko tumh text me show karo to ushe null text ke sath concatenat jarur kar na



               }

               @Override
               public void onCancelled(@NonNull DatabaseError error) {

               }
           });

        FirebaseDatabase.getInstance().getReference().child("USERS")
                .child(postBy).addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        User user =snapshot.getValue(User.class);
                        Picasso.get()
                                .load(user.getProfile_photo())
                                .placeholder(R.drawable.placeholder)
                                .into(profile);

                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });

Go.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View view) {
        CommentModel commentModel = new CommentModel();
        commentModel.setCommentBody(commentData.getText().toString());
        commentModel.setCommentTime(new Date().getTime());
        commentModel.setCommentBy(FirebaseAuth.getInstance().getUid());

        database.getReference().child("Posts")
                .child(postId)
                .child("Comments")
                .push()         // aghar push use nhai karenghe to comments folder ke data ko he ye haar bhar replace karegha mtlb jo phele data
                                // tha ushe pe new data ko replace kar degha or pura na data chala jayegha
                .setValue(commentModel).addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void unused) {

                  FirebaseDatabase.getInstance().getReference()
                          .child("Posts")
                          .child(postId)
                          .child("commentcount").addListenerForSingleValueEvent(new ValueEventListener() {
                              @Override
                              public void onDataChange(@NonNull DataSnapshot snapshot) {
                                  int commentcount = 0 ;
                                  if (snapshot.exists())
                                  {
                                      commentcount = snapshot.getValue(Integer.class);
                                  }

                                  database.getReference()
                                          .child("Posts")
                                          .child(postId)
                                          .child("commentcount")
                                          .setValue(commentcount+1).addOnSuccessListener(new OnSuccessListener<Void>() {
                                              @Override
                                              public void onSuccess(Void unused) {

                                                  commentData.setText("");


                                              }
                                          });




                              }

                              @Override
                              public void onCancelled(@NonNull DatabaseError error) {

                              }
                          });




                    /*  .setValue(commentModel.getCommentnumber() + 1).addOnSuccessListener(new OnSuccessListener<Void>() {
                              @Override
                              public void onSuccess(Void unused) {
                             commentData.setText("");
                              }
                          }); */



                        /*      database.getReference()
                                .child("Posts")
                                .child(postId)
                                .child("commentcount").addValueEventListener(new ValueEventListener() {
                                    @Override
                                    public void onDataChange(@NonNull DataSnapshot snapshot) {

                                    }

                                    @Override
                                    public void onCancelled(@NonNull DatabaseError error) {

                                    }
                                });  */


                    }
                });

    }
});
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        commentAdapter =new CommentAdapter(this,list);
        recyclerView.setAdapter(commentAdapter);


database.getReference().child("Posts")
        .child(postId)
        .child("Comments")  // add value event listner lagh ne ka mtlb he esh comment name ke folder ke undher jithna b sub folder unh ko snapshot ke form me get kar lo
        .addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                list.clear();
                for (DataSnapshot dataSnapshot: snapshot.getChildren())
                {
                    CommentModel commentModel = dataSnapshot.getValue(CommentModel.class);
                    list.add(commentModel);
                }

                commentAdapter.notifyDataSetChanged();

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


    }
}