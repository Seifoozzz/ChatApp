package com.example.mychatapp2.ui_AddRoom;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.mychatapp2.DataBase.Models.Room;
import com.example.mychatapp2.R;

import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class RoomsAdabter extends RecyclerView.Adapter<RoomsAdabter.ViewHolder> {
    List <Room> RoomsList;
    Context context;

List<Room>rooms;



    public RoomsAdabter(Context context, List<Room> rooms) {
        this.rooms = rooms;
        this.context = context;
    }
    public void changeData(List<Room> rooms){
        this.rooms=rooms;
        notifyDataSetChanged();
    }

    public void remove(int adapterPosition) {
        rooms.remove(adapterPosition);
        notifyItemRemoved(adapterPosition);
    }
    @NonNull
    @Override
    public RoomsAdabter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_room,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RoomsAdabter.ViewHolder holder, final int position) {
final Room room=rooms.get(position);
holder.RoomName.setText(room.getRoomName());
holder.RoomDesc.setText(room.getRoomDesc());
        Glide.with(context).load(room.getImage()).into(holder.RoomImage);


      if (onItemClickListener!=null){
          holder.itemView.setOnClickListener(new View.OnClickListener() {
              @Override
              public void onClick(View view) {
                  onItemClickListener.onItemClick(position,room);
              }
          });
      }
    }

    @Override
    public int getItemCount() {
       return rooms == null ?0: rooms.size();
    }
    public void setOnItemClickListener(RoomsAdabter.onItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    onItemClickListener onItemClickListener;
    public interface onItemClickListener{
        void onItemClick(int position,Room room);
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView RoomName,RoomDesc;
        CircleImageView RoomImage;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            RoomName=itemView.findViewById(R.id.roomName);
            RoomDesc=itemView.findViewById(R.id.roomDescription);
            RoomImage=itemView.findViewById(R.id.roomUserImage);
        }
    }
}
