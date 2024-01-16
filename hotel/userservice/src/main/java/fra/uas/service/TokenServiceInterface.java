package fra.uas.service;

public interface TokenServiceInterface {

    //Creates a Token for the User that is valid for 1 Hour
    public String createToken(String username);

    // The Token is being updated when the user interacts with the Service so that the login time span is longer
    public void updateToken(String token);

    //Deletes the Token when user Logs out or if the last user interaction is older than 1 hour
    public void deleteToken(String username);

    public boolean isTokenValid(String token);

    public String getUsernameByToken(String token);

    public void changeUsernameOfToken(String token, String newUsername);


}
