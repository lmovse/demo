package info.lmovse.netty4.protocol.codec;

import info.lmovse.netty4.protocol.serialization.KryoSerialization;
import info.lmovse.netty4.protocol.support.ProtocolHeader;
import info.lmovse.netty4.protocol.support.ProtocolMessage;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufInputStream;
import io.netty.buffer.ByteBufOutputStream;

public class ProtocolCodec {

    /**
     * 10 bytes header
     * + 2 bytes for magic number
     * + 1 bytes for flag
     * + 1 bytes for response status
     * + 8 bytes for requestId
     * + 4 bytes for body length
     */
    private static final int HEADER_LENGTH = 10;
    private static final short MAGIC = (short) 0xbabe;
    private static final byte[] LENGTH_PLACEHOLDER = new byte[4];
    private final KryoSerialization kryoSerialization;

    public ProtocolCodec(final KryoSerialization kryoSerialization) {
        this.kryoSerialization = kryoSerialization;
    }

    public ProtocolMessage decode(final ByteBuf byteBuf) {
        short magic = byteBuf.readShort();
        byte flag = byteBuf.readByte();
        byte status = byteBuf.readByte();
        long requestId = byteBuf.readLong();
        int bodyLength = byteBuf.readInt();
        ProtocolHeader header = new ProtocolHeader(magic, flag, status, requestId, bodyLength);
        byteBuf.slice(HEADER_LENGTH, bodyLength);
        Object body = kryoSerialization.deserialization(new ByteBufInputStream(byteBuf));
        return new ProtocolMessage(header, body);
    }

    public void encode(final ByteBuf byteBuf, final ProtocolMessage data) {
        ProtocolHeader header = data.getHeader();
        byteBuf.writeShort(MAGIC);
        byteBuf.writeByte(header.getFlag());
        byteBuf.writeByte(header.getStatus());
        byteBuf.writeLong(header.getRequestId());
        int startIndex = byteBuf.writerIndex();
        byteBuf.writeBytes(LENGTH_PLACEHOLDER);
        kryoSerialization.serialization(new ByteBufOutputStream(byteBuf), data.getBody());
        int endIndex = byteBuf.writerIndex();
        byteBuf.setInt(startIndex, endIndex - startIndex - 4);
    }
}
