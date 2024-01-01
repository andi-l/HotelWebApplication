package tech.titans.hotel.Service;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tech.titans.hotel.Model.User;
import tech.titans.hotel.Repository.UserRepository;

import java.util.ArrayList;

@Service
public class UserService implements UserServiceInterface {

    @Autowired
    public UserRepository userRepository;

    // Create a User
    @Override
    public void createUser(User user) {
        userRepository.userList.add(user);
        System.out.println("New User " + user.getFirstName() + " created!");
    }

    // Delete a User
    @Override
    public void deleteUser(String username) {
        userRepository.userList.removeIf(user -> user.getUsername().equals(username));
    }

    // Check if Username already exists

    public boolean usernameExists(String name) {
        return userRepository.userList.stream()
                .anyMatch(user -> user.getUsername().equalsIgnoreCase(name));
    }

    // Return the userRepository
    @Override
    public ArrayList<User> getUserList() {

        return userRepository.userList;
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
}
