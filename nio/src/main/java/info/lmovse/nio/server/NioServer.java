package info.lmovse.nio.server;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.Iterator;

@SuppressWarnings("Duplicates")
public class NioServer {

    private static final ByteBuffer sendBuffer = ByteBuffer.allocate(1024);
    private static final ByteBuffer readBuffer = ByteBuffer.allocate(1024);

    public static void main(String[] args) throws IOException {
        ServerSocketChannel serverSocketChannel = ServerSocketChannel.open();
        serverSocketChannel.configureBlocking(false);
        Selector selector = Selector.open();
        serverSocketChannel.register(selector, SelectionKey.OP_ACCEPT);
        serverSocketChannel.bind(new InetSocketAddress(9090));
        while (!Thread.currentThread().isInterrupted()) {
            int numKey = selector.select();
            if (numKey == 0) {
                System.err.println("wakeup with zero!!");
                continue;
            }
            Iterator<SelectionKey> iterator = selector.selectedKeys().iterator();
            while (iterator.hasNext()) {
                SelectionKey selectionKey = iterator.next();
                if (selectionKey.isAcceptable()) {
                    System.out.println("channel accept...");
                    ServerSocketChannel socketChannel = (ServerSocketChannel) selectionKey.channel();
                    SocketChannel acceptChannel = socketChannel.accept();
                    // register acceptChannel READ event
                    acceptChannel.configureBlocking(false);
                    acceptChannel.register(selector, SelectionKey.OP_READ);
                } else if (selectionKey.isReadable()) {
                    System.out.println("channel reading...");
                    SocketChannel channel = (SocketChannel) selectionKey.channel();
                    readBuffer.clear();
                    int byteLength;
                    try {
                        byteLength = channel.read(readBuffer);
                    } catch (IOException e) {
                        // The remote forcibly closed the connection,
                        // cancel the selection key and close the channel.
                        selectionKey.cancel();
                        channel.close();
                        return;
                    }
                    byte[] array = readBuffer.array();
                    System.out.println(new String(array, 0, byteLength));
                    channel.register(selector, SelectionKey.OP_WRITE);
                } else if (selectionKey.isWritable()) {
                    System.out.println("channel writing...");
                    SocketChannel channel = (SocketChannel) selectionKey.channel();
                    sendBuffer.clear();
                    sendBuffer.put("I am server".getBytes());
                    sendBuffer.flip();
                    channel.write(sendBuffer);
                    channel.register(selector, SelectionKey.OP_READ);
                }
                iterator.remove();
            }
        }
    }
}
