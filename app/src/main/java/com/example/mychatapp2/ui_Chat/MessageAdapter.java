package com.example.mychatapp2.ui_Chat;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.mychatapp2.DataBase.Datautils;
import com.example.mychatapp2.DataBase.Models.Message;
import com.example.mychatapp2.R;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class MessageAdapter extends RecyclerView.Adapter<MessageAdapter.ViewHolder> {
    List<Message> messages;
    Context context;

  public void changeData(List<Message>messages){
      this.messages=messages;
      notifyDataSetChanged();
  }

    public MessageAdapter(Context context,List<Message> messages) {
        this.context = context;
        this.messages = messages;
    }
    public static final int  RECIVED=10;
    public static final int  SENt=5;

    @Override
    public int getItemViewType(int position) {
        Message message=messages.get(position);
         if (message.getSenderId().equals(Datautils.CurrentUser.getId()))
             return SENt;
         else
             return RECIVED;
    }

    @NonNull
    @Override
    public MessageAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view=null;
        if (viewType==SENt){
         view= LayoutInflater.from(parent.getContext()).inflate(R.layout.message_sent,parent,false);
        }
        else
            view =LayoutInflater.from(parent.getContext()).inflate(R.layout.message_received,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MessageAdapter.ViewHolder holder, int position) {
        int viewType=getItemViewType(position);
         Message message=messages.get(position);
       if (viewType==SENt){
           holder.content.setText(message.getContent());
           holder.time.setText(message.getTime());
       }
       else {
           holder.sendername.setText(message.getSenderName());
           holder.content.setText(message.getContent());

           holder.time.setText(message.getTime());
       }
    }

    @Override
    public int getItemCount() {
        return messages == null ?0: messages.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView content,time,sendername;
        CircleImageView image;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            content=itemView.findViewById(R.id.content);
            time =itemView.findViewById(R.id.time);
            sendername=itemView.findViewById(R.id.senderName);
            image=itemView.findViewById(R.id.roomUserImage);
        }
    }
}
