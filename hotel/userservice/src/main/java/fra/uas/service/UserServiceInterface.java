package fra.uas.service;



import fra.uas.model.User;

import java.util.ArrayList;

public interface UserServiceInterface {

    public void createUser(User user);

    public boolean deleteUser(String username);

    public boolean usernameExists(String name);

    public ArrayList<User> getUserList();

    public boolean changePassword(String username, String newPassword);

    public User changeUsername(String oldUsername, String newUsername);

    public boolean validateUser(String username, String password);


}
