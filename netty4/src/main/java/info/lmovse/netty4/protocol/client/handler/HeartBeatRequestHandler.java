package info.lmovse.netty4.protocol.client.handler;

import info.lmovse.netty4.protocol.support.ProtocolHeader;
import info.lmovse.netty4.protocol.support.ProtocolMessage;
import info.lmovse.netty4.protocol.value.AuthResult;
import info.lmovse.netty4.protocol.value.Event;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

import java.util.concurrent.TimeUnit;

import static info.lmovse.netty4.protocol.value.Constant.FLAG_EVENT;
import static info.lmovse.netty4.protocol.value.Constant.FLAG_REQUEST;
import static info.lmovse.netty4.protocol.value.Constant.FLAG_TWOWAY;
import static info.lmovse.netty4.protocol.value.Constant.MAGIC;

public class HeartBeatRequestHandler extends ChannelInboundHandlerAdapter {

    @Override
    public void channelRead(final ChannelHandlerContext ctx, final Object msg) {
        ProtocolMessage protocolMessage = (ProtocolMessage) msg;
        ProtocolHeader header = protocolMessage.getHeader();
        int i = header.getFlag() & FLAG_EVENT;
        if (FLAG_EVENT == i && protocolMessage.getBody() == AuthResult.SUCCESS) {
            System.out.println("starting send heartbeat message...");
            ctx.executor().scheduleAtFixedRate(() -> {
                byte flag = FLAG_REQUEST | FLAG_EVENT | FLAG_TWOWAY;
                ProtocolHeader protocolHeader = new ProtocolHeader(MAGIC, flag, 0L);
                ProtocolMessage message = new ProtocolMessage(protocolHeader, Event.HEARTBEAT);
                ctx.writeAndFlush(message);
            }, 0, 5000, TimeUnit.MILLISECONDS);
        } else if (FLAG_EVENT == i && protocolMessage.getBody() == Event.HEARTBEAT) {
            System.out.println("receive heartBeat message...");
        } else {
            ctx.fireChannelRead(msg);
        }
    }
}
