package info.lmovse.nio.client;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.SocketChannel;
import java.util.Iterator;

import static java.nio.channels.SelectionKey.OP_CONNECT;
import static java.nio.channels.SelectionKey.OP_WRITE;

@SuppressWarnings("Duplicates")
public class NioClient {

    private static final ByteBuffer sendBuffer = ByteBuffer.allocate(1024);
    private static final ByteBuffer readBuffer = ByteBuffer.allocate(1024);

    public static void main(String[] args) throws IOException {
        SocketChannel channel = SocketChannel.open();
        channel.configureBlocking(false);
        channel.connect(new InetSocketAddress(9090));
        Selector selector = Selector.open();
        channel.register(selector, OP_CONNECT);
        while (!Thread.currentThread().isInterrupted()) {
            int keyNum = selector.select();
            if (keyNum == 0) {
                System.err.println("wakeup with zero key!!");
                continue;
            }
            Iterator<SelectionKey> keyIterator = selector.selectedKeys().iterator();
            while (keyIterator.hasNext()) {
                SelectionKey selectionKey = keyIterator.next();
                if (selectionKey.isConnectable()) {
                    SocketChannel socketChannel = (SocketChannel) selectionKey.channel();
                    // here must finishConnect or will cause selector.select return 0
                    socketChannel.finishConnect();
                    socketChannel.register(selector, OP_WRITE);
                } else if (selectionKey.isWritable()) {
                    SocketChannel socketChannel = (SocketChannel) selectionKey.channel();
                    sendBuffer.clear();
                    sendBuffer.put("I am from client".getBytes());
                    // should flip before readData from buffer
                    sendBuffer.flip();
                    socketChannel.write(sendBuffer);
                    socketChannel.register(selector, SelectionKey.OP_READ);
                } else if (selectionKey.isReadable()) {
                    SocketChannel socketChannel = (SocketChannel) selectionKey.channel();
                    readBuffer.clear();
                    int readByteLength;
                    try {
                        readByteLength = socketChannel.read(readBuffer);
                    } catch (IOException e) {
                        selectionKey.cancel();
                        channel.close();
                        return;
                    }
                    System.out.println(new String(readBuffer.array(), 0, readByteLength));
                    socketChannel.register(selector, OP_WRITE);
                }
                keyIterator.remove();
            }
        }
    }
}
