package com.example.bottomnavigation;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.bottomnavigation.Model.PostModel;
import com.example.bottomnavigation.Model.User;
import com.github.dhaval2404.imagepicker.ImagePicker;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;

import java.util.Date;

public class Postphoto_Activity extends AppCompatActivity {
Button post;
ImageView postuserphoto, postuserimage,postclip;
TextView postusername, postuserprofession ,posttext;
Uri uri;

FirebaseDatabase database;
FirebaseAuth auth;
ProgressDialog dialog;
FirebaseStorage firebaseStorage;
@SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_postphoto);
    post = findViewById(R.id.post);
    postuserphoto = findViewById(R.id.senderpic);
    postusername= findViewById(R.id.postusername);
    postuserprofession = findViewById(R.id.postuserprofession);
    posttext = findViewById(R.id.posttext);
    postuserimage = findViewById(R.id.postuserimage);
    postclip= findViewById(R.id.imageView7);
    dialog = new ProgressDialog(getApplicationContext());
    database = FirebaseDatabase.getInstance();
    auth = FirebaseAuth.getInstance();
    post.setEnabled(false);
firebaseStorage = FirebaseStorage.getInstance();


dialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
dialog.setTitle("Post Uploading");
dialog.setMessage("Please wait");
dialog.setCancelable(false);
dialog.setCanceledOnTouchOutside(false);




post.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View view) {
       // dialog.show();
        StorageReference dref =firebaseStorage.getReference().child("Posts")
                .child(FirebaseAuth.getInstance().getUid())
                .child(new Date().getTime()+"");

        dref.putFile(uri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
           dref.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
               @Override
               public void onSuccess(Uri uri) {
                   PostModel postModel = new PostModel();
                   postModel.setPostimg(uri.toString());
                   postModel.setPostby(FirebaseAuth.getInstance().getUid());
                   postModel.setPostdesc(posttext.getText().toString());
                   postModel.setPosttime(new Date().getTime());






                   // ye wala code he real

                   database.getReference().child("Posts")
                           .push()  //create random child inside post folder in realtimedatabase
                           .setValue(postModel).addOnSuccessListener(new OnSuccessListener<Void>() {
                               @Override
                               public void onSuccess(Void unused) {
                              //     dialog.dismiss();
                                   Toast.makeText(Postphoto_Activity.this, "Susscfully uploaded", Toast.LENGTH_SHORT).show();

                               }
                           });




               }
           });


            }
        });



    }
});

    database.getReference().child("USERS")
                    .child(FirebaseAuth.getInstance().getUid())
            .addValueEventListener(new ValueEventListener() {
                                @Override
                                public void onDataChange(@NonNull DataSnapshot snapshot) {
                                    if (snapshot.exists())
                                    {
                                        User user = snapshot.getValue(User.class);  // yadh rakh na ye har ush fragment me kar na padhegha jesh me hame ye sabh detail cahhaye
                                              Picasso.get()
                                                      .load(user.getProfile_photo())// esha nhai hoga ke ek bhaar get kar le value to hamesha ke leye sabh ke leye ho gaya;
                                                      .placeholder(R.drawable.placeholder)// har file me get karna
                                                      .into(postuserphoto);
                                              postusername.setText(user.getName());  // ye tabh kar na jabh sirf ek item ke value set kar ne ho
                                              postuserprofession.setText(user.getProfession()); // aghar bhaut share ho jeshe recyclerview me hota he to
                                                                                                   // phit adapter portion me ye sabh karo
                                    }
                                }

                                @Override
                                public void onCancelled(@NonNull DatabaseError error) {

                                }
                            });


    postuserimage.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View view) {
         //   Toast.makeText(Postphoto_Activity.this, "hello", Toast.LENGTH_SHORT).show();
           ImagePicker.with(Postphoto_Activity.this)
                   .crop(3,2)
                .compress(1024)
                .maxResultSize(1080,1080)
                .createIntent(intent -> {
                    startForMediaPickerResult.launch(intent);
                    return null;
                });
        }
    });



    posttext.addTextChangedListener(new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

        }

        @Override
        public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
String desc = posttext.getText().toString();
if (!desc.isEmpty()) {
    post.setEnabled(true);
}
        else
{
    post.setEnabled(false);
}


        }

        @Override
        public void afterTextChanged(Editable editable) {

        }
    });



    }

    private final ActivityResultLauncher<Intent> startForMediaPickerResult = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            result -> {
                    Intent data = result.getData();
                    if (data != null && result.getResultCode() == Activity.RESULT_OK) {
                        uri = data.getData();

                        if (uri==null)
                        {
                            Toast.makeText(Postphoto_Activity.this, "Please select image", Toast.LENGTH_SHORT).show();
                        }
                        else {
                            postclip.setImageURI(uri);
                            postclip.setVisibility(View.VISIBLE);

                            post.setEnabled(true);

                        }
                    }
            });
}