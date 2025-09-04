package services;

import java.time.Duration;
import java.util.Comparator;

import models.PlaybackResponse;
import models.Track;

public class PlaybackService {
    private final MetadataService metadataService;
    private final SignedUrlGenerator urlGenerator;

    public PlaybackService(MetadataService metadataService, SignedUrlGenerator urlGenerator){
        this.metadataService = metadataService;
        this.urlGenerator = urlGenerator;
    }

    public PlaybackResponse startPlayback(String userId, String trackId, int requestedBitrate){
        Track track = metadataService.getTrack(trackId);
        if(track == null) throw new IllegalArgumentException("Invalid trackId");

        int bitrate = chooseBitrate(track, requestedBitrate);
        String objectPath = track.bitrateUrl.get(bitrate);
        String signedUrl = urlGenerator.generateSignedUrl(objectPath, userId, Duration.ofMinutes(5));
        
        return new PlaybackResponse(signedUrl, bitrate, track.duration);
    }
    private int chooseBitrate(Track track, int requestedBitrate){
       return track.bitrateUrl.keySet().stream()
       .sorted()
       .min(Comparator.comparingInt(b -> Math.abs(b - requestedBitrate)))
       .orElse(track.bitrateUrl.keySet().iterator().next()); 
    }
}
