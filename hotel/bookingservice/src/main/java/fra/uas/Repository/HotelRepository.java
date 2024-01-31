package fra.uas.Repository;

import fra.uas.Model.*;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Repository;

@Repository
public class HotelRepository {

  public ArrayList<Hotel> hotelList = new ArrayList<>();

  
  public void addHotel(Hotel hotel) {
    hotelList.add(hotel);
}

  public List<Hotel> getAllHotels() {
        return hotelList;
    }
}