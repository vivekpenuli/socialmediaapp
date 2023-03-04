package com.example.bottomnavigation;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;
import android.widget.Toast;

import com.example.bottomnavigation.Fragment.ChatFragment;
import com.example.bottomnavigation.Fragment.HomeFragment;
import com.example.bottomnavigation.Fragment.NotificationFragment;
import com.example.bottomnavigation.Fragment.ProfileFragment;
import com.example.bottomnavigation.Fragment.SearchFragment;
import com.example.bottomnavigation.databinding.ActivityMainBinding;
import com.iammert.library.readablebottombar.ReadableBottomBar;

public class MainActivity extends AppCompatActivity {
ActivityMainBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.framecontainer, new HomeFragment());
        transaction.commit();

        binding.readablebottombar.setOnItemSelectListener(new ReadableBottomBar.ItemSelectListener() {
            @Override
            public void onItemSelected(int i) {
                FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();

                switch (i)
                {
                    case 0 :
                       transaction.replace(R.id.framecontainer,new HomeFragment());
                     //   Toast.makeText(MainActivity.this, "Home", Toast.LENGTH_SHORT).show();
                        break;

                    case 1:
                        transaction.replace(R.id.framecontainer, new SearchFragment());
                        //   Toast.makeText(MainActivity.this, "notification", Toast.LENGTH_SHORT).show();
                    break;

                    case 2:
                       transaction.replace(R.id.framecontainer,new ChatFragment());
                       break;
                        //  Toast.makeText(MainActivity.this, "Search", Toast.LENGTH_SHORT).show();
                    case 3:
                        transaction.replace(R.id.framecontainer,new NotificationFragment());
                        break;

                    case 4:
                        transaction.replace(R.id.framecontainer,new ProfileFragment());
                        break;

                }
                transaction.commit();






            }
        });


    }
}