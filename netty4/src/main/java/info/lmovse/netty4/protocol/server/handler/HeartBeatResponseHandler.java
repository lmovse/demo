package info.lmovse.netty4.protocol.server.handler;

import info.lmovse.netty4.protocol.support.ProtocolHeader;
import info.lmovse.netty4.protocol.support.ProtocolMessage;
import info.lmovse.netty4.protocol.value.Event;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static info.lmovse.netty4.protocol.value.Constant.FLAG_EVENT;
import static info.lmovse.netty4.protocol.value.Constant.MAGIC;

public class HeartBeatResponseHandler extends ChannelInboundHandlerAdapter {

    private static final Logger LOGGER = LoggerFactory.getLogger(HeartBeatResponseHandler.class);

    @Override
    public void channelRead(final ChannelHandlerContext ctx, final Object msg) {
        ProtocolMessage protocolMessage = (ProtocolMessage) msg;
        ProtocolHeader header = protocolMessage.getHeader();
        int i = header.getFlag() & FLAG_EVENT;
        if (FLAG_EVENT == i && protocolMessage.getBody() == Event.HEARTBEAT) {
            LOGGER.info("===> Receive heartBeat message, reqId: {}", header.getRequestId());
            ProtocolHeader protocolHeader = new ProtocolHeader(MAGIC, FLAG_EVENT, header.getRequestId());
            ProtocolMessage message = new ProtocolMessage(protocolHeader, Event.HEARTBEAT);
            ctx.writeAndFlush(message);
        } else {
            ctx.fireChannelRead(msg);
        }
    }
}
