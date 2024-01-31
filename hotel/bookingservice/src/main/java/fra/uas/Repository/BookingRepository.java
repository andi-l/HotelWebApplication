package fra.uas.Repository;

import tech.titans.hotel.Model.*;
import java.util.List;
import java.util.ArrayList;
import java.util.HashMap;

import org.springframework.stereotype.Repository;

@Repository
public class BookingRepository {

  private HashMap<String, List<Booking>> userBookings = new HashMap<>();

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