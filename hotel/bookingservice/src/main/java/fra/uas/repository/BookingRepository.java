package fra.uas.repository;

import fra.uas.model.*;
import java.util.List;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Repository;

@Repository
public class BookingRepository {

  public HashMap<String, List<Booking>> userBookings = new HashMap<>();

  public List<Booking> getAllBooking(){
      List<Booking> allBookings = new ArrayList<>();

      for (Map.Entry<String, List<Booking>> entry : userBookings.entrySet()){
            for (Booking booking: entry.getValue()){
                allBookings.add(booking);
            }

  }
      return allBookings;

  }

  public void addBookingForUser(String username, Booking booking) {
    List<Booking> bookings = userBookings.get(username);
    if (bookings == null) {
        bookings = new ArrayList<>();
        userBookings.put(username, bookings);
    }
    bookings.add(booking);
}

public Booking getBookingByUsernameAndId(String username, int bookingId) {
  List<Booking> bookings = userBookings.get(username);
  if (bookings == null) {
      return null;
  }
  for (Booking booking : bookings) {
      if (booking.getID() == bookingId) {
          return booking;
      }
  }
  return null;
}

public List<Booking> getAllBookingsByUsername(String username) {
  List<Booking> bookings = userBookings.get(username);
  if (bookings == null) {
      return new ArrayList<>();
  }
  return bookings;
}

}