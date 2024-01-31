package fra.uas.DTO;

public class BookingDTO {

    private String roomType;
    private String checkInDate;
    private String checkOutDate;
    private int capacity;

    public String getRoomType() {
        return roomType;
    }
    public void setRoomType(String roomType) {
        this.roomType = roomType;
    }
    public String getCheckInDate() {
        return checkInDate;
    }
    public void setCheckInDate(String checkInDate) {
        this.checkInDate = checkInDate;
    }
    public String getCheckOutDate() {
        return checkOutDate;
    }
    public void setCheckOutDate(String checkOutDate) {
        this.checkOutDate = checkOutDate;
    }
    public int getCapacity() {
        return capacity;
    }
    public void setCapacity(int capacity) {
        this.capacity = capacity;
    }

    
}