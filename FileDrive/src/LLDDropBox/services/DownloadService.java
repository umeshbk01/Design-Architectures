package LLDDropBox.services;

import java.util.Optional;

import LLDDropBox.models.FileVersion;
import LLDDropBox.models.MetadataStore;
import LLDDropBox.models.ObjectStore;

public class DownloadService {
    public final ObjectStore objectStore;
    public final MetadataStore metaStore;

    public DownloadService(ObjectStore objectStore, MetadataStore metaStore){
        this.objectStore = objectStore;
        this.metaStore = metaStore;
    }
    public Optional<String> getDownloadForUrlVersion(String versionId){
        Optional<FileVersion> version = metaStore.getVersion(versionId);
        return version.map(fileVersion -> objectStore.getObjectUrl(fileVersion.objectKey));
    }

    public Optional<byte[]> downloadVersionBytes(String versionId) {
            Optional<FileVersion> v = metaStore.getVersion(versionId);
            if (!v.isPresent()) return Optional.empty();
            return Optional.ofNullable(objectStore.getObject(v.get().objectKey));
        }
}
