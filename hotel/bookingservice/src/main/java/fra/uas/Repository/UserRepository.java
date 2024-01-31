package fra.uas.Repository;

import fra.uas.Model.*;
import java.util.ArrayList;
import org.springframework.stereotype.Repository;

@Repository
public class UserRepository {

  public ArrayList<User> userList = new ArrayList<>();
}