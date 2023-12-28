package tech.titans.hotel.Controller;

import fra.uas.Service.UserService;
import fra.uas.hotel.Model.User;
import java.util.ArrayList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/users")
public class UserController {

  @Autowired
  public UserService userService;

  // Create a new User
  @PostMapping("/create")
  public ResponseEntity<?> createUser(@RequestBody User user) {
    userService.createUser(user);
    return ResponseEntity.ok(
      "User " + user.getUsername() + " created successfully"
    );
  }

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
  public List<User> getUserList() {
    return userService.getUserList();
  }
}
