package tech.titans.hotel.Service;

public interface TokenService {

    //Creates a Token for the User that is valid for 1 Hour
    public void createToke(String username);

    // The Token is being updated when the user interacts with the Service so that the login time span is longer
    public void updateToken(String token);

    //Deletes the Token when user Logs out or if the last user interaction is older than 1 hour
    public void deleteToken(String username);
}
