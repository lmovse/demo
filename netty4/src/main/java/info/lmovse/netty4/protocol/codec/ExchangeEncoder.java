package info.lmovse.netty4.protocol.codec;

import info.lmovse.netty4.protocol.support.ProtocolHeader;
import info.lmovse.netty4.protocol.support.ProtocolMessage;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToMessageEncoder;

import java.time.Instant;
import java.util.List;
import java.util.Random;

import static info.lmovse.netty4.protocol.value.Constant.FLAG_REQUEST;
import static info.lmovse.netty4.protocol.value.Constant.FLAG_TWOWAY;

public class ExchangeEncoder extends MessageToMessageEncoder<Object> {

    private static final short magic = (short) 0xbabe;
    private Random random = new Random(Instant.now().toEpochMilli());

    @Override
    protected void encode(final ChannelHandlerContext ctx, final Object msg, final List<Object> out) {
        byte flag = FLAG_REQUEST | FLAG_TWOWAY;
        long requestId = random.nextLong();
        out.add(new ProtocolMessage(new ProtocolHeader(magic, flag, requestId), msg));
    }
}
