package LLDDropBox.models;

import java.util.ArrayList;
import java.util.List;

public class FileMetadata {
    public String fileId;
    public String fileName;
    public String ownerId;
    public volatile String currentVersionId;
    public List<String> versionIds;
    volatile public long modifiedAt;

    public FileMetadata(String fileId, String fileName, String ownerId){
        this.fileId = fileId;
        this.fileName = fileName;
        this.ownerId = ownerId;
        this.versionIds = new ArrayList<>();
        this.modifiedAt = System.currentTimeMillis();
    }
}
