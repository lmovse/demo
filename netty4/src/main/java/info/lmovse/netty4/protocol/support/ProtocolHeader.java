package info.lmovse.netty4.protocol.support;

public class ProtocolHeader {
    private short magic;
    // event two-way request
    private byte flag;
    private byte status;
    private long requestId;
    private int bodyLength;

    public ProtocolHeader(final short magic, final byte flag, final byte status,
                          final long requestId, final int bodyLength) {
        this.magic = magic;
        this.flag = flag;
        this.status = status;
        this.requestId = requestId;
        this.bodyLength = bodyLength;
    }

    public ProtocolHeader(final short magic, final byte flag, final long requestId) {
        this.magic = magic;
        this.status = 0;
        this.flag = flag;
        this.requestId = requestId;
    }

    public short getMagic() {
        return magic;
    }

    public byte getFlag() {
        return flag;
    }

    public byte getStatus() {
        return status;
    }

    public long getRequestId() {
        return requestId;
    }

    public int getBodyLength() {
        return bodyLength;
    }
}