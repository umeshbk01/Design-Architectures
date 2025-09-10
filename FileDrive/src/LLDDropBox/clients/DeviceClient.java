package LLDDropBox.clients;

import java.util.List;
import java.util.Optional;

import LLDDropBox.models.Op;
import LLDDropBox.services.DownloadService;
import LLDDropBox.services.SyncService;

public class DeviceClient implements Runnable{
    public final String deviceId;
    public final String userId;
    public final SyncService syncService;
    public final DownloadService downloadService;
    public volatile long lastSeenSeq = 0;
    public volatile boolean running =true;

    public DeviceClient(String deviceId, String userId, SyncService syncService, DownloadService downloadService){
        this.deviceId = deviceId;
        this.userId = userId;
        this.syncService = syncService;
        this.downloadService = downloadService;
    }
    public void stop(){
        running = false;
    }

    @Override
    public void run(){
        System.out.println("Device " + deviceId + " for user " + userId + " started.");
        while(running){
            try{
                List<Op> ops = syncService.fetchOps(userId, lastSeenSeq, 10);
                if(!ops.isEmpty()){
                    for(Op op: ops){
                        System.out.println("Device " + deviceId + " processing op: " + op);
                        Optional<byte[]> bytes = downloadService.downloadVersionBytes(op.versionId);
                        if(bytes.isPresent()){
                            System.out.println("Device " + deviceId + " downloaded " + bytes.get().length + " bytes for file " + op.fileId + " version " + op.versionId);
                        } else {
                            System.out.println("Device " + deviceId + " failed to download bytes for file " + op.fileId + " version " + op.versionId);
                        }
                        lastSeenSeq = Math.max(lastSeenSeq, op.seq);
                    }
                }
                Thread.sleep(500);
            }catch(InterruptedException e){
                Thread.currentThread().interrupt();
                break;
            }
    }
        System.out.println("Device " + deviceId + " for user " + userId + " stopped.");
    }   
}
