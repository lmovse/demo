package info.lmovse.netty4.protocol.server;

import info.lmovse.netty4.protocol.codec.ExchangeDecoder;
import info.lmovse.netty4.protocol.codec.ExchangeEncoder;
import info.lmovse.netty4.protocol.codec.ProtocolCodec;
import info.lmovse.netty4.protocol.codec.TransportDecoder;
import info.lmovse.netty4.protocol.codec.TransportEncoder;
import info.lmovse.netty4.protocol.serialization.KryoSerialization;
import info.lmovse.netty4.protocol.server.handler.AuthResponseHandler;
import info.lmovse.netty4.protocol.server.handler.HeartBeatResponseHandler;
import info.lmovse.netty4.protocol.server.handler.ServerHandler;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;

public class ProtocolServer {
    public static void main(String[] args) {
        NioEventLoopGroup bossGroup = new NioEventLoopGroup();
        NioEventLoopGroup workerGroup = new NioEventLoopGroup();
        ProtocolCodec codec = new ProtocolCodec(new KryoSerialization());
        ServerBootstrap bootstrap = new ServerBootstrap();
        try {
            ChannelFuture channelFuture = bootstrap.group(bossGroup, workerGroup)
                    .channel(NioServerSocketChannel.class)
                    .childHandler(new ChannelInitializer<SocketChannel>() {
                        @Override
                        protected void initChannel(final SocketChannel ch) {
                            ch.pipeline()
                                    .addLast("transport-decoder", new TransportDecoder(codec))
                                    .addLast("exchange-decoder", new ExchangeDecoder())
                                    .addLast("transport-encoder", new TransportEncoder(codec))
                                    .addLast("exchange-encoder", new ExchangeEncoder())
                                    .addLast("auth-handler", new AuthResponseHandler())
                                    .addLast("heartbeat-handler", new HeartBeatResponseHandler())
                                    .addLast("handler", new ServerHandler());
                        }
                    })
                    .option(ChannelOption.SO_BACKLOG, 128)
                    .childOption(ChannelOption.SO_KEEPALIVE, true)
                    .childOption(ChannelOption.TCP_NODELAY, true)
                    .bind(7070)
                    .sync();// bind port to accept incoming connections
            // Wait until the server socket is closed
            // For this example, it won't be happen
            channelFuture.channel().closeFuture().sync();
        } catch (InterruptedException e) {
            workerGroup.shutdownGracefully();
            bossGroup.shutdownGracefully();
        }
    }
}
