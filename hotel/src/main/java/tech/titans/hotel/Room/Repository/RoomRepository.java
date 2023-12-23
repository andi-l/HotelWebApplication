package fra.uas.Room.Repository;

import fra.uas.Room.Model.Room;
import java.util.ArrayList;
import org.springframework.stereotype.Repository;

@Repository
public class RoomRepository {

  public ArrayList<Room> roomList = new ArrayList<>();
}