package LLDDropBox.models;

import java.util.Arrays;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;

public class InMemoryS3Store implements ObjectStore{

    private final Map<String, byte[]> store = new ConcurrentHashMap<>();
    private final AtomicLong counter = new AtomicLong(1);

    @Override
    public String putObject(byte[] data) {
       
        String key = "obj-" + counter.getAndIncrement();
        store.put(key, Arrays.copyOf(data, data.length));
        return key;
    }

    @Override
    public byte[] getObject(String objectKey) {
        byte[] data = store.get(objectKey);
        return data == null ? null : Arrays.copyOf(data, data.length);
    }

    @Override
    public String getObjectUrl(String objectKey) {
        return "s3://inmemory-bucket/" + objectKey;    
    }

}
