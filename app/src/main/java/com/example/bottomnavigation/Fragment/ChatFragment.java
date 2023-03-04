package com.example.bottomnavigation.Fragment;

import static android.os.Build.VERSION_CODES.R;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.bottomnavigation.Adapter.ChatAdapter;
import com.example.bottomnavigation.Model.RequestModel;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class ChatFragment extends Fragment {
RecyclerView chatrev;
ArrayList<RequestModel> chatlist=new ArrayList<>();
FirebaseDatabase database;
ChatAdapter chatAdapter;
    public ChatFragment() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
       View view =inflater.inflate(com.example.bottomnavigation.R.layout.fragment_chat, container, false);


        database = FirebaseDatabase.getInstance();
        chatrev = view.findViewById(com.example.bottomnavigation.R.id.charrecv);
        chatrev.setLayoutManager(new LinearLayoutManager(getContext(),LinearLayoutManager.VERTICAL,false));
       chatAdapter = new ChatAdapter(getContext(),chatlist);
       chatrev.setAdapter(chatAdapter);

       database.getReference()
               .child("USERS")
               .child(FirebaseAuth.getInstance().getUid())
               .child("Followers").addValueEventListener(new ValueEventListener() {
                   @Override
                   public void onDataChange(@NonNull DataSnapshot snapshot) {
                       chatlist.clear();
                       for (DataSnapshot dataSnapshot: snapshot.getChildren())
                       {
                           RequestModel model = dataSnapshot.getValue(RequestModel.class);
                         // model.getUserID(dataSnapshot.getKey());
                            model.setUserID(dataSnapshot.getKey());
                           chatlist.add(model);

                       }

                       chatAdapter.notifyDataSetChanged();
                   }

                   @Override
                   public void onCancelled(@NonNull DatabaseError error) {

                   }
               });




    return  view;
    }




}