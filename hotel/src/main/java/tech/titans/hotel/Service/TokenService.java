package tech.titans.hotel.Service;

import org.springframework.stereotype.Service;
import tech.titans.hotel.Model.Token;
import tech.titans.hotel.Repository.TokenRepository;

import java.util.UUID;

@Service
public class TokenService implements TokenServiceInterface {
    private TokenRepository tokenRepository = new TokenRepository();

    @Override
    public String createToken(String username) {
        if (tokenRepository.tokenExists(username)) {
            tokenRepository.deleteToken(username);
        }
        String token = UUID.randomUUID().toString();
        tokenRepository.tokens.add(new Token(username, token));
        return token;
    }

    @Override
    public void updateToken(String token) {
        tokenRepository.updateToken(token);

    }

    @Override
    public void deleteToken(String username) {
        tokenRepository.deleteToken(username);
    }

    @Override
    public boolean isTokenValid(String token) {
        return tokenRepository.isTokenValid(token);
    }

    @Override
    public String getUsernameByToken(String token) {
        return tokenRepository.getUsernameByToken(token);
    }

    @Override
    public void changeUsernameOfToken(String token, String newUsername) {

        tokenRepository.getTokenByAuthtoken(token).setUsername(newUsername);

    }


}
