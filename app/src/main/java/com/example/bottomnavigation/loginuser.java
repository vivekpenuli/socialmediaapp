package com.example.bottomnavigation;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Toast;

import com.example.bottomnavigation.databinding.ActivityLoginuserBinding;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;


public class loginuser extends AppCompatActivity {
    ActivityLoginuserBinding binding;

FirebaseAuth auth;
FirebaseUser currentuser;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

       binding = ActivityLoginuserBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        auth = FirebaseAuth.getInstance();
        currentuser = auth.getCurrentUser();
        binding.loginuser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (validateField()) {
                    Toast.makeText(loginuser.this, "Fill All Field", Toast.LENGTH_SHORT).show();

                } else
                {
                    auth.signInWithEmailAndPassword(binding.emailogin.getText().toString().trim(), binding.passlogin
                            .getText().toString().trim()).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {

                            if (task.isSuccessful()) {
                                Intent intent = new Intent(loginuser.this, MainActivity.class);
                                startActivity(intent);
                                finish();
                            } else {
                                Toast.makeText(loginuser.this, "you are not user", Toast.LENGTH_SHORT).show();
                            }

                        }
                    });
               }
            }
        });
        binding.gotologin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(loginuser.this,signup.class);
                startActivity(intent);
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        if (currentuser!=null)
        {
            Intent intent =new Intent(loginuser.this,MainActivity.class);
            startActivity(intent);
            finish();
        }
    }

    private boolean validateField() {
        int flag=0;

        if (binding.emailogin.getText().toString().isEmpty())
        {
            binding.emailogin.setError("Enter Email");
            flag =1;
        }
        else if (!Patterns.EMAIL_ADDRESS.matcher(binding.emailogin.getText().toString()).matches())
        {
            binding.emailogin.setError("Enter Valid Email");
            flag=1;
        }
        if (binding.passlogin.getText().toString().isEmpty())
        {
            binding.passlogin.setError("Enter Password");
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