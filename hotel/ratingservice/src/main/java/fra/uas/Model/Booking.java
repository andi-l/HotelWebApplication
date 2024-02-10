package fra.uas.model;

import lombok.Data;

import java.util.Date;


import java.util.Date;

@Data
public class Booking {
    private static int counter = 1;
    private int id;
    private String roomType;
    private int roomCapacity;
    private Date checkInDate;
    private Date checkOutDate;

    public Booking(String roomType, Date checkInDate, Date checkOutDate, int roomCapacity) {
        this.id = counter++;
        this.roomType = roomType;
        this.checkInDate = checkInDate;
        this.checkOutDate = checkOutDate;
        this.roomCapacity = roomCapacity;
    }

    @Override
    public String toString() {
        return "Booking [id=" + id + ", roomType=" + roomType + ", roomCapacity=" + roomCapacity + ", checkInDate="
                + checkInDate + ", checkOutDate=" + checkOutDate + "]";
    }
}