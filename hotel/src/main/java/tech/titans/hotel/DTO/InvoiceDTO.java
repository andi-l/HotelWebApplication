package tech.titans.hotel.DTO;
public class InvoiceDTO {
    private int bookingId;
    private String roomType;
    private String checkInDate;
    private String checkOutDate;
    private double totalCost;


    public int getBookingId() {
        return this.bookingId;
    }

    public void setBookingId(int bookingId) {
        this.bookingId = bookingId;
    }

    public String getRoomType() {
        return this.roomType;
    }

    public void setRoomType(String roomType) {
        this.roomType = roomType;
    }

    public String getCheckInDate() {
        return this.checkInDate;
    }

    public void setCheckInDate(String checkInDate) {
        this.checkInDate = checkInDate;
    }

    public String getCheckOutDate() {
        return this.checkOutDate;
    }

    public void setCheckOutDate(String checkOutDate) {
        this.checkOutDate = checkOutDate;
    }

    public double getTotalCost() {
        return this.totalCost;
    }

    public void setTotalCost(double totalCost) {
        this.totalCost = totalCost;
    }

    @Override
    public String toString() {
        return "InvoiceDTO{" +
        "bookingId=" + bookingId +
        ", roomType='" + roomType + '\'' +
        ", checkInDate='" + checkInDate + '\'' +
        ", checkOutDate='" + checkOutDate + '\'' +
        ", totalCost=" + totalCost +
        '}';
    }
    
}
