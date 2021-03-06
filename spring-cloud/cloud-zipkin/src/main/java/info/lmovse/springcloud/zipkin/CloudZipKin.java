package info.lmovse.springcloud.zipkin;


import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import zipkin.server.internal.EnableZipkinServer;

@EnableZipkinServer
@SpringBootApplication
public class CloudZipKin {

    public static void main(String[] args) {
        SpringApplication.run(CloudZipKin.class, args);
    }
}
