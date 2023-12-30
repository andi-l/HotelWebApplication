package tech.titans.hotel.Repository;

import tech.titans.hotel.Model.*;
import java.util.ArrayList;
import org.springframework.stereotype.Repository;

@Repository
public class HotelRepository {

  public ArrayList<Hotel> hotelList = new ArrayList<>();

  
  public void addHotel(Hotel hotel) {
    hotelList.add(hotel);
}
}