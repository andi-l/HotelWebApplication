package tech.titans.hotel.Controller;


import org.apache.coyote.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tech.titans.hotel.Model.User;
import tech.titans.hotel.Service.UserService;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/users")
public class UserController {

  @Autowired
  public UserService userService;

  // Create a new User
  @PostMapping("/create")
  public ResponseEntity<?> createUser(@RequestBody User user) {
    if(userService.usernameExists(user.getUsername())){
      return ResponseEntity.badRequest().body("Username already exists");
    }
    System.out.println(user);
    userService.createUser(user);
    return ResponseEntity.ok(
      "User " + user.getUsername() + " created successfully"
    );
  }


  // add method to see all users: + "allUsers: "+ userService.userRepository.userList

  // Delete a User
  @DeleteMapping("/delete/{username}")
  public ResponseEntity<?> deleteUser(@PathVariable String username) {
    userService.deleteUser(username);
    return ResponseEntity.ok("User " + username + " deleted successfully");
  }

  // Change Password
  @PutMapping("/changePassword")
  public ResponseEntity<?> changePassword(
    @RequestParam String username,
    @RequestParam String newPassword
  ) {
    boolean result = userService.changePassword(username, newPassword);
    if (result) {
      return ResponseEntity.ok("Password updated successfully");
    } else {
      return ResponseEntity.badRequest().body("User not found");
    }
  }

  // Change Username
  @PutMapping("/changeUsername")
  public ResponseEntity<?> changeUsername(
    @RequestParam String oldUsername,
    @RequestParam String newUsername
  ) {
    boolean result = userService.changeUsername(oldUsername, newUsername);
    if (result) {
      return ResponseEntity.ok("Username updated successfully");
    } else {
      return ResponseEntity
        .badRequest()
        .body("Username not updated, possibly already exists");
    }
  }

  // Get list of all Users
  @GetMapping("/list")
  public ArrayList<User> getUserList() {
    return userService.getUserList();
  }
}
