package com.example.innfystays;

public class Roomdetails {

    private String RoomId,RoomNumber,RoomCapacity,RoomAvailabilty,RoomImage;





    public String getRoomId() {
        return RoomId;
    }

    public void setRoomId(String roomId) {
        RoomId = roomId;
    }

    public String getRoomNumber() {
        return RoomNumber;
    }

    public void setRoomNumber(String roomNumber) {
        RoomNumber = roomNumber;
    }

    public String getRoomCapacity() {
        return RoomCapacity;
    }

    public void setRoomCapacity(String roomCapacity) {
        RoomCapacity = roomCapacity;
    }

    public String getRoomAvailabilty() {
        return RoomAvailabilty;
    }

    public void setRoomAvailabilty(String roomAvailabilty) {
        RoomAvailabilty = roomAvailabilty;
    }

    public String getRoomImage() {
        return RoomImage;
    }

    public void setRoomImage(String roomImage) {
        RoomImage = roomImage;
    }

    public Roomdetails() {
    }

    public Roomdetails(String roomId, String roomNumber, String roomCapacity, String roomAvailabilty, String roomImage) {
        RoomId = roomId;
        RoomNumber = roomNumber;
        RoomCapacity = roomCapacity;
        RoomAvailabilty = roomAvailabilty;
        RoomImage = roomImage;
    }
}
