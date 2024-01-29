package tech.titans.hotel.Repository;

import tech.titans.hotel.Model.*;
import java.util.ArrayList;

import org.springframework.stereotype.Repository;
import tech.titans.hotel.Model.User;

@Repository
public class UserRepository {

  public ArrayList<User> userList = new ArrayList<>();
}
