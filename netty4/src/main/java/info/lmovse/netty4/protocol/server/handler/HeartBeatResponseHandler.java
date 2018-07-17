package info.lmovse.netty4.protocol.server.handler;

import info.lmovse.netty4.protocol.support.ProtocolHeader;
import info.lmovse.netty4.protocol.support.ProtocolMessage;
import info.lmovse.netty4.protocol.value.Event;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

import static info.lmovse.netty4.protocol.value.Constant.FLAG_EVENT;
import static info.lmovse.netty4.protocol.value.Constant.MAGIC;

public class HeartBeatResponseHandler extends ChannelInboundHandlerAdapter {

    @Override
    public void channelRead(final ChannelHandlerContext ctx, final Object msg) {
        ProtocolMessage protocolMessage = (ProtocolMessage) msg;
        ProtocolHeader header = protocolMessage.getHeader();
        int i = header.getFlag() & FLAG_EVENT;
        if (FLAG_EVENT == i && protocolMessage.getBody() == Event.HEARTBEAT) {
            System.out.println("receive heartBeat message...");
            ProtocolHeader protocolHeader = new ProtocolHeader(MAGIC, FLAG_EVENT, 0L);
            ProtocolMessage message = new ProtocolMessage(protocolHeader, Event.HEARTBEAT);
            ctx.writeAndFlush(message);
        } else {
            ctx.fireChannelRead(msg);
        }
    }
}
