package info.lmovse.netty3.client;

import info.lmovse.netty3.client.handler.TimeClientPoJoHandler;
import info.lmovse.netty3.client.handler.TimePoJoDecodeHandler;
import org.jboss.netty.bootstrap.ClientBootstrap;
import org.jboss.netty.channel.Channels;
import org.jboss.netty.channel.socket.nio.NioClientSocketChannelFactory;

import java.net.InetSocketAddress;
import java.util.concurrent.Executors;

public class Client {

    public static void main(String[] args) {
        NioClientSocketChannelFactory factory =
                new NioClientSocketChannelFactory(Executors.newCachedThreadPool(), Executors.newCachedThreadPool());
        ClientBootstrap bootstrap = new ClientBootstrap(factory);
        bootstrap.setPipelineFactory(() -> Channels.pipeline(new TimePoJoDecodeHandler(), new TimeClientPoJoHandler()));
        bootstrap.setOption("tcpNoDelay", true);
        bootstrap.setOption("keepAlive", true);
        bootstrap.connect(new InetSocketAddress("localhost", 7070));
    }
}
