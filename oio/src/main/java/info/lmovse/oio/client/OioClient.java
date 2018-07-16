package info.lmovse.oio.client;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.nio.charset.Charset;
import java.time.Instant;

public class OioClient {

    public static void main(String[] args) throws IOException {
        Socket socket = new Socket();
        socket.connect(new InetSocketAddress(9090));
        String s = "I am client, current unix time: " + Instant.now().toEpochMilli();
        socket.getOutputStream().write(s.getBytes(Charset.forName("UTF-8")));
        while (!Thread.currentThread().isInterrupted()) {
            byte[] bytes = new byte[1024];
            try {
                int read = socket.getInputStream().read(bytes);
                System.out.println(new String(bytes, 0, read, Charset.forName("UTF-8")));
                Thread.sleep(2000);
                String s2 = "I am client, current epoch milli time: " + Instant.now().toEpochMilli();
                socket.getOutputStream().write(s2.getBytes("UTF-8"));
            } catch (IOException | InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
