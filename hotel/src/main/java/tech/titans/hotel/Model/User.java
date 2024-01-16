package tech.titans.hotel.Model;

public class User {

    private String username;
    private String firstName;
    private String lastName;
    private String email;
    private String password;
    private Role role;

    // Full Constructor
    public User(
            String username,
            String firstName,
            String lastName,
            String email,
            String password
    ) {
        this.username = username;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.password = password;
        this.role = Role.USER;
    }


    public String getUsername() {
        return this.username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getFirstName() {
        return this.firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return this.lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public String toString() {
        return (
                "User {" +
                        "username='" +
                        getUsername() +
                        "'" +
                        ", firstName='" +
                        getFirstName() +
                        "'" +
                        ", lastName='" +
                        getLastName() +
                        "'" +
                        ", email='" +
                        getEmail() +
                        "'" +
                        ", password='" +
                        getPassword() +
                        "'" +
                        "}\n"
        );
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }
}
