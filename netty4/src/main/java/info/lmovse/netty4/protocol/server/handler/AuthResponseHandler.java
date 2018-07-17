package info.lmovse.netty4.protocol.server.handler;

import info.lmovse.netty4.protocol.support.ProtocolHeader;
import info.lmovse.netty4.protocol.support.ProtocolMessage;
import info.lmovse.netty4.protocol.value.AuthResult;
import info.lmovse.netty4.protocol.value.Constant;
import info.lmovse.netty4.protocol.value.Event;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

import static info.lmovse.netty4.protocol.value.Constant.FLAG_EVENT;

public class AuthResponseHandler extends ChannelInboundHandlerAdapter {

    @Override
    public void channelRead(final ChannelHandlerContext ctx, final Object msg) {
        ProtocolMessage message = (ProtocolMessage) msg;
        ProtocolHeader header = message.getHeader();
        int i = header.getFlag() & FLAG_EVENT;
        if (i == FLAG_EVENT && message.getBody() == Event.AUTH) {
            System.out.println("receive auth request from remote address: " + ctx.channel().remoteAddress());
            ProtocolHeader protocolHeader = new ProtocolHeader(Constant.MAGIC, FLAG_EVENT, 0L);
            ProtocolMessage protocolMessage = new ProtocolMessage(protocolHeader, AuthResult.SUCCESS);
            ctx.writeAndFlush(protocolMessage);
        } else {
            ctx.fireChannelRead(msg);
        }
    }
}
