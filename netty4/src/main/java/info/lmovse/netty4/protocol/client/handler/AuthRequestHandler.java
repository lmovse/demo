package info.lmovse.netty4.protocol.client.handler;

import info.lmovse.netty4.protocol.support.ProtocolHeader;
import info.lmovse.netty4.protocol.support.ProtocolMessage;
import info.lmovse.netty4.protocol.value.AuthResult;
import info.lmovse.netty4.protocol.value.Constant;
import info.lmovse.netty4.protocol.value.Event;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static info.lmovse.netty4.protocol.support.RequestIdGenerator.generateId;
import static info.lmovse.netty4.protocol.value.Constant.FLAG_EVENT;
import static info.lmovse.netty4.protocol.value.Constant.FLAG_REQUEST;
import static info.lmovse.netty4.protocol.value.Constant.FLAG_TWOWAY;

public class AuthRequestHandler extends ChannelInboundHandlerAdapter {

    private static final Logger LOGGER = LoggerFactory.getLogger(AuthRequestHandler.class);

    @Override
    public void channelActive(final ChannelHandlerContext ctx) {
        byte flag = FLAG_TWOWAY | FLAG_EVENT | FLAG_REQUEST;
        ProtocolHeader header = new ProtocolHeader(Constant.MAGIC, flag, generateId());
        ProtocolMessage message = new ProtocolMessage(header, Event.AUTH);
        ctx.writeAndFlush(message);
    }

    @Override
    public void channelRead(final ChannelHandlerContext ctx, final Object msg) {
        ProtocolMessage protocolMessage = (ProtocolMessage) msg;
        ProtocolHeader header = protocolMessage.getHeader();
        int i = header.getFlag() & FLAG_EVENT;
        if (i == FLAG_EVENT && protocolMessage.getBody() == AuthResult.SUCCESS) {
            LOGGER.info("=== Auth Success!");
            ctx.fireChannelRead(msg);
        } else if (i == FLAG_EVENT && protocolMessage.getBody() == AuthResult.FAIL) {
            LOGGER.error("=== Auth Failed!");
            ctx.close().addListener(ChannelFutureListener.CLOSE);
        } else {
            ctx.fireChannelRead(msg);
        }
    }
}
