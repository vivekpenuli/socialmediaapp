package com.example.bottomnavigation.Fragment;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.provider.ContactsContract;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.bottomnavigation.Adapter.PostAdapter;
import com.example.bottomnavigation.Adapter.Postcheckkaro;
import com.example.bottomnavigation.Adapter.StoryAdapter;

import com.example.bottomnavigation.Model.FollowModel;
import com.example.bottomnavigation.Model.PostModel;
import com.example.bottomnavigation.Model.StoryuserModel;
import com.example.bottomnavigation.Model.User;
import com.example.bottomnavigation.Model.UserStories;
import com.example.bottomnavigation.Postphoto_Activity;
import com.example.bottomnavigation.R;
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
import com.makeramen.roundedimageview.RoundedImageView;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.Date;




public class HomeFragment extends Fragment {
ImageView usercoverphoto;
  RecyclerView storyrv;
  TextView createpost;
  Uri uri;
  RoundedImageView storyimg;
  ImageView createpost1,storybuttn;
  ArrayList<StoryuserModel>  arraylist =new ArrayList<>();
  StoryAdapter storyAdapter;
FirebaseDatabase firebaseDatabase;
Postcheckkaro ppadapter;
    ArrayList<String> postidealist=new ArrayList<String>();//Creating arraylist
FirebaseStorage firebaseStorage;
RecyclerView dasrev;
//ActivityResultLauncher<String> gallerylauncher;
ArrayList<PostModel> postModelArrayList=new ArrayList<>();
PostAdapter postAdapter;
    public HomeFragment() {
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
        View view =  inflater.inflate(R.layout.fragment_home, container, false);
usercoverphoto = view.findViewById(R.id.commentprofile);
firebaseDatabase = FirebaseDatabase.getInstance();
createpost = view.findViewById(R.id.createpost);
storybuttn = view.findViewById(R.id.storyclick);
storyimg = view.findViewById(R.id.storyimg);
firebaseStorage = FirebaseStorage.getInstance();
createpost1 = view.findViewById(R.id.createpost1);

createpost.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View view) {


        //   startActivity(intent);
        Intent intent = new Intent(HomeFragment.this.getActivity(), Postphoto_Activity.class);
        startActivity(intent);
    }
});


createpost1.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View view) {
        //   startActivity(intent);
        Intent intent = new Intent(HomeFragment.this.getActivity(),Postphoto_Activity.class);
        startActivity(intent);

    }
});




firebaseDatabase.getReference("USERS").child(FirebaseAuth.getInstance().getUid()).addValueEventListener(new ValueEventListener() {
    @Override
    public void onDataChange(@NonNull DataSnapshot snapshot) {
        if (snapshot.exists()) {
            User user = snapshot.getValue(User.class);
            Picasso.get()
                    .load(user.getProfile_photo())
                    .placeholder(R.drawable.placeholder)
                    .into(usercoverphoto);
        }
    }
    @Override
    public void onCancelled(@NonNull DatabaseError error) {

    }
});



// story recyclerview

       storyrv = view.findViewById(R.id.storyrecv);
      storyrv.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL,true));
        storyAdapter = new StoryAdapter(getContext(),arraylist);
       // storyrv.setNestedScrollingEnabled(false);
        storyrv.setAdapter(storyAdapter);
        FirebaseDatabase.getInstance().getReference()
                .child("stories")
                .addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {

                        if (snapshot.exists())
                        { arraylist.clear();
                            for (DataSnapshot storySnapshot :snapshot.getChildren())
                            {
                                StoryuserModel storyuserModel = new StoryuserModel();
                               // storyuserModel.setStoryAt(dataSnapshot.getKey());
                                storyuserModel.setStoryBy(storySnapshot.getKey());
                                storyuserModel.setStoryAt(storySnapshot.child("postedBy").getValue(Long.class));

                                ArrayList<UserStories> stories = new ArrayList<>();
                                for (DataSnapshot snapshot1 : storySnapshot.child("userStories").getChildren()){
                                    UserStories userStories = snapshot1.getValue(UserStories.class);
                                    stories.add(userStories);
                                }

                                storyuserModel.setStories(stories);
                                arraylist.add(storyuserModel);
                            }
                            storyAdapter.notifyDataSetChanged();
                        }

                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });

      /*
        arraylist.add(new StoryuserModel("vivek",R.drawable.bhageshwar,R.drawable.bhageshwar));
        arraylist.add(new StoryuserModel("kamal",R.drawable.bhageshwar,R.drawable.bhageshwar));
        arraylist.add(new StoryuserModel("prakash",R.drawable.bhageshwar,R.drawable.bhageshwar));
        arraylist.add(new StoryuserModel("praveen",R.drawable.bhageshwar,R.drawable.bhageshwar));
        arraylist.add(new StoryuserModel("sumit",R.drawable.bhageshwar,R.drawable.bhageshwar));
        arraylist.add(new StoryuserModel("aditya",R.drawable.einsten,R.drawable.bhageshwar));
        arraylist.add(new StoryuserModel("rohan",R.drawable.download,R.drawable.bhageshwar));
        arraylist.add(new StoryuserModel("gopal",R.drawable.einsten,R.drawable.bhageshwar));
        arraylist.add(new StoryuserModel("ganesh",R.drawable.download,R.drawable.bhageshwar));
        arraylist.add(new StoryuserModel("shiv",R.drawable.bhageshwar,R.drawable.bhageshwar));
        arraylist.add(new StoryuserModel("shankar",R.drawable.bhageshwar,R.drawable.bhageshwar));
        arraylist.add(new StoryuserModel("vivek",R.drawable.bhageshwar,R.drawable.bhageshwar));
        arraylist.add(new StoryuserModel("vivek",R.drawable.bhageshwar,R.drawable.bhageshwar));
        arraylist.add(new StoryuserModel("vivek",R.drawable.bhageshwar,R.drawable.download));

        storyAdapter = new StoryAdapter(getContext(),arraylist);
       storyrv.setNestedScrollingEnabled(false);
        storyrv.setAdapter(storyAdapter);


        */



