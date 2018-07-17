package info.lmovse.netty4.protocol.codec;

import info.lmovse.netty4.protocol.support.ProtocolHeader;
import info.lmovse.netty4.protocol.support.ProtocolMessage;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToMessageEncoder;

import java.time.Instant;
import java.util.List;
import java.util.Random;

public class ExchangeEncoder extends MessageToMessageEncoder<Object> {

    private static final short magic = (short) 0xbabe;
    private Random random = new Random(Instant.now().toEpochMilli());

    @Override
    protected void encode(final ChannelHandlerContext ctx, final Object msg, final List<Object> out) {
        if (msg instanceof ProtocolMessage) {
            out.add(msg);
            return;
        }
        byte flag = 0;
        long requestId = random.nextLong();
        ProtocolHeader header = new ProtocolHeader(magic, flag, requestId);
        out.add(new ProtocolMessage(header, msg));
    }
}
