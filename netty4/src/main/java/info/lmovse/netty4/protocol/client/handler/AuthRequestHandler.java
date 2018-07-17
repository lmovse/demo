package info.lmovse.netty4.protocol.client.handler;

import info.lmovse.netty4.protocol.support.ProtocolHeader;
import info.lmovse.netty4.protocol.support.ProtocolMessage;
import info.lmovse.netty4.protocol.value.AuthResult;
import info.lmovse.netty4.protocol.value.Constant;
import info.lmovse.netty4.protocol.value.Event;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

import static info.lmovse.netty4.protocol.value.Constant.FLAG_EVENT;
import static info.lmovse.netty4.protocol.value.Constant.FLAG_REQUEST;
import static info.lmovse.netty4.protocol.value.Constant.FLAG_TWOWAY;

public class AuthRequestHandler extends ChannelInboundHandlerAdapter {

    @Override
    public void channelActive(final ChannelHandlerContext ctx) {
        byte flag = FLAG_TWOWAY | FLAG_EVENT | FLAG_REQUEST;
        ProtocolHeader header = new ProtocolHeader(Constant.MAGIC, flag, 0L);
        ProtocolMessage message = new ProtocolMessage(header, Event.AUTH);
        ctx.writeAndFlush(message);
    }

    @Override
    public void channelRead(final ChannelHandlerContext ctx, final Object msg) {
        ProtocolMessage protocolMessage = (ProtocolMessage) msg;
        ProtocolHeader header = protocolMessage.getHeader();
        int i = header.getFlag() & FLAG_EVENT;
        if (i == FLAG_EVENT && protocolMessage.getBody() == AuthResult.SUCCESS) {
            System.out.println("auth success!");
            ctx.fireChannelRead(msg);
        } else if (i == FLAG_EVENT && protocolMessage.getBody() == AuthResult.FAIL) {
            System.out.println("auth failed!");
            ctx.close().addListener(ChannelFutureListener.CLOSE);
        } else {
            ctx.fireChannelRead(msg);
        }
    }
}