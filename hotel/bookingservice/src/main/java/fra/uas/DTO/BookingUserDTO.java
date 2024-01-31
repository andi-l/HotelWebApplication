package fra.uas.DTO;

import fra.uas.Model.*;

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