package tech.titans.hotel.User;

public class User {

    private String name;
    private String email;
    private String password;

    // Konstruktor
    public User(String name, String email, String password) {
        this.name = name;
        this.email = email;
        this.password = password;
    }

    // Getter und Setter für name
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    // Getter und Setter für email
    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    // Getter und Setter für password
    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    // toString Methode
    @Override
    public String toString() {
        return "User{" +
               "name='" + name + '\'' +
               ", email='" + email + '\'' +
               ", password='" + password + '\'' +
               '}';
    }
}

