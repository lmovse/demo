package info.lmovse.netty4.protocol.codec;

import info.lmovse.netty4.protocol.support.ProtocolHeader;
import info.lmovse.netty4.protocol.support.ProtocolMessage;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToMessageEncoder;

import java.util.List;

import static info.lmovse.netty4.protocol.support.RequestIdGenerator.generateId;
import static info.lmovse.netty4.protocol.value.Constant.FLAG_REQUEST;
import static info.lmovse.netty4.protocol.value.Constant.FLAG_TWOWAY;
import static info.lmovse.netty4.protocol.value.Constant.MAGIC;

public class ExchangeEncoder extends MessageToMessageEncoder<Object> {

    @Override
    protected void encode(final ChannelHandlerContext ctx, final Object msg, final List<Object> out) {
        byte flag = FLAG_REQUEST | FLAG_TWOWAY;
        out.add(new ProtocolMessage(new ProtocolHeader(MAGIC, flag, generateId()), msg));
    }
}
