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

import com.example.bottomnavigation.Adapter.ChatAdapter;
import com.example.bottomnavigation.Adapter.MessageAdapter;
import com.example.bottomnavigation.Fragment.ChatFragment;
import com.example.bottomnavigation.Model.MessagesModel;
import com.example.bottomnavigation.Model.User;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.squareup.picasso.Picasso;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.Date;

public class ChatUserActivity extends AppCompatActivity {
FirebaseAuth auth;
TextView name;
EditText etmesage;

MessageAdapter messageAdapter;
ArrayList<MessagesModel> list=new ArrayList<>();
ImageView pic,back,sendmsg;
RecyclerView chat;
@SuppressLint("MissingInflatedId")
@Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat_user);
        auth = FirebaseAuth.getInstance();
        name =findViewById(R.id.sendername);
        pic= findViewById(R.id.senderpic);
        sendmsg = findViewById(R.id.send);
        chat =findViewById(R.id.chat);
        etmesage = findViewById(R.id.etmessage);
        back = findViewById(R.id.baclk);
      final  String senderID= auth.getUid();  // making a variable make it global even inside declare inside any function
        String receverid= getIntent().getStringExtra("userId");

    FirebaseDatabase.getInstance().getReference()
            .child("USERS")
            .child(receverid)
            .addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    User user = snapshot.getValue(User.class);

                    Picasso.get()
                            .load(user.getProfile_photo())
                            .placeholder(R.drawable.placeholder)
                            .into(pic);
                    name.setText(user.getName());

                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });

back.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View view) {
        Intent intent = new Intent(ChatUserActivity.this, ChatFragment.class);
        startActivity(intent);
        finish();
    }
});


//final ArrayList<MessagesModel> messagesModels = new ArrayList<>();
//final MessageAdapter messageAdapter = new MessageAdapter(messagesModels,this);

//chat.setAdapter(messageAdapter);

   // chat.setLayoutManager(new LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false));

    LinearLayoutManager layoutManager = new LinearLayoutManager(this);
    chat.setLayoutManager(layoutManager);

    messageAdapter = new MessageAdapter(list,this);
    chat.setAdapter(messageAdapter);
    final String senderroom = senderID +receverid;
    final String recieverroom = receverid + senderID;



    FirebaseDatabase.getInstance().getReference()
            .child("chats")
            .child(senderroom)
            .addValueEventListener(new ValueEventListener() {
                @SuppressLint("NotifyDataSetChanged")
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    list.clear();
                    for (DataSnapshot dataSnapshot:snapshot.getChildren())
                    {
                        MessagesModel model = dataSnapshot.getValue(MessagesModel.class);
                        list.add(model);

                    }

                    messageAdapter.notifyDataSetChanged();

                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });






    sendmsg.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View view) {

            String message= etmesage.getText().toString();
            final MessagesModel model = new MessagesModel(senderID,message); // we created a contructor for this purpose
            model.setTimestamp(new Date().getTime());
            etmesage.setText("");



            FirebaseDatabase.getInstance().getReference()
                    .child("chats")
                    .child(senderroom)
                    .push()
                    .setValue(model).addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void unused) {


                           FirebaseDatabase.getInstance().getReference()
                                   .child("chats")
                                   .child(recieverroom)
                                   .push()
                                   .setValue(model).addOnSuccessListener(new OnSuccessListener<Void>() {
                                       @Override
                                       public void onSuccess(Void unused) {

                                       }
                                   });




                        }
                    });






        }
    });






    }
}