package LLDDropBox.models;

public class Op {
    public long seq;
    public String userId;
    public String fileId;
    public String versionId;
    public String opType;
    public long ts;

    public Op(long seq, String userId, String fileId, String versionId, String opType){
        this.seq = seq;
        this.userId = userId;
        this.fileId = fileId;
        this.versionId = versionId;
        this.opType = opType;
        this.ts = System.currentTimeMillis();
    }

    @Override
    public String toString() {
        return "Op{" +
                "seq=" + seq +
                ", userId='" + userId + '\'' +
                ", fileId='" + fileId + '\'' +
                ", opType='" + opType + '\'' +
                ", ts=" + ts +
                '}';
    }
}
