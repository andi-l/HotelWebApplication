package fra.uas.service;


import fra.uas.model.Role;
import fra.uas.model.User;
import fra.uas.repository.UserRepository;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import java.util.ArrayList;

@Service
public class UserService implements UserServiceInterface {

    @Autowired
    public UserRepository userRepository;

    // Create a User
    @Override
    public void createUser(User user) {
        userRepository.userList.add(user);
        System.out.println("New User " + user.getUsername() + " created!");
    }

    // Delete a User
    @Override
    public boolean deleteUser(String username) {
        boolean existed = userRepository.userList.removeIf(user -> user.getUsername().equals(username));
        System.out.println(existed ? "User deleted." : "User not found.");
        return existed;
    }

    // Check if Username already exists
    public boolean usernameExists(String name) {
        return userRepository.userList.stream()
                .anyMatch(user -> user.getUsername().equalsIgnoreCase(name));
    }

    // Return the userRepository
    @Override
    public ArrayList<User> getUserList() {
        ArrayList<User> userList = new ArrayList<>();
        for (User user : userRepository.userList) {
            if (user.getRole() == Role.USER) {
                userList.add(user);
            }
        }
        return userList;
    }

    // Change Password
    @Override
    public boolean changePassword(String username, String newPassword) {
        for (User user : userRepository.userList) {
            if (user.getUsername().equals(username)) {
                user.setPassword(newPassword);
                System.out.println("Password for user " + username + " has been updated.");
                return true;
            }
        }
        System.out.println("User not found.");
        return false;


    }

    //Change Username
    @Override
    public User changeUsername(String oldUsername, String newUsername) {
        for (User user : userRepository.userList) {
            if (user.getUsername().equals(oldUsername)) {
                if (!usernameExists(newUsername)) {
                    user.setUsername(newUsername);
                    return user;
                } else {
                    System.out.println("Username already exists.");
                    return null;
                }
            }
        }
        return null;
    }

    @Override
    public boolean validateUser(String username, String password) {
        return userRepository.userList.stream()
                .anyMatch(user -> user.getUsername().equalsIgnoreCase(username) && user.getPassword().equals(password));
    }

    @PostConstruct
    private void createAdmin() {
        User user = new User("Paladin", "Andi", "Latifi", "admin@gmail.com", "secret123");
        user.setRole(Role.ADMIN);
        userRepository.userList.add(user);
    }

    public User getUser(String username) {
        for (User user : userRepository.userList) {
            if (user.getUsername().equals(username)) {
                return user;
            }
        }
        return null;
    }
}
