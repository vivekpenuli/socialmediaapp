package com.example.bottomnavigation.Fragment;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.bottomnavigation.Adapter.FollowAdapter;
import com.example.bottomnavigation.Model.FollowModel;
import com.example.bottomnavigation.Model.PostModel;
import com.example.bottomnavigation.Model.RequestModel;
import com.example.bottomnavigation.Model.User;
import com.example.bottomnavigation.R;
import com.example.bottomnavigation.loginuser;
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

import java.util.ArrayList;

public class ProfileFragment extends Fragment {
TextView name, profes;
TextView followerscount,ifollowcount,postcount;
    RecyclerView profilrev;
    ArrayList<FollowModel> arraylist =new ArrayList<>();
    FollowAdapter profileAdapter;
Uri uri;
ImageView logout;
int flag=-1;

ImageView uploadcover,photoupload,profile,profilebuttn;
StorageReference storageReference;
FirebaseDatabase database;
FirebaseAuth auth;
    public ProfileFragment() {
        // Required empty public constructor
    }



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @SuppressLint("MissingInflatedId")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =inflater.inflate(R.layout.fragment_profile, container, false);
        uploadcover = view.findViewById(R.id.uploadbackphoto);
        photoupload  = view.findViewById(R.id.coverphoto);
        followerscount = view.findViewById(R.id.textView11);
        ifollowcount = view.findViewById(R.id.textView12);
        name = view.findViewById(R.id.usernameprofile1);
        profes = view.findViewById(R.id.userwork);
        profile=view.findViewById(R.id.commentprofile);
        postcount = view.findViewById(R.id.textView13);
        logout = view.findViewById(R.id.logout);
        profilebuttn=view.findViewById(R.id.clipimg);

storageReference = FirebaseStorage.getInstance().getReference();
database = FirebaseDatabase.getInstance();

auth =FirebaseAuth.getInstance();
database.getReference().child("USERS").child(FirebaseAuth.getInstance().getUid()).addValueEventListener(new ValueEventListener() {
    @Override
    public void onDataChange(@NonNull DataSnapshot snapshot) {
        if(snapshot.exists())
        {
            User user = snapshot.getValue(User.class);
            Picasso.get()
                    .load(user.getCover_photo())
                    .placeholder(R.drawable.placeholder)
                    .into(photoupload);
            Picasso.get()
                    .load(user.getProfile_photo())
                    .placeholder(R.drawable.placeholder)
                    .into(profile);

            name.setText(user.getName());
            profes.setText(user.getProfession());


        }
    }

    @Override
    public void onCancelled(@NonNull DatabaseError error) {

    }
});

FirebaseDatabase.getInstance().getReference()
        .child("USERS")
        .child(FirebaseAuth.getInstance().getUid()).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
if (snapshot.exists()){
                RequestModel requestModel = snapshot.getValue(RequestModel.class);
                followerscount.setText(requestModel.getFollowcount()+"");
            }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


FirebaseDatabase.getInstance().getReference()
        .child("USERS")
        .child(FirebaseAuth.getInstance().getUid())
        .child("follow").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                //Log.e(dataSnapshot.getKey(),dataSnapshot.getChildrenCount() + "");
                int count= (int) snapshot.getChildrenCount();
              //  System.out.println("your follow: "+count+"");
ifollowcount.setText(count+"");



            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });



      profilrev = view.findViewById(R.id.friendrv);
        profilrev.setLayoutManager(new GridLayoutManager(getContext(),1, LinearLayoutManager.HORIZONTAL,false));

        database.getReference().child("USERS").child(auth.getUid())
                .child("Followers")
                        .addValueEventListener(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot snapshot) {
                           arraylist.clear();

                           for (DataSnapshot dataSnapshot : snapshot.getChildren())
                           {
                               FollowModel followModel = dataSnapshot.getValue(FollowModel.class);
                               arraylist.add(followModel);

                           }
                                profileAdapter = new FollowAdapter(getContext(),arraylist);
                                profilrev.setAdapter(profileAdapter);
                                profileAdapter.notifyDataSetChanged();

                               /*

                                requestAdapter = new RequestAdapter(getContext(),list);
                reterv.setAdapter(requestAdapter);
requestAdapter.notifyDataSetChanged();

                                */


                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError error) {

                            }
                        });
        /*
  arraylist.add(new FollowModel(R.drawable.bhageshwar,"Amit kumar"));
        arraylist.add(new FollowModel(R.drawable.bhageshwar,"Amit kumar"));
        arraylist.add(new FollowModel(R.drawable.einsten,"Sumit kumar"));
        arraylist.add(new FollowModel(R.drawable.bhageshwar,"Gopal kumar"));
        arraylist.add(new FollowModel(R.drawable.bhageshwar,"vivek kumar"));
        arraylist.add(new FollowModel(R.drawable.bhageshwar,"gourav kumar"));
        arraylist.add(new FollowModel(R.drawable.bhageshwar,"Aayush kumar"));
        arraylist.add(new FollowModel(R.drawable.einsten,"Hritik kumar"));
        arraylist.add(new FollowModel(R.drawable.bhageshwar,"ritik kumar"));
        arraylist.add(new FollowModel(R.drawable.bhageshwar,"Amit kumar"));
        arraylist.add(new FollowModel(R.drawable.download,"Amit kumar"));
        arraylist.add(new FollowModel(R.drawable.bhageshwar,"Amit kumar"));
        arraylist.add(new FollowModel(R.drawable.add,"Amit kumar"));
        arraylist.add(new FollowModel(R.drawable.bhageshwar,"Amit kumar"));
        arraylist.add(new FollowModel(R.drawable.bhageshwar,"Amit kumar"));
        arraylist.add(new FollowModel(R.drawable.einsten,"parvesh kumar"));

        profileAdapter = new FollowAdapter(getContext(),arraylist);
        profilrev.setAdapter(profileAdapter);
        */
   /*

        storyAdapter = new StoryAdapter(getContext(),arraylist);
       storyrv.setNestedScrollingEnabled(false);
        storyrv.setAdapter(storyAdapter);
    */


        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                auth.signOut();
              //  Intent intent = new Intent(ProfileFragment.this,loginuser.class);
             //   startActivity(intent);