// post section
      dasrev = view.findViewById(R.id.newsfeedrv);
      dasrev.setLayoutManager(new LinearLayoutManager(getContext(),LinearLayoutManager.VERTICAL,true));
//recyclerView.setLayoutManager(new LinearLayoutManager(context,LinearLayoutManager.VERTICAL,true));

      //why i declare them here not inside snapshot which generally i do
        // becase aghar eshe snapshoe ke undher likha to ye haar bhar page refresh karegha jo ke achi baat nhai he esh leye adapter ko upper se kar deya


    ppadapter =new Postcheckkaro(getContext(),postModelArrayList);
      dasrev.setAdapter(ppadapter);

      //  postAdapter = new PostAdapter(getContext(),postModelArrayList);
       // dasrev.setAdapter(postAdapter);
/*
      firebaseDatabase.getReference().child("Posts").addValueEventListener(new ValueEventListener() {
          @Override
          public void onDataChange(@NonNull DataSnapshot snapshot) {
postModelArrayList.clear();
              for (DataSnapshot dataSnapshot :snapshot.getChildren())
              {

                  PostModel model = dataSnapshot.getValue(PostModel.class);
                  model.setPostid(dataSnapshot.getKey());  // jo .push kara tha database me daal ne ke leye datasnapshot.getkey ka mtlb hota he
                                                          // id pata karo kesh ke undher tumh value insert karhe he
                  postModelArrayList.add(model);

              }
            //  postAdapter = new PostAdapter(getContext(),postModelArrayList);
             // dasrev.setAdapter(postAdapter);
              postAdapter.notifyDataSetChanged();

          }

          @Override
          public void onCancelled(@NonNull DatabaseError error) {

          }
      });

*/

/// edhar se likh raha hu me c\


//postidealist.add("Tpu7987PoCd9rN7y7xBTFeN8q3E2");
     //   postidealist.add("9lALNh3tVeUfNkHVLZrF5dEHVAL2");
postidealist.add(FirebaseAuth.getInstance().getUid());

FirebaseDatabase.getInstance().getReference()
        .child("USERS")
                .child(FirebaseAuth.getInstance().getUid())
                        .child("Followers").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
for (DataSnapshot dataSnapshot:snapshot.getChildren())
{
postidealist.add(dataSnapshot.getKey());
}

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });






//     // blink wala code he ye
       firebaseDatabase.getReference().child("Posts").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                postModelArrayList.clear();
                for (DataSnapshot dataSnapshot :snapshot.getChildren())
                {
//Tpu7987PoCd9rN7y7xBTFeN8q3E2  ,   9a4dfLvI66ZLg8fOEW5HHCoujBS2
                    PostModel model = dataSnapshot.getValue(PostModel.class);
                    model.setPostid(dataSnapshot.getKey());  // jo .push kara tha database me daal ne ke leye datasnapshot.getkey ka mtlb hota he
                    // id pata karo kesh ke undher tumh value insert karhe he
                    if (checkmania(model.getPostby())) {

                        postModelArrayList.add(model);
                    }
                }




//ppadapter.notifyItemChanged(0,postModelArrayList);
          ppadapter.notifyItemChanged(0);
     //   ppadapter.notifyDataSetChanged();
            }


            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });





