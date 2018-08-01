package info.lmovse.oio.server;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.time.Instant;
import java.util.concurrent.ExecutorService;

import static java.lang.Runtime.getRuntime;
import static java.util.concurrent.Executors.newFixedThreadPool;

public class OioServer {

    public static void main(String[] args) throws IOException {
        ExecutorService executorService = newFixedThreadPool(getRuntime().availableProcessors() + 1);
        ServerSocket serverSocket = new ServerSocket();
        serverSocket.bind(new InetSocketAddress(9090));
        while (!Thread.currentThread().isInterrupted()) {
            byte[] bytes = new byte[1024];
            Socket socket = serverSocket.accept();
            executorService.submit(() -> {
                try {
                    while (!Thread.currentThread().isInterrupted()) {
                        int read = socket.getInputStream().read(bytes);
                        System.out.println(new String(bytes, 0, read));
                        String s = "I am server, current epoch milli time is: " + Instant.now().toEpochMilli();
                        socket.getOutputStream().write(s.getBytes());
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            });
        }
    }
}
