package com.example.bottomnavigation.Fragment;

import android.annotation.SuppressLint;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.bottomnavigation.Adapter.RequestAdapter;

import com.example.bottomnavigation.Model.RequestModel;

import com.example.bottomnavigation.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;


import java.util.ArrayList;

public class SearchFragment extends Fragment {
    RecyclerView reterv;
  //  ArrayList<NotificationModel> list  =new ArrayList<>();
    //NotificationAdapter notificationAdapter;
FirebaseDatabase database;
FirebaseAuth auth;
    ArrayList<RequestModel> list = new ArrayList<>();
    RequestAdapter requestAdapter;
    public SearchFragment() {
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

        View view =  inflater.inflate(R.layout.fragment_search, container, false);
database = FirebaseDatabase.getInstance();
        reterv = view.findViewById(R.id.searchrv);
        reterv.setLayoutManager(new LinearLayoutManager(getContext(),LinearLayoutManager.VERTICAL,false));



        database.getReference().child("USERS").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                list.clear();
           for (DataSnapshot dataSnapshot : snapshot.getChildren())

           {    // remove all element from the list . jabh ham ehda kuch kare rahe he jesh se database me effect padh raha ho to
               // ye use kar na aghar normal kam ho jeshse recyclerview ke element ke click hone pe toast sow kar na tabh esh ke jarurat nhai he
               // but agahr recyclerview ke elemetn pe click kar ne se database me farakh padhe to eshe use kar na q ke jeshe he tumh database me
               // kuch b change karoghe to recyclerview ko notify ho jayegha or wo phir se recyclerview banan de ga

               if (dataSnapshot.getKey().equals(FirebaseAuth.getInstance().getUid()))
               {
                   continue;
               }

               //            User user = snapshot.getValue(User.class);
               RequestModel model = dataSnapshot.getValue(RequestModel.class);
               //     RequestModel model = new RequestModel();
               //  model.setRename(dataSnapshot.child("name").getValue(String.class));
               //  model.setReprofe(dataSnapshot.child("profession").getValue(String.class));
model.setUserID(dataSnapshot.getKey());
               list.add(model);

           }
                requestAdapter = new RequestAdapter(getContext(),list);
                reterv.setAdapter(requestAdapter);
requestAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        /*
        list.add(new RequestModel(R.drawable.bhageshwar,"vivek","president"));
        list.add(new RequestModel(R.drawable.bhageshwar,"vivek","president"));
        list.add(new RequestModel(R.drawable.bhageshwar,"vivek","president"));
        list.add(new RequestModel(R.drawable.bhageshwar,"vivek","president"));
        list.add(new RequestModel(R.drawable.bhageshwar,"vivek","president"));
        list.add(new RequestModel(R.drawable.bhageshwar,"vivek","president"));
        list.add(new RequestModel(R.drawable.bhageshwar,"vivek","president"));

        requestAdapter = new RequestAdapter(getContext(),list);
        reterv.setAdapter(requestAdapter);

*/

        return  view;



    }
}