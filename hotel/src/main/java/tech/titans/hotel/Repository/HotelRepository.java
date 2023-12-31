package tech.titans.hotel.Repository;

import java.util.ArrayList;
import org.springframework.stereotype.Repository;
import tech.titans.hotel.Model.Hotel;

@Repository
public class HotelRepository {

  public ArrayList<Hotel> hotelList = new ArrayList<>();
}