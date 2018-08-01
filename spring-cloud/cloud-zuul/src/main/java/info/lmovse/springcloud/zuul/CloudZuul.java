package info.lmovse.springcloud.zuul;

import org.springframework.boot.SpringApplication;
import org.springframework.cloud.client.SpringCloudApplication;
import org.springframework.cloud.netflix.feign.EnableFeignClients;

@SpringCloudApplication
@EnableFeignClients
public class CloudZuul {

    public static void main(String[] args) {
        SpringApplication.run(CloudZuul.class, args);
    }
}

