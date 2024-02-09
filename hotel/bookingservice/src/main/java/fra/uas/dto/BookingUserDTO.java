package fra.uas.dto;

import fra.uas.model.*;

public class BookingUserDTO {
        private String username;
        private Booking booking;
        
        public String getUsername() {
            return username;
        }
        public void setUsername(String username) {
            this.username = username;
        }
        public Booking getBooking() {
            return booking;
        }
        public void setBooking(Booking booking) {
            this.booking = booking;
        }
        
        
    
}