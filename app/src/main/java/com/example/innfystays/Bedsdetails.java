package com.example.innfystays;

public class Bedsdetails {
    String BedId,RoomId;
    String BedState;

    public String getBedId() {
        return BedId;
    }

    public void setBedId(String bedId) {
        BedId = bedId;
    }

    public String getRoomId() {
        return RoomId;
    }

    public void setRoomId(String roomId) {
        RoomId = roomId;
    }

    public String getBedState() {
        return BedState;
    }

    public void setBedState(String bedState) {
        BedState = bedState;
    }

    public Bedsdetails() {
    }

    public Bedsdetails(String bedId, String roomId, String bedState) {
        BedId = bedId;
        RoomId = roomId;
        BedState = bedState;
    }
}
