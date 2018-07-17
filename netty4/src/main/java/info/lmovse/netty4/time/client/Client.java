package info.lmovse.netty4.time.client;

import info.lmovse.netty4.time.client.handler.TimeClientPoJoHandler;
import info.lmovse.netty4.time.client.handler.TimePoJoDecodeHandler;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;

import java.net.InetSocketAddress;

public class Client {

    public static void main(String[] args) {
        NioEventLoopGroup worker = new NioEventLoopGroup();
        Bootstrap bootstrap = new Bootstrap();
        try {
            ChannelFuture channelFuture =
                    bootstrap.group(worker)
                            .channel(NioSocketChannel.class)
                            .handler(new ChannelInitializer<SocketChannel>() {
                                @Override
                                protected void initChannel(final SocketChannel ch) {
                                    ch.pipeline().addLast(new TimePoJoDecodeHandler(), new TimeClientPoJoHandler());
                                }
                            })
                            .option(ChannelOption.SO_KEEPALIVE, true)
                            .connect(new InetSocketAddress("localhost", 7070))
                            .sync();
            channelFuture.channel().closeFuture().sync();
        } catch (InterruptedException e) {
            worker.shutdownGracefully();
        }
    }
}
