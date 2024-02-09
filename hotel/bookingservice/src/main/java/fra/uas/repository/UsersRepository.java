package fra.uas.repository;

import fra.uas.model.*;
import java.util.ArrayList;
import org.springframework.stereotype.Repository;

@Repository
public class UsersRepository {

  public ArrayList<User> userList = new ArrayList<>();
}