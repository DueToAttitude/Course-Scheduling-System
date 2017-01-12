package com.bean;

/**
 * Created by recycle on 12/15 0015.
 */
public class RoomBean {
    private int idRoom;
    private String roomName;
    private int roomScheduled;

    public int getRoomScheduled() {
        return roomScheduled;
    }

    public void setRoomScheduled(int roomScheduled) {
        this.roomScheduled = roomScheduled;
    }

    public int getIdRoom() {
        return idRoom;
    }

    public void setIdRoom(int idRoom) {
        this.idRoom = idRoom;
    }

    public String getRoomName() {
        return roomName;
    }

    public void setRoomName(String roomName) {
        this.roomName = roomName;
    }
}
