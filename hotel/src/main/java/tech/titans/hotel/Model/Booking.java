package tech.titans.hotel.Model;

import java.util.Date;

public class Booking {
    private String roomType;
    private int roomCapacity;
    private Date checkInDate;
    private Date checkOutDate;
    private String hotelName;
    

    public Booking(String roomType, Date checkInDate, Date checkOutDate, int roomCapacity) {
        this.roomType = roomType;
        this.checkInDate = checkInDate;
        this.checkOutDate = checkOutDate;
        this.roomCapacity = roomCapacity;
        this.hotelName = "HotelA";
    }

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

    // public String getHotelName() {
    //     return hotelName;
    // }

    // public void setHotelName(String hotelName) {
    //     this.hotelName = hotelName;
    // }

    public int getRoomCapacity(){
        return roomCapacity;
    }

    public void setRoomCapacity(int roomCapacity){
        this.roomCapacity = roomCapacity;
 
    }

    @Override
public String toString() {
    return "Booking{" +
           "roomType='" + roomType + '\'' +
           ", roomCapacity=" + roomCapacity +
           ", checkInDate=" + checkInDate +
           ", checkOutDate=" + checkOutDate +
           ", hotelName='" + hotelName + '\'' +
           '}';
}

}