package fra.uas.Repositories;

import fra.uas.Model.*;
import java.util.ArrayList;
import org.springframework.stereotype.Repository;

@Repository
public class RoomRepository {

  public ArrayList<Room> roomList = new ArrayList<>();
}