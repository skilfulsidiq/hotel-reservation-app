package com.skilfulsidiq.model;

public class Room implements IRoom{
    private final String roomNumber;
    private final Double price;
    private final RoomType enumeration;



    public Room(String roomNumber, Double price, RoomType enumeration) {
        this.roomNumber = roomNumber;
        this.price = price;
        this.enumeration = enumeration;
    }

    @Override
    public String toString() {
        return "Room{" +
                "roomNumber='" + roomNumber + '\'' +
                ", price=" + price +
                ", enumeration=" + enumeration +
                '}';
    }

    public String getRoomNumber() {
        return this.roomNumber;
    }

    public Double getRoomPrice() {
        return this.price;
    }

    public RoomType getRoomType() {
        return this.enumeration;
    }

    public boolean isFree() {
        return false;
    }
}
