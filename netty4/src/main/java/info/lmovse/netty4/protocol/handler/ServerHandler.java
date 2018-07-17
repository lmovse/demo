package info.lmovse.netty4.protocol.handler;

import info.lmovse.netty4.protocol.support.Person;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

public class ServerHandler extends ChannelInboundHandlerAdapter {

    @Override
    public void channelRead(final ChannelHandlerContext ctx, final Object msg) throws Exception {
        Person person = (Person) msg;
        System.out.println(person);
        ctx.writeAndFlush(new Person(person.getAge() + 1, person.getName()));
    }
}
