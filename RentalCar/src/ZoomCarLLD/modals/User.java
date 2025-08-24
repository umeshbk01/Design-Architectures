package ZoomCarLLD.modals;

public class User {
    final String userId;
    final String name;
    final String licenseNo;
    final String phoneNumber;

    public User(String userId, String name, String licenseNo, String phoneNumber) {
        this.userId = userId;
        this.name = name;
        this.licenseNo = licenseNo;
        this.phoneNumber = phoneNumber;
    }

    @Override
    public String toString() {
        return String.format("%s (%s)", name, phoneNumber);
    }

}
