package info.lmovse.springcloud.server;

import org.springframework.boot.SpringApplication;
import org.springframework.cloud.client.SpringCloudApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@EnableDiscoveryClient
@SpringCloudApplication
public class CloudProvider {

    public static void main(String[] args) {
        SpringApplication.run(CloudProvider.class, args);
    }

}
