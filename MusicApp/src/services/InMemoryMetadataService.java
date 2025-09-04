package services;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import models.Track;

public class InMemoryMetadataService implements MetadataService{
    private final Map<String, Track> store = new ConcurrentHashMap<>();
    
    @Override
    public Track getTrack(String trackId) {
        return store.get(trackId);
    }
}
