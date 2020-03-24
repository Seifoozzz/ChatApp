package com.example.mychatapp2.DataBase.Models;

public class Room {
    String RoomName;
    String RoomDesc;
    String RoomId;
    String Image;
    String adminUser;

    public String getAdminUser() {
        return adminUser;
    }

    public void setAdminUser(String adminUser) {
        this.adminUser = adminUser;
    }

    public Room(){

    }
    public String getRoomName() {
        return RoomName;
    }

    public void setRoomName(String roomName) {
        RoomName = roomName;
    }

    public String getRoomDesc() {
        return RoomDesc;
    }

    public void setRoomDesc(String roomDesc) {
        RoomDesc = roomDesc;
    }

    public String getRoomId() {
        return RoomId;
    }

    public void setRoomId(String roomId) {
        RoomId = roomId;
    }

    public String getImage() {
        return Image;
    }

    public void setImage(String image) {
        Image = image;
    }
}
