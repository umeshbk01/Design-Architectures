package models;

public class PlaybackResponse {
    public final String url;
    public final int bitrate;
    public final int durationSec;
    public PlaybackResponse(String url, int bitrate, int durationSec) {
        this.url = url; this.bitrate = bitrate; this.durationSec = durationSec;
    }
}
