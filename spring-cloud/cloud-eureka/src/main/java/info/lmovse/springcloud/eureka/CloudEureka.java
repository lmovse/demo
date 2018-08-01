package info.lmovse.springcloud.eureka;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;

@EnableEurekaServer
@SpringBootApplication
public class CloudEureka {

    public static void main(String[] args) {
        SpringApplication.run(CloudEureka.class, args);
    }
}
