package info.lmovse.netty4.protocol.codec;

import info.lmovse.netty4.protocol.support.ProtocolMessage;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToMessageDecoder;

import java.util.List;

import static info.lmovse.netty4.protocol.value.Constant.FLAG_EVENT;

public class ExchangeDecoder extends MessageToMessageDecoder<ProtocolMessage> {

    @Override
    protected void decode(final ChannelHandlerContext ctx, final ProtocolMessage msg, final List<Object> out) {
        int i = msg.getHeader().getFlag() & FLAG_EVENT;
        if (i == FLAG_EVENT) {
            out.add(msg);
        } else {
            out.add(msg.getBody());
        }
    }
}
