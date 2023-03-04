package com.example.bottomnavigation;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Patterns;
import android.view.View;
import android.widget.Toast;

import com.example.bottomnavigation.Model.SignupData;
import com.example.bottomnavigation.Model.User;
import com.example.bottomnavigation.databinding.ActivitySignupBinding;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class signup extends AppCompatActivity {
ActivitySignupBinding binding;
FirebaseAuth auth;
FirebaseDatabase database;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
       binding = ActivitySignupBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        auth = FirebaseAuth.getInstance();
        database = FirebaseDatabase.getInstance();


        binding.join.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
             //   Toast.makeText(signup.this, "name is" + binding.usernamesign.getText().toString(), Toast.LENGTH_SHORT).show();
                if (validateField())
                {
                    Toast.makeText(signup.this, "Please fill all field", Toast.LENGTH_SHORT).show();
                }
                else {
                 auth.createUserWithEmailAndPassword(binding.emailsign.getText().toString().trim(), binding.passwordsign.getText().toString().trim())
                        .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if (task.isSuccessful()) {
                                   // SignupData signupData = new SignupData(binding.usernamesign.getText().toString(),
                                     //       binding.profeesionsign.getText().toString(), binding.emailsign.getText().toString(), binding.passwordsign.getText().toString());

                                    User user =new User(

                                            binding.usernamesign.getText().toString(),
                                            binding.emailsign.getText().toString(),
                                            binding.passwordsign.getText().toString(),
                                            binding.profeesionsign.getText().toString()

                                            );

                                    String id = task.getResult().getUser().getUid();
                                    database.getReference().child("USERS").child(id).setValue(user);


                                    Toast.makeText(signup.this, "Detail uploaded", Toast.LENGTH_SHORT).show();
                                    Intent intent = new Intent(signup.this, MainActivity.class);
                                    startActivity(intent);

                                } else {
                                    Toast.makeText(signup.this, "Already Register Account", Toast.LENGTH_SHORT).show();
                                }

                            }
                        });


                }
            }
        });

    }

    private boolean validateField() {
int flag=0;
        if (binding.usernamesign.getText().toString().isEmpty())
        {
            //  Toast.makeText(signup.this, "name is" + binding.usernamesign.getText().toString(), Toast.LENGTH_SHORT).show();
            binding.usernamesign.setError("Enter Name");
            flag =1;
        }
        if (binding.emailsign.getText().toString().isEmpty())
        {
            binding.emailsign.setError("Enter Email");
            flag=1;
        }
        else if (!Patterns.EMAIL_ADDRESS.matcher(binding.emailsign.getText().toString()).matches())
        {
            binding.emailsign.setError("Enter Valid Email");
            flag=1;
        }
        if (binding.profeesionsign.getText().toString().isEmpty())
        {
            binding.profeesionsign.setError("Enter Profession");
            flag=1;
        }
        if (binding.passwordsign.getText().toString().isEmpty())
        {
            binding.passwordsign.setError("Enter Password");
            flag=1;
        }
        else if (binding.passwordsign.getText().toString().length() <6 )
        {
            binding.passwordsign.setError("Password Lenght greater then 6");
            flag=1;
        }

        if (flag==1)
        {
            return true;
        }
        else
        {
            return false;
        }

    }
}