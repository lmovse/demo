package info.lmovse.netty4.protocol.client.handler;

import info.lmovse.netty4.protocol.support.ProtocolHeader;
import info.lmovse.netty4.protocol.support.ProtocolMessage;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static info.lmovse.netty4.protocol.support.RequestIdGenerator.generateId;
import static info.lmovse.netty4.protocol.value.AuthResult.SUCCESS;
import static info.lmovse.netty4.protocol.value.Constant.FLAG_EVENT;
import static info.lmovse.netty4.protocol.value.Constant.FLAG_REQUEST;
import static info.lmovse.netty4.protocol.value.Constant.FLAG_TWOWAY;
import static info.lmovse.netty4.protocol.value.Constant.MAGIC;
import static info.lmovse.netty4.protocol.value.Event.HEARTBEAT;
import static java.util.concurrent.TimeUnit.MILLISECONDS;

public class HeartBeatRequestHandler extends ChannelInboundHandlerAdapter {

    private static final int HEARTBEAT_PERIOD = 6000;
    private static final Logger LOGGER = LoggerFactory.getLogger(HeartBeatRequestHandler.class);

    @Override
    public void channelRead(final ChannelHandlerContext ctx, final Object msg) {
        ProtocolMessage protocolMessage = (ProtocolMessage) msg;
        ProtocolHeader header = protocolMessage.getHeader();
        int i = header.getFlag() & FLAG_EVENT;
        if (FLAG_EVENT == i && protocolMessage.getBody() == SUCCESS) {
            LOGGER.info("=== Staring sending heartbeat request");
            // will start next task only previous task execute completed
            ctx.executor().scheduleWithFixedDelay(() -> {
                byte flag = FLAG_REQUEST | FLAG_EVENT | FLAG_TWOWAY;
                ProtocolHeader protocolHeader = new ProtocolHeader(MAGIC, flag, generateId());
                ProtocolMessage message = new ProtocolMessage(protocolHeader, HEARTBEAT);
                ctx.writeAndFlush(message);
            }, HEARTBEAT_PERIOD, HEARTBEAT_PERIOD, MILLISECONDS);
        } else if (FLAG_EVENT == i && protocolMessage.getBody() == HEARTBEAT) {
            LOGGER.info("===> Receive heartbeat response from server, reqId: {}", header.getRequestId());
        } else {
            ctx.fireChannelRead(msg);
        }
    }
}
