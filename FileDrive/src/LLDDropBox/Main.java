package LLDDropBox;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import LLDDropBox.clients.DeviceClient;
import LLDDropBox.models.FileMetadata;
import LLDDropBox.models.FileVersion;
import LLDDropBox.models.InMemoryS3Store;
import LLDDropBox.models.MetadataStore;
import LLDDropBox.models.ObjectStore;
import LLDDropBox.services.DownloadService;
import LLDDropBox.services.SyncService;
import LLDDropBox.services.UploadService;

public class Main {
    public static void main(String[] args) throws Exception{
        ObjectStore s3 = new InMemoryS3Store();
        MetadataStore metaStore = new MetadataStore();
        SyncService syncService = new SyncService();
        UploadService uploadService = new UploadService(s3, metaStore, syncService);
        DownloadService downloadService = new DownloadService(s3, metaStore);

        // Simulate a user with two devices
        String userId = "user-123";
        DeviceClient deviceA = new DeviceClient("device-A", userId, syncService, downloadService);
        DeviceClient deviceB = new DeviceClient("device-B", userId, syncService, downloadService);

        ExecutorService exec = Executors.newFixedThreadPool(2);
        exec.submit(deviceA);
        exec.submit(deviceB);

        // Device A uploads a file and commits
        System.out.println("\n--- Device A uploads v1 ---");
        byte[] contentV1 = "Hello world version 1".getBytes();
        String objKeyV1 = uploadService.uploadBytes(contentV1);
        String versionId1 = uploadService.commitVersion(userId, null, "notes.txt", objKeyV1, contentV1.length);
        System.out.println("Committed versionId: " + versionId1);

        // Wait a little for devices to pick up the op
        Thread.sleep(1500);

        // Device A updates file -> v2
        System.out.println("\n--- Device A uploads v2 ---");
        byte[] contentV2 = "Hello world version 2 - updated".getBytes();
        String objKeyV2 = uploadService.uploadBytes(contentV2);

        // find fileId (we can look up from metadata store: find the file created above)
        // In this demo there's only one file in meta store; get its id:
        String fileId = metaStore.files.keySet().iterator().next();
        String versionId2 = uploadService.commitVersion(userId, fileId, null, objKeyV2, contentV2.length);
        System.out.println("Committed versionId: " + versionId2);

        Thread.sleep(1500);

        // Stop device polling
        deviceA.stop();
        deviceB.stop();
        exec.shutdown();
        exec.awaitTermination(2, TimeUnit.SECONDS);

        // Show final metadata
        FileMetadata meta = metaStore.getFile(fileId).get();
        System.out.println("\nFinal metadata for fileId " + fileId + ": name=" + meta.fileName + ", currentVersion=" + meta.currentVersionId + ", history=" + meta.versionIds);
        for (String vId : meta.versionIds) {
            FileVersion v = metaStore.getVersion(vId).get();
            System.out.println("  version=" + v.versionId + " objectKey=" + v.objectKey + " size=" + v.sizeBytes + " createdBy=" + v.createdBy);
            System.out.println("    downloadUrl=" + s3.getObjectUrl(v.objectKey));
        }
    } 
}
