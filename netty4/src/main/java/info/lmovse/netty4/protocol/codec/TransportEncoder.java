package info.lmovse.netty4.protocol.codec;

import info.lmovse.netty4.protocol.support.ProtocolMessage;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;

public class TransportEncoder extends MessageToByteEncoder<ProtocolMessage> {

    private ProtocolCodec codec;

    public TransportEncoder(final ProtocolCodec codec) {
        this.codec = codec;
    }

    @Override
    protected void encode(final ChannelHandlerContext ctx, final ProtocolMessage msg, final ByteBuf out) {
        codec.encode(out, msg);
    }
}
