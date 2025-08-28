package ConcurrentBooking.util;

import java.util.Set;

public class Hold {
    public final String holdId;
    public final String showId;
    public final String userId;
    public final Set<String> seatIds;
    public final long expiryEpochMillis; // TTL expiry

    public Hold(String holdId, String showId, String userId, Set<String> seatIds, long expiryEpochMillis) {
        this.holdId = holdId; this.showId = showId; this.userId = userId; this.seatIds = seatIds;
        this.expiryEpochMillis = expiryEpochMillis;
    }
}
