package fra.uas.Model;

import java.util.Date;


import java.util.Date;

public class Booking {
    private static int counter = 1;
    private int id;
    private String roomType;
    private int roomCapacity;
    private Date checkInDate;
    private Date checkOutDate;

//    public Booking(String roomType, Date checkInDate, Date checkOutDate, int roomCapacity) {
//        this.id = counter++;
//        this.roomType = roomType;
//        this.checkInDate = checkInDate;
//        this.checkOutDate = checkOutDate;
//        this.roomCapacity = roomCapacity;
//        this.hotelName = "Tech Titans Hotel";
//    }

    public String getRoomType() {
        return roomType;
    }

    public void setRoomType(String roomType) {
        this.roomType = roomType;
    }

    public Date getCheckInDate() {
        return checkInDate;
    }

    public void setCheckInDate(Date checkInDate) {
        this.checkInDate = checkInDate;
    }

    public Date getCheckOutDate() {
        return checkOutDate;
    }

    public void setCheckOutDate(Date checkOutDate) {
        this.checkOutDate = checkOutDate;
    }

    public int getRoomCapacity(){
        return roomCapacity;
    }

    public void setRoomCapacity(int roomCapacity){
        this.roomCapacity = roomCapacity;

    }
    public int getID(){
        return id;
    }

    public static int getCounter() {
        return counter;
    }

    public static void setCounter(int counter) {
        Booking.counter = counter;
    }

    @Override
    public String toString() {
        return "Booking [id=" + id + ", roomType=" + roomType + ", roomCapacity=" + roomCapacity + ", checkInDate="
                + checkInDate + ", checkOutDate=" + checkOutDate  + "]";
    }
}