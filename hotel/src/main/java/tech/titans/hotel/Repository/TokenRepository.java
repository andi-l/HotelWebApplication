package tech.titans.hotel.Repository;

import java.time.LocalDateTime;
import java.util.HashMap;

public class TokenRepository {
    private HashMap<String, TokenInfo> tokens = new HashMap<>();

    public void createOrUpdateToken(String username, String token) {
        tokens.put(username, new TokenInfo(token, LocalDateTime.now().plusHours(1)));
    }

    public void deleteToken(String username) {
        tokens.remove(username);
    }

    public boolean isTokenValid(String username, String token) {
        TokenInfo tokenInfo = tokens.get(username);
        if (tokenInfo != null && tokenInfo.getToken().equals(token)) {
            return tokenInfo.getExpiry().isAfter(LocalDateTime.now());
        }
        return false;
    }

    private static class TokenInfo {
        private String token;
        private LocalDateTime expiry;

        public TokenInfo(String token, LocalDateTime expiry) {
            this.token = token;
            this.expiry = expiry;
        }

        public String getToken() {
            return token;
        }

        public LocalDateTime getExpiry() {
            return expiry;
        }
    }
}
