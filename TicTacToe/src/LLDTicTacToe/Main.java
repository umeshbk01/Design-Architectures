package LLDTicTacToe;

public class Main {
    private static final long EPOCH = 1704067200000L; // Custom epoch: Jan 1, 2024
    private static final int MACHINE_ID_BITS = 6;
    private static final int SEQUENCE_BITS = 6;
    private static final int MAX_SEQUENCE = (1 << SEQUENCE_BITS) - 1;
    private static final String BASE62 = "0123456789abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ";

    private final int machineId;
    private long lastTimestamp = -1L;
    private int sequence = 0;

    public Main(int machineId) {
        if (machineId > ((1 << MACHINE_ID_BITS) - 1)) {
            throw new IllegalArgumentException("Invalid Machine ID");
        }
        this.machineId = machineId;
    }

    public synchronized String generateShortURL() {
        long timestamp = System.currentTimeMillis() - EPOCH;

        if (timestamp == lastTimestamp) {
            sequence = (sequence + 1) & MAX_SEQUENCE;
            if (sequence == 0) {
                // Sequence exhausted, wait for the next millisecond
                timestamp = waitNextMillis(lastTimestamp);
            }
        } else {
            sequence = 0;
        }

        lastTimestamp = timestamp;

        // Combine timestamp, machineId, and sequence into a 42-bit ID
        long id = (timestamp << (MACHINE_ID_BITS + SEQUENCE_BITS))
                | (machineId << SEQUENCE_BITS)
                | sequence;

        return encodeBase62(id);
    }

    private long waitNextMillis(long lastTimestamp) {
        long timestamp = System.currentTimeMillis() - EPOCH;
        while (timestamp <= lastTimestamp) {
            timestamp = System.currentTimeMillis() - EPOCH;
        }
        return timestamp;
    }

    private String encodeBase62(long num) {
        StringBuilder sb = new StringBuilder();
        System.out.println("id: "+num);
        while (num > 0) {
            sb.append(BASE62.charAt((int) (num % 62)));
            num /= 62;
        }
        while (sb.length() < 7) { // Ensure fixed length of 7 characters
            sb.append('0');
        }
        return sb.reverse().toString();
    }

    public static void main(String[] args) {
        Main shortener = new Main(16); // Machine ID = 3
        System.out.println(shortener.generateShortURL());
    }
}
