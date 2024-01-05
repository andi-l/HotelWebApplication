package tech.titans.hotel.Repository;

import java.util.ArrayList;
import org.springframework.stereotype.Repository;
import tech.titans.hotel.Model.Booking;

@Repository
public class BookingRepository {

  public ArrayList<Booking> bookingList = new ArrayList<>();
}