package tech.titans.hotel.Model;

public class Booking{
    String type;
    Date bookingDate;
    String hotelName;


    public Booking(String type, Date bookingDate, String hotelName) {
        this.type = type;
        this.bookingDate = bookingDate;
        this.hotelName = hotelName;
    }

    public String getType() {
        return this.type;
    }

    public void setType(String type) {
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