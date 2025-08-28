package ConcurrentBooking.modals;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

public class User {
    public final String userId;
    public final String userName;
    public final Set<String> roles;

    public User(String id, String name, String... roles) {
        this.userId = id; this.userName = name; this.roles = new HashSet<>(Arrays.asList(roles));
    }
}