// yha khatam ho raha he








        /* layoutManager = new LinearLayoutManager(getActivity()){
            @Override
            public boolean canScrollVertically() {
                return false;
            }
        };


postModelArrayList.add(new PostModel(R.drawable.download,R.drawable.einsten,"VIVEK PENULI","Student","7","8","Hope you like mytory"));

     postModelArrayList.add(new PostModel(R.drawable.einsten,R.drawable.bhageshwar,"VIVEK PENULI","Student","7","8",""));
      postModelArrayList.add(new PostModel(R.drawable.bhageshwar,R.drawable.bhai,"VIVEK PENULI","Student","7","8","Hope"));
      postModelArrayList.add(new PostModel(R.drawable.download,R.drawable.einsten,"VIVEK PENULI","Student","7","8","Hope you like mytory"));
      postModelArrayList.add(new PostModel(R.drawable.download,R.drawable.einsten,"VIVEK PENULI","Student","7","8","Hope you like mytory"));
      postModelArrayList.add(new PostModel(R.drawable.download,R.drawable.einsten,"VIVEK PENULI","Student","7","8","Hope you like mytory"));
      postModelArrayList.add(new PostModel(R.drawable.download,R.drawable.einsten,"VIVEK PENULI","Student","7","8","Hope you like mytory"));
      postModelArrayList.add(new PostModel(R.drawable.download,R.drawable.einsten,"VIVEK PENULI","Student","7","8","Hope you like mytory"));
      postModelArrayList.add(new PostModel(R.drawable.download,R.drawable.einsten,"VIVEK PENULI","Student","7","8","Hope you like mytory"));
      postModelArrayList.add(new PostModel(R.drawable.download,R.drawable.einsten,"VIVEK PENULI","Student","7","8","Hope you like mytory"));
      postModelArrayList.add(new PostModel(R.drawable.download,R.drawable.einsten,"VIVEK PENULI","Student","7","8","Hope you like mytory so this is about me do you want to say something special here in this occasion"));
      postModelArrayList.add(new PostModel(R.drawable.download,R.drawable.einsten,"VIVEK PENULI","Student","9","80","Hope you like mytory"));
      postModelArrayList.add(new PostModel(R.drawable.download,R.drawable.einsten,"VIVEK PENULI","Student","7","8","Hope you like mytory"));
      postModelArrayList.add(new PostModel(R.drawable.download,R.drawable.einsten,"VIVEK PENULI","Student","7","8","Hope you like mytory"));
      postModelArrayList.add(new PostModel(R.drawable.download,R.drawable.einsten,"pARVESH PENULI","Student","7","8","Hope you like mytory"));



     postAdapter = new PostAdapter(getContext(),postModelArrayList);
     dasrev.setAdapter(postAdapter);

     */
storybuttn.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View view) {

        ImagePicker.with(HomeFragment.this)
                .crop()
                .compress(1024)
                .maxResultSize(1080,1080)
                .createIntent(intent -> {
                    startForMediaPickerResult.launch(intent);
                    return null;
                });



    }
});

      return  view;
    }

    private boolean checkmania(String postby) {

        int i;
        for (i=0;i<postidealist.size();i++)
        {
            if (postby.equals(postidealist.get(i)))
            {
                return true;
            }
        }
return false;
    }



    private final ActivityResultLauncher<Intent> startForMediaPickerResult = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            result -> {
                Intent data = result.getData();
                if (data != null && result.getResultCode() == Activity.RESULT_OK) {
                    uri = data.getData();

                    if (uri==null)
                    {
                        Toast.makeText(getContext(), "Please select image", Toast.LENGTH_SHORT).show();
                    }
                    else {
                        storyimg.setImageURI(uri);

                        StorageReference dref = firebaseStorage.getReference()  // store data in storage
                                .child("stories")
                                .child(FirebaseAuth.getInstance().getUid())
                                .child(new Date().getTime()+"");


                             dref.putFile(uri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                            @Override
                            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {

                                dref.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                                    @Override
                                    public void onSuccess(Uri uri) {
                                       StoryuserModel storyuserModel = new StoryuserModel();
                                        storyuserModel.setStoryAt(new Date().getTime());
                                        UserStories stories = new UserStories(uri.toString(),storyuserModel.getStoryAt());

                                        firebaseDatabase.getReference()
                                                .child("stories")
                                                .child(FirebaseAuth.getInstance().getUid())
                                                .child("userStories")
                                                .push()
                                                .setValue(stories);

                                        firebaseDatabase.getReference()
                                                .child("stories")
                                                .child(FirebaseAuth.getInstance().getUid())
                                                .child("postedBy")
                                                .setValue(storyuserModel.getStoryAt());


                                  /*
                                        StoryuserModel storyuserModel = new StoryuserModel();
                                        storyuserModel.setStoryAt(new Date().getTime());

                                  firebaseDatabase.getReference()
                                           .child("stories")
                                           .child(FirebaseAuth.getInstance().getUid())
                                           .child("postedBy")
                                           .setValue(storyuserModel.getStoryAt()).addOnSuccessListener(new OnSuccessListener<Void>() {
                                               @Override
                                              public void onSuccess(Void unused) {

                                                   UserStories stories = new UserStories(uri.toString(),storyuserModel.getStoryAt());

                                                   firebaseDatabase.getReference()
                                                           .child("stories")
                                                           .child(FirebaseAuth.getInstance().getUid())
                                                           .child("userStories")
                                                           .push()
                                                           .setValue(3);
                                                 //  Toast.makeText(HomeFragment.this, "done", Toast.LENGTH_SHORT).show();
                                               }


                                           });  */

                                    }
                                });
                            }
                        });




                      //  postclip.setImageURI(uri);
                       // postclip.setVisibility(View.VISIBLE);

                       // post.setEnabled(true);

                    }
                }



            });



}