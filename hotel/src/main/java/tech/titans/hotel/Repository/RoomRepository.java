package tech.titans.hotel.Repository;

import tech.titans.hotel.Model.*;
import java.util.ArrayList;
import org.springframework.stereotype.Repository;

@Repository
public class RoomRepository {

  public ArrayList<Room> roomList = new ArrayList<>();
}