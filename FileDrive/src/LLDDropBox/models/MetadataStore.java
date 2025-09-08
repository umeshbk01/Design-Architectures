package LLDDropBox.models;

import java.util.Map;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

public class MetadataStore {
    public Map<String, FileMetadata> files = new ConcurrentHashMap<>();
    public Map<String, FileVersion> versions = new ConcurrentHashMap<>();

    public FileMetadata createFile(String ownerId, String fileName){
        String fileId = UUID.randomUUID().toString();
        FileMetadata metadata = new FileMetadata(fileId, fileName, ownerId);
        files.put(fileId, metadata);
        return metadata;
    }

    public Optional<FileMetadata> getFile(String fileId){
        return Optional.ofNullable(files.get(fileId));
    }

    public void addVersion(FileVersion version){
        versions.put(version.versionId, version);
        FileMetadata metadata = files.get(version.fileId);
        if(metadata != null){
            synchronized (metadata){
                metadata.versionIds.add(version.versionId);
                metadata.currentVersionId = version.versionId;
                metadata.modifiedAt = System.currentTimeMillis();
            }
        }
    }
    public Optional<FileVersion> getVersion(String versionId) {
            return Optional.ofNullable(versions.get(versionId));
    }
}
