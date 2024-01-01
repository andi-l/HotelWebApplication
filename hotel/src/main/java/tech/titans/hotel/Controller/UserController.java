package tech.titans.hotel.Controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tech.titans.hotel.Model.User;
import tech.titans.hotel.Model.UserDTO;
import tech.titans.hotel.Service.TokenServiceInterface;
import tech.titans.hotel.Service.UserService;

import java.util.ArrayList;

@RestController
@RequestMapping("/users")
public class UserController {

    @Autowired
    public UserService userService;

    @Autowired
    public TokenServiceInterface tokenService;

    // Create a new User
    @PostMapping("/create")
    public ResponseEntity<?> createUser(@RequestBody User user) {
        if (userService.usernameExists(user.getUsername())) {
            return ResponseEntity.badRequest().body("Username already exists");
        }
        System.out.println(user);
        userService.createUser(user);
        return ResponseEntity.ok(
                "User " + user.getUsername() + " created successfully"
        );
    }

    // add method to see all users: + "allUsers: "+ userService.userRepository.userList

    // User Login
    @PostMapping("/login")
    public ResponseEntity<?> loginUser(@RequestBody UserDTO user) {
        if (userService.validateUser(user.getUsername(), user.getPassword())) {
            String token = tokenService.createToken(user.getUsername());
            return ResponseEntity.ok("Token: " + token);
        } else {
            return ResponseEntity.badRequest().body("Invalid credentials");
        }
    }

    // Protected endpoint
    @GetMapping("/protected")
    public ResponseEntity<?> protectedEndpoint(@RequestHeader("Authorization") String authToken) {
        if (tokenService.isTokenValid(authToken)) {
            return ResponseEntity.ok("Access to protected resource granted");
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid or expired token");
        }
    }

    // Delete a User
    @DeleteMapping("/delete/{username}")
    public ResponseEntity<?> deleteUser(@PathVariable String username) {
        userService.deleteUser(username);
        return ResponseEntity.ok("User " + username + " deleted successfully");
    }

    // Change Password
    @PutMapping("/changepassword")
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
    @PutMapping("/changeusername")
    public ResponseEntity<?> changeUsername(@RequestHeader("Authorized") String token,
                                            @RequestBody UserDTO newUsernameDTO
    ) {
        String oldUsername = tokenService.getUsernameByToken(token);
        User result = userService.changeUsername(oldUsername, newUsernameDTO.getUsername());
        if (result != null) {
            System.out.println(result);
            tokenService.changeUsernameOfToken(token, newUsernameDTO.getUsername());
            return ResponseEntity.ok("Username updated successfully" + result);
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
