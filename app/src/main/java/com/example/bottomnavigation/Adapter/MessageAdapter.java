package com.example.bottomnavigation.Adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.bottomnavigation.Model.MessagesModel;
import com.example.bottomnavigation.R;
import com.google.android.gms.common.api.Api;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

public class MessageAdapter extends  RecyclerView.Adapter{

ArrayList<MessagesModel> messagesModels;
Context context;
int SENDER_VIEW_TYPE=1;
int RECIEVER_VIEW_TYPE=2;

    public MessageAdapter(ArrayList<MessagesModel> messagesModels, Context context) {
        this.messagesModels = messagesModels;
        this.context = context;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

       if (viewType == SENDER_VIEW_TYPE)
       {
           View view = LayoutInflater.from(context).inflate(R.layout.sample_sender,parent,false);
           return  new SenderviewHolder(view);
       }
       else
       {
           View view = LayoutInflater.from(context).inflate(R.layout.sample_reciever,parent,false);
           return new RecieverviewHolder(view);
       }




       // return null;
    }

    @Override
    public int getItemViewType(int position) {
        if (messagesModels.get(position).getUid().equals(FirebaseAuth.getInstance().getUid()))
        {
            return SENDER_VIEW_TYPE;
        }
        else {
            return RECIEVER_VIEW_TYPE;
        }


      //  return super.getItemViewType(position);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        MessagesModel messagesModel = messagesModels.get(position);
        String newtime;

        if (holder.getClass()==SenderviewHolder.class)
        {
           // ((SenderviewHolder)holder).sendertime.setText(messagesModel.getTimestamp());
            ((SenderviewHolder)holder).sendermsg.setText(messagesModel.getMessage());
         //   ((SenderviewHolder)holder).sendertime.setText(Math.toIntExact(messagesModel.getTimestamp()));
        //    ((SenderviewHolder)holder).sendermsg.setText(messagesModel.getMessage());
            newtime= getset(String.valueOf(messagesModel.getTimestamp()));
            ((SenderviewHolder)holder).sendertime.setText(newtime);


        }
        else {
            ((RecieverviewHolder)holder).recievermsg.setText(messagesModel.getMessage());
        //    ((RecieverviewHolder)holder).recievermsg.setText(messagesModel.getMessage());
          newtime= getset(String.valueOf(messagesModel.getTimestamp()));

            ((RecieverviewHolder)holder).recievertime.setText(newtime);
        }





    }

    private String getset(String valueOf) {
        long timestamp = Long.parseLong(valueOf);
        try{
            Date newdate= new Date(timestamp);
            SimpleDateFormat sfd= new SimpleDateFormat("dd.MM.yyyy", Locale.getDefault());
            return sfd.format(newdate);
        }catch (Exception e)
        {
            return  "date";
        }


    }

    @Override
    public int getItemCount() {
        return messagesModels.size();
    }

    public  class RecieverviewHolder extends RecyclerView.ViewHolder{

        TextView recievermsg, recievertime;

        public RecieverviewHolder(@NonNull View itemView) {
            super(itemView);
            recievermsg = itemView.findViewById(R.id.recievertext);
            recievertime =itemView.findViewById(R.id.recievertime);

        }
    }



    public class SenderviewHolder extends  RecyclerView.ViewHolder{

TextView sendermsg, sendertime;
        public SenderviewHolder(@NonNull View itemView) {
            super(itemView);
            sendermsg = itemView.findViewById(R.id.senderchat);
            sendertime =itemView.findViewById(R.id.sendertime);
        }
    }





}
