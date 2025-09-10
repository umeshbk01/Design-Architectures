package LLDDropBox.services;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;

import LLDDropBox.models.Op;

public class SyncService {
    public AtomicLong seqGen = new AtomicLong(1);
    public Map<String, List<Op>> userOpLogs = new ConcurrentHashMap<>();

    public Op publishOp(String userId, String fileId, String versionId, String opType){
        long seq = seqGen.getAndIncrement();
        Op op = new Op(seq, userId, fileId, versionId, opType);
        userOpLogs.computeIfAbsent(userId, k -> Collections.synchronizedList(new ArrayList<>())).add(op);
        System.out.println("[SyncService] published " + op);
        return op;
    }

    public List<Op> fetchOps(String userId, long lastSeenSeq, int maxEntries) {
            List<Op> list = userOpLogs.getOrDefault(userId, Collections.emptyList());
            List<Op> res = new ArrayList<>();
            synchronized (list) {
                for (Op op : list) {
                    if (op.seq > lastSeenSeq) {
                        res.add(op);
                        if (res.size() >= maxEntries) break;
                    }
                }
            }
            return res;
        }
}
