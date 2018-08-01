package info.lmovse.netty4.protocol.support;

public class ProtocolHeader {
    private short magic;
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
        this(magic, flag, (byte) 0, requestId, 0);
    }

    public ProtocolHeader(final short magic, final byte flag, final byte status, final long requestId) {
        this(magic, flag, status, requestId, 0);
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
