package info.lmovse.springcloud.registry;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;

@EnableEurekaServer
@SpringBootApplication
public class CloudServer {

    public static void main(String[] args) {
        SpringApplication.run(CloudServer.class, args);
    }
}
