package LLDDropBox.models;

public interface ObjectStore {
    public String putObject(byte[] data);

    public byte[] getObject(String objectKey);

    public String getObjectUrl(String objectKey);
}
