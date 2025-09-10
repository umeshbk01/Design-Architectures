package LLDDropBox.services;

import java.util.UUID;

import LLDDropBox.models.FileMetadata;
import LLDDropBox.models.FileVersion;
import LLDDropBox.models.MetadataStore;
import LLDDropBox.models.ObjectStore;

public class UploadService {
    public ObjectStore objectStore;
    public MetadataStore metaStore;
    public SyncService syncService;

    public UploadService(ObjectStore objectStore, MetadataStore metaStore, SyncService syncService){
        this.objectStore = objectStore;
        this.metaStore = metaStore;
        this.syncService = syncService;
    }

    public String uploadBytes(byte[] data){
        return objectStore.putObject(data);

    }
    public String commitVersion(String userId, String fileId, String fileName, String objectKey, long sizeBytes){
        FileMetadata meta;
        boolean isNew = false;
        if(fileId == null){
            meta = metaStore.createFile(userId, fileName);
            isNew = true;
        } else {
            meta = metaStore.getFile(fileId).orElseThrow(() -> new RuntimeException("File not found: " + fileId));
        }
        System.out.println("[UploadService] Committing new version for fileId=" + meta.fileId + ", objectKey=" + objectKey);
        String versionId = UUID.randomUUID().toString();
        FileVersion version = new FileVersion(versionId, meta.fileId, objectKey, sizeBytes, userId);
        System.out.println("[UploadService] Committed " + version.versionId);
        
        metaStore.addVersion(version);
        syncService.publishOp(meta.ownerId, meta.fileId, versionId,  isNew ? "CREATE" : "UPDATE");
       return versionId;
    }
}