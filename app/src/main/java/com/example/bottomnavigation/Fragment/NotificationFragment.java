package com.example.bottomnavigation.Fragment;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.bottomnavigation.Adapter.NotificationAdapter;

import com.example.bottomnavigation.Model.NotificationModel;
import com.example.bottomnavigation.R;
//import com.google.android.material.tabs.TabLayout;

import java.util.ArrayList;

public class NotificationFragment extends Fragment {

    RecyclerView noterv;
    ArrayList<NotificationModel> list  =new ArrayList<>();
    NotificationAdapter notificationAdapter;

    public NotificationFragment() {
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

        View view =  inflater.inflate(R.layout.fragment_notification, container, false);

        noterv = view.findViewById(R.id.notificationRv);
        noterv.setLayoutManager(new LinearLayoutManager(getContext(),LinearLayoutManager.VERTICAL,false));
        list.add(new NotificationModel(R.drawable.bhageshwar,"mr. parvesh penuli mention you in comment","4 hour ago"));
        list.add(new NotificationModel(R.drawable.bhageshwar,"mr. parvesh penuli mention you in comment","4 hour ago"));
        list.add(new NotificationModel(R.drawable.bhageshwar,"mr. parvesh penuli mention you in comment","4 hour ago"));
        list.add(new NotificationModel(R.drawable.bhageshwar,"mr. parvesh penuli mention you in comment","4 hour ago"));
        list.add(new NotificationModel(R.drawable.bhageshwar,"mr. parvesh penuli mention you in comment","4 hour ago"));
        list.add(new NotificationModel(R.drawable.bhageshwar,"mr. parvesh penuli mention you in comment","4 hour ago"));
        list.add(new NotificationModel(R.drawable.bhageshwar,"mr. parvesh penuli mention you in comment","4 hour ago"));
        list.add(new NotificationModel(R.drawable.bhageshwar,"mr. parvesh penuli mention you in comment","4 hour ago"));
        list.add(new NotificationModel(R.drawable.bhageshwar,"mr. parvesh penuli mention you in comment","4 hour ago"));
        list.add(new NotificationModel(R.drawable.bhageshwar,"mr. parvesh penuli mention you in comment","4 hour ago"));
        list.add(new NotificationModel(R.drawable.bhageshwar,"mr. parvesh penuli mention you in comment","4 hour ago"));
        list.add(new NotificationModel(R.drawable.bhageshwar,"mr. parvesh penuli mention you in comment","4 hour ago"));
        list.add(new NotificationModel(R.drawable.bhageshwar,"mr. parvesh penuli mention you in comment","4 hour ago"));
        list.add(new NotificationModel(R.drawable.einsten,"mr. vivek penuli mention you in comment","4 hour ago"));

        list.add(new NotificationModel(R.drawable.bhageshwar,"mr. parvesh penuli mention you in comment","4 hour ago"));

        notificationAdapter = new NotificationAdapter(getContext(),list);
        noterv.setAdapter(notificationAdapter);






        return  view;
    }
}