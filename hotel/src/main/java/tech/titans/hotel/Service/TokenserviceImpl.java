package tech.titans.hotel.Service;

import tech.titans.hotel.Repository.TokenRepository;
import java.util.UUID;

public class TokenserviceImpl implements TokenService{
    private TokenRepository tokenRepository = new TokenRepository();

    @Override
    public void createToke(String username) {
        String token = UUID.randomUUID().toString();
        tokenRepository.createOrUpdateToken(username, token);
    }

    @Override
    public void updateToken(String token) {
        // Assuming the token string also contains the username
        // The format of the token should be username:token
        String[] parts = token.split(":");
        if (parts.length == 2 && tokenRepository.isTokenValid(parts[0], parts[1])) {
            tokenRepository.createOrUpdateToken(parts[0], parts[1]);
        }
    }

    @Override
    public void deleteToken(String username) {
        tokenRepository.deleteToken(username);
    }
}
