package services;

import java.time.Duration;

public interface SignedUrlGenerator {
    String generateSignedUrl(String objectPath, String clientId, Duration ttl);
}
