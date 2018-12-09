package info.lmovse.netty4.protocol.server.handler;

import info.lmovse.netty4.protocol.support.Person;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ServerHandler extends ChannelInboundHandlerAdapter {

    private static final Logger log = LoggerFactory.getLogger(ServerHandler.class.getName());

    @Override
    public void channelRead(final ChannelHandlerContext ctx, final Object msg) {
        Person person = (Person) msg;
        log.info("===> Person: ", person);
        ctx.writeAndFlush(new Person(person.getAge() + 1, person.getName()));
    }
}
