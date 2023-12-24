package fra.uas.Repository;

import fra.uas.Room.Model.Room;
import java.util.ArrayList;
import org.springframework.stereotype.Repository;

@Repository
public class HotelRepository {

  public ArrayList<Room> hotelList = new ArrayList<>();
}