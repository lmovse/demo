package info.lmovse.netty4.protocol.server.handler;

import info.lmovse.netty4.protocol.support.ProtocolHeader;
import info.lmovse.netty4.protocol.support.ProtocolMessage;
import info.lmovse.netty4.protocol.value.AuthResult;
import info.lmovse.netty4.protocol.value.Constant;
import info.lmovse.netty4.protocol.value.Event;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static info.lmovse.netty4.protocol.value.Constant.FLAG_EVENT;
import static info.lmovse.netty4.protocol.value.Constant.OK;

public class AuthResponseHandler extends ChannelInboundHandlerAdapter {

    private static final Logger LOGGER = LoggerFactory.getLogger(AuthResponseHandler.class);

    @Override
    public void channelRead(final ChannelHandlerContext ctx, final Object msg) {
        ProtocolMessage message = (ProtocolMessage) msg;
        ProtocolHeader header = message.getHeader();
        int i = header.getFlag() & FLAG_EVENT;
        if (i == FLAG_EVENT && message.getBody() == Event.AUTH) {
            LOGGER.info("===> Receive auth request from remote address:  {}", ctx.channel().remoteAddress());
            ProtocolHeader protocolHeader = new ProtocolHeader(Constant.MAGIC, FLAG_EVENT, OK, header.getRequestId());
            ProtocolMessage protocolMessage = new ProtocolMessage(protocolHeader, AuthResult.SUCCESS);
            ctx.writeAndFlush(protocolMessage);
        } else {
            ctx.fireChannelRead(msg);
        }
    }
}
