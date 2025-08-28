package ConcurrentBooking.services;

import java.util.Map;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

import ConcurrentBooking.modals.User;

public class AuthService {
    private final Map<String, User> tokens = new ConcurrentHashMap<>();
    public String login(User user) {
        String token = "tok-" + UUID.randomUUID().toString();
        tokens.put(token, user);
        return token;
    }
    
    public Optional<User> authenticate(String token) {
        return Optional.ofNullable(tokens.get(token));
    }
}
