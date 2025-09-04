package services;

import java.time.Duration;
import java.util.Base64;

public class SimpleSignedUrlGenerator implements SignedUrlGenerator{
    private final String secret = "secret";
    @Override
    public String generateSignedUrl(String objectPath, String clientId, Duration ttl){
        long expiry = System.currentTimeMillis() + ttl.toMillis();
        String token = Base64.getEncoder().encodeToString((objectPath + "|" + clientId + "|" + expiry + "|" + secret).getBytes());
        return "https://cdn.example.com/" + objectPath + "?token=" + token + "&expiry=" + expiry;
    }
    
}
