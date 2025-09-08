package LLDDropBox.models;

public class FileVersion {
    public String versionId;
    public String fileId;
    public String objectKey;
    public long sizeBytes;
    public String createdBy;
    public long createdAt;

    public FileVersion(String versionId, String fileId, String objectKey, long sizeBytes, String createdBy){
        this.versionId = versionId;
        this.fileId = fileId;
        this.objectKey = objectKey;
        this.sizeBytes = sizeBytes;
        this.createdBy = createdBy;
        this.createdAt = System.currentTimeMillis();
    }
    
}