Intent intent = new Intent(ProfileFragment.this.getActivity(),loginuser.class);
                startActivity(intent);
getActivity().finish();
            }
        });




        profilebuttn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ImagePicker.with(ProfileFragment.this)
                        .crop()
                        .compress(1024)
                        .maxResultSize(1080,1080)
                        .createIntent(intent -> {
                            startForMediaPickerResult.launch(intent);
                            return null;
                        });

            }
        });


uploadcover.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View view) {
        flag =0;
        ImagePicker.with(ProfileFragment.this)
                .crop(16f, 9f)
                .compress(1024)
                .maxResultSize(1080,1080)
                .createIntent(intent -> {
                    startForMediaPickerResult.launch(intent);
                    return null;
                });
    }
});

   return view;
    }

    private final ActivityResultLauncher<Intent> startForMediaPickerResult = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            result -> {
               if(flag==0)  //backcoverphto
               {
                   flag=-1;
                   Intent data = result.getData();
                   if (data != null && result.getResultCode() == Activity.RESULT_OK) {
                       uri = data.getData();

                       if (uri==null)
                       {
                           Toast.makeText(getContext(), "Please select image", Toast.LENGTH_SHORT).show();
                       }
                       else {
                           photoupload.setImageURI(uri);

                           StorageReference dref = storageReference.child("cover_photo").child(FirebaseAuth.getInstance().getUid());
                           dref.putFile(uri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                               @Override
                               public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                                   Toast.makeText(getContext(), "Cover photo Saved", Toast.LENGTH_SHORT).show();
                                   dref.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                                       @Override
                                       public void onSuccess(Uri uri) {

                                           database.getReference().child("USERS").child(FirebaseAuth.getInstance().getUid()).child("cover_photo").setValue(uri.toString());


                                       }
                                   });

                               }
                           });


                       }
                   }

               }


               else {
//profile photo

                   Intent data = result.getData();
                   if (data != null && result.getResultCode() == Activity.RESULT_OK) {
                       uri = data.getData();

                       if (uri==null)
                       {
                           Toast.makeText(getContext(), "Please select image", Toast.LENGTH_SHORT).show();
                       }
                       else {
                           profile.setImageURI(uri);

                           StorageReference dref = storageReference.child("profile_photo").child(FirebaseAuth.getInstance().getUid());
                           dref.putFile(uri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                               @Override
                               public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                                   Toast.makeText(getContext(), "profile photo Updated", Toast.LENGTH_SHORT).show();
                                   dref.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                                       @Override
                                       public void onSuccess(Uri uri) {

                                           database.getReference().child("USERS").child(FirebaseAuth.getInstance().getUid()).child("profile_photo").setValue(uri.toString());


                                       }
                                   });

                               }
                           });


                       }
                   }



                }
                });
}













