package info.lmovse.netty4.protocol.codec;

import info.lmovse.netty4.protocol.support.ProtocolHeader;
import info.lmovse.netty4.protocol.support.ProtocolMessage;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToMessageEncoder;

import java.util.List;
import java.util.Random;

import static java.time.Instant.now;

public class ExchangeEncoder extends MessageToMessageEncoder<Object> {

    private static final short magic = (short) 0xbabe;

    @Override
    protected void encode(final ChannelHandlerContext ctx, final Object msg, final List<Object> out) {
        byte flag = 0;
        long requestId = new Random(now().toEpochMilli()).nextLong();
        ProtocolHeader header = new ProtocolHeader(magic, flag, requestId);
        out.add(new ProtocolMessage(header, msg));
    }
}
