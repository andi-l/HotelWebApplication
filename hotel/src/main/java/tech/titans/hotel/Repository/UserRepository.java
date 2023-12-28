package fra.uas.hotel.Repository;

import fra.uas.hotel.model.User;
import java.util.ArrayList;
import org.springframework.stereotype.Repository;

@Repository
public class UserRepository {

  public ArrayList<User> userList = new ArrayList<>();
}
