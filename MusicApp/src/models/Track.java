package models;
import java.util.Map;

public class Track {
    public String id;
    public String title;
    public String artist;
    public int duration;
    public final Map<Integer, String> bitrateUrl;

    public Track(String id, String title, String artist, int duration, Map<Integer, String> bitrateUrl) {
        this.id = id;
        this.title = title;
        this.artist = artist;
        this.duration = duration;
        this.bitrateUrl = bitrateUrl;
    }
}
