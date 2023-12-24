package tech.titans.hotel.Model;

public class Booking{
    Room type;
    Date bookingDate;
    String hotelName;


    public Booking(Room type, Date bookingDate, String hotelName) {
        this.type = type;
        this.bookingDate = bookingDate;
        this.hotelName = hotelName;
    }

    public Room getType() {
        return this.type;
    }

    public void setType(Room type) {
        this.type = type;
    }

    public Date getBookingDate() {
        return this.bookingDate;
    }

    public void setBookingDate(Date bookingDate) {
        this.bookingDate = bookingDate;
    }

    public String getHotelName() {
        return this.hotelName;
    }

    public void setHotelName(String hotelName) {
        this.hotelName = hotelName;
    }

}