package tech.titans.hotel.Service;

import tech.titans.hotel.Model.User;

import java.util.ArrayList;

public interface UserServiceInterface {

    public void createUser(User user);

    public void deleteUser(String username);

    public boolean usernameExists(String name);

    public ArrayList<User> getUserList();

    public boolean changePassword(String username, String newPassword);

    public User changeUsername(String oldUsername, String newUsername);

    public boolean validateUser(String username, String password);


}
