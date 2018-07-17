package info.lmovse.netty4.protocol.codec;

import info.lmovse.netty4.protocol.support.ProtocolMessage;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToMessageDecoder;

import java.util.List;

public class ExchangeDecoder extends MessageToMessageDecoder<ProtocolMessage> {

    @Override
    protected void decode(final ChannelHandlerContext ctx, final ProtocolMessage msg, final List<Object> out) {
        out.add(msg.getBody());
    }
}
