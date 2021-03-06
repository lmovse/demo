package info.lmovse.netty4.protocol.client.handler;

import info.lmovse.netty4.protocol.support.Person;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ClientHandler extends ChannelInboundHandlerAdapter {

    private static final Logger log = LoggerFactory.getLogger(ClientHandler.class.getName());

    @Override
    public void channelActive(final ChannelHandlerContext ctx) {
        ctx.writeAndFlush(new Person(1, "lmovse"));
    }

    @Override
    public void channelRead(final ChannelHandlerContext ctx, final Object msg) throws InterruptedException {
        Person person = (Person) msg;
        log.info("===> Person: ", person);
        Thread.sleep(1000);
        ctx.writeAndFlush(person);
    }
}
