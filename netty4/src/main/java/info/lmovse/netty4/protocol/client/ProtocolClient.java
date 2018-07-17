package info.lmovse.netty4.protocol.client;

import info.lmovse.netty4.protocol.client.handler.AuthRequestHandler;
import info.lmovse.netty4.protocol.client.handler.ClientHandler;
import info.lmovse.netty4.protocol.client.handler.HeartBeatRequestHandler;
import info.lmovse.netty4.protocol.codec.ExchangeDecoder;
import info.lmovse.netty4.protocol.codec.ExchangeEncoder;
import info.lmovse.netty4.protocol.codec.ProtocolCodec;
import info.lmovse.netty4.protocol.codec.TransportDecoder;
import info.lmovse.netty4.protocol.codec.TransportEncoder;
import info.lmovse.netty4.protocol.serialization.KryoSerialization;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;

import java.net.InetSocketAddress;

public class ProtocolClient {

    public static void main(String[] args) {
        NioEventLoopGroup worker = new NioEventLoopGroup();
        ProtocolCodec codec = new ProtocolCodec(new KryoSerialization());
        Bootstrap bootstrap = new Bootstrap();
        try {
            ChannelFuture channelFuture =
                    bootstrap.group(worker)
                            .channel(NioSocketChannel.class)
                            .handler(new ChannelInitializer<SocketChannel>() {
                                @Override
                                protected void initChannel(final SocketChannel ch) {
                                    ch.pipeline()
                                            .addLast("transport-decoder", new TransportDecoder(codec))
                                            .addLast("exchange-decoder", new ExchangeDecoder())
                                            .addLast("transport-encoder", new TransportEncoder(codec))
                                            .addLast("auth-handler", new AuthRequestHandler())
                                            .addLast("heartbeat-handler", new HeartBeatRequestHandler())
                                            .addLast("exchange-encoder", new ExchangeEncoder())
                                            .addLast("handler", new ClientHandler());
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
