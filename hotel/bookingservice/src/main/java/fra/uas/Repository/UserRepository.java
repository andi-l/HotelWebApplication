package fra.uas.Repository;

import tech.titans.hotel.Model.*;
import java.util.ArrayList;
import org.springframework.stereotype.Repository;

@Repository
public class UserRepository {

  public ArrayList<User> userList = new ArrayList<>();
}