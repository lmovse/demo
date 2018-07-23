package info.lmovse.springcloud.gateway;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.cloud.client.SpringCloudApplication;
import org.springframework.cloud.netflix.feign.EnableFeignClients;
import org.springframework.cloud.netflix.zuul.EnableZuulProxy;

@EnableZuulProxy
@SpringCloudApplication
@EnableFeignClients
@Slf4j
public class CloudGateway {

    public static void main(String[] args) {
        SpringApplication.run(CloudGateway.class, args);
    }
}

